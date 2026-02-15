package com.epeshi.esalium.entity.custom;

import com.epeshi.esalium.item.ModItems;
import com.epeshi.esalium.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EmperrorPuEntity extends HostileEntity {

    private final ServerBossBar bossBar = new ServerBossBar(this.getDisplayName(), BossBar.Color.RED, BossBar.Style.NOTCHED_10);

    private static final TrackedData<Boolean> AWAKENED = DataTracker.registerData(EmperrorPuEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> BLOCKING = DataTracker.registerData(EmperrorPuEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> AERIAL_PHASE = DataTracker.registerData(EmperrorPuEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> FINAL_PHASE = DataTracker.registerData(EmperrorPuEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int phase1DamageTakenCounter = 0;
    private int phase2HitCounter = 0;
    private int aerialTimer = 0;

    private boolean hasTriggeredAerial1 = false;
    private boolean hasTriggeredAerial2 = false;
    private boolean isSecondAerialPhase = false;

    private int smashCooldown = 0;
    private boolean isSmashing = false;

    private double startY = 0;
    private int musicTicker = 0;
    private int targetSwitchTicker = 0;

    private final List<VillagerEntity> bombs = new ArrayList<>();
    private final List<UUID> spawnedMinions = new ArrayList<>();

    public EmperrorPuEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.getNavigation().setCanSwim(true);
        this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
        this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        super.dropLoot(damageSource, causedByPlayer);
        // İsim değişti: Melodi Heart
        this.dropItem(ModItems.MELODI_HEART);
        this.dropItem(ModItems.EMPERRORS_TEARS);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Awakened", this.isAwakened());
        nbt.putBoolean("AerialPhase", this.isAerialPhase());
        nbt.putBoolean("FinalPhase", this.isFinalPhase());
        nbt.putBoolean("HasTriggeredAerial1", this.hasTriggeredAerial1);
        nbt.putBoolean("HasTriggeredAerial2", this.hasTriggeredAerial2);
        nbt.putInt("AerialTimer", this.aerialTimer);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Awakened")) this.setAwakened(nbt.getBoolean("Awakened"));
        if (nbt.contains("AerialPhase")) this.setAerialPhase(nbt.getBoolean("AerialPhase"));
        if (nbt.contains("FinalPhase")) this.setFinalPhase(nbt.getBoolean("FinalPhase"));
        if (nbt.contains("HasTriggeredAerial1")) this.hasTriggeredAerial1 = nbt.getBoolean("HasTriggeredAerial1");
        if (nbt.contains("HasTriggeredAerial2")) this.hasTriggeredAerial2 = nbt.getBoolean("HasTriggeredAerial2");
        if (nbt.contains("AerialTimer")) this.aerialTimer = nbt.getInt("AerialTimer");
        updateBossBarName();
    }

    private void updateBossBarName() {
        if (this.isFinalPhase()) {
            // Dil anahtarı: boss.esalium...
            this.bossBar.setName(Text.translatable("boss.esalium.phase_final").formatted(Formatting.DARK_RED, Formatting.BOLD));
            this.bossBar.setColor(BossBar.Color.RED);
            this.bossBar.setStyle(BossBar.Style.NOTCHED_20);
        } else if (this.hasTriggeredAerial1) {
            this.bossBar.setName(Text.translatable("boss.esalium.phase_ground").formatted(Formatting.RED));
        }
    }

    @Override
    protected BodyControl createBodyControl() {
        return new BodyControl(this) {
            @Override
            public void tick() {
                float headYaw = EmperrorPuEntity.this.getHeadYaw();
                float bodyYaw = EmperrorPuEntity.this.bodyYaw;
                float delta = MathHelper.wrapDegrees(headYaw - bodyYaw);
                if (Math.abs(delta) > 0.5f) {
                    float change = MathHelper.clamp(delta, -10.0F, 10.0F);
                    EmperrorPuEntity.this.bodyYaw = MathHelper.wrapDegrees(bodyYaw + change);
                } else {
                    EmperrorPuEntity.this.bodyYaw = headYaw;
                }
            }
        };
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new MobNavigation(this, world) {
            @Override
            protected PathNodeNavigator createPathNodeNavigator(int range) {
                this.nodeMaker = new LandPathNodeMaker();
                this.nodeMaker.setCanEnterOpenDoors(true);
                return new PathNodeNavigator(this.nodeMaker, range) {
                    @Override
                    protected float getDistance(PathNode a, PathNode b) { return a.getHorizontalDistance(b); }
                };
            }
        };
    }

    @Override
    protected void initEquipment(net.minecraft.util.math.random.Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(AWAKENED, false);
        builder.add(BLOCKING, false);
        builder.add(AERIAL_PHASE, false);
        builder.add(FINAL_PHASE, false);
    }

    @Override
    public int getMaxLookYawChange() { return 50; }

    @Override
    public void tick() {
        super.tick();

        if (this.isAwakened() && this.isAlive()) {
            this.musicTicker++;
            if (this.musicTicker % 2400 == 0 || this.musicTicker == 1) {
                this.getWorld().playSound(null, this.getBlockPos(), ModSounds.BOSS_MUSIC, SoundCategory.RECORDS, 50.0f, 1.0f);
            }
            this.targetSwitchTicker++;
            if (this.targetSwitchTicker >= 100) {
                this.targetSwitchTicker = 0;
                switchTargetInMultiplayer();
            }
        }

        if (!this.getWorld().isClient) {
            ServerWorld serverWorld = (ServerWorld) this.getWorld();

            if (this.isAwakened() && this.age % 20 == 0) {
                List<VillagerEntity> scaredVillagers = this.getWorld().getEntitiesByClass(VillagerEntity.class, this.getBoundingBox().expand(30.0), entity -> true);
                for (VillagerEntity villager : scaredVillagers) {
                    Vec3d runawayDir = villager.getPos().subtract(this.getPos()).normalize();
                    Vec3d targetPos = villager.getPos().add(runawayDir.multiply(10.0));
                    villager.getNavigation().startMovingTo(targetPos.x, targetPos.y, targetPos.z, 1.2);
                }
            }

            if (!bombs.isEmpty()) {
                Iterator<VillagerEntity> iterator = bombs.iterator();
                while (iterator.hasNext()) {
                    VillagerEntity bomb = iterator.next();
                    if (bomb.isRemoved()) { iterator.remove(); continue; }
                    if (bomb.isOnGround()) {
                        serverWorld.createExplosion(this, bomb.getX(), bomb.getY(), bomb.getZ(), 2.0f, ExplosionSourceType.NONE);
                        serverWorld.spawnParticles(ParticleTypes.EXPLOSION, bomb.getX(), bomb.getY(), bomb.getZ(), 1, 0, 0, 0, 0);
                        bomb.discard();
                        iterator.remove();
                    }
                }
            }

            if (this.isAerialPhase()) {
                this.aerialTimer++;
                this.setInvulnerable(true);
                this.setNoGravity(true);

                if (this.getY() < this.startY + 6.0) this.setVelocity(0, 0.2, 0);
                else { this.setVelocity(0, 0, 0); this.fallDistance = 0; }
                this.velocityModified = true;

                LivingEntity target = this.getTarget();
                if (target != null) this.getLookControl().lookAt(target, 100.0F, 100.0F);

                SimpleParticleType particle = isSecondAerialPhase ? ParticleTypes.SCULK_SOUL : ParticleTypes.DRAGON_BREATH;
                double radius = 1.5;
                double angle = (this.age % 20) * (Math.PI * 2) / 20;
                serverWorld.spawnParticles(particle, this.getX() + Math.cos(angle) * radius, this.getY(), this.getZ() + Math.sin(angle) * radius, 1, 0, 0, 0, 0);
                serverWorld.spawnParticles(particle, this.getX() - Math.cos(angle) * radius, this.getY(), this.getZ() - Math.sin(angle) * radius, 1, 0, 0, 0, 0);

                if (!isSecondAerialPhase) {
                    if (this.aerialTimer % 40 == 0) spawnAreaAttack();
                    if (this.aerialTimer >= 400) endAerialPhase();
                }
                else {
                    if (!spawnedMinions.isEmpty()) {
                        spawnedMinions.removeIf(uuid -> {
                            Entity entity = serverWorld.getEntity(uuid);
                            return entity == null || !entity.isAlive();
                        });
                        if (this.age % 20 == 0) {
                            serverWorld.spawnParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY() + 3, this.getZ(), 1, 0.5, 0.5, 0.5, 0.0);
                        }
                    } else {
                        endAerialPhase();
                    }
                }
                return;
            }

            if (this.isAwakened()) {
                float healthPercent = this.getHealth() / this.getMaxHealth();
                if (!hasTriggeredAerial1 && healthPercent <= 0.60f) { startAerialPhase(false); return; }
                if (hasTriggeredAerial1 && !hasTriggeredAerial2 && healthPercent <= 0.30f) { startAerialPhase(true); return; }
            }

            if (this.isFinalPhase()) {
                this.setGlowing(true);
                if (smashCooldown > 0) smashCooldown--;

                if (smashCooldown == 0 && this.isOnGround() && this.getTarget() != null) {
                    LivingEntity target = this.getTarget();
                    if (this.distanceTo(target) < 20) {
                        double dx = target.getX() - this.getX();
                        double dz = target.getZ() - this.getZ();
                        double dist = Math.sqrt(dx * dx + dz * dz);

                        this.setVelocity(dx / dist * 0.8, 1.2, dz / dist * 0.8);
                        this.velocityModified = true;

                        this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0f, 1.0f);
                        this.isSmashing = true;
                        this.smashCooldown = 100;
                    }
                }

                if (this.isSmashing && this.isOnGround() && this.getVelocity().y <= 0.01) {
                    this.isSmashing = false;
                    this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE.value(), 1.0f, 1.0f);

                    serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                    serverWorld.spawnParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, this.getX(), this.getY(), this.getZ(), 20, 3.0, 0.5, 3.0, 0.1);

                    List<LivingEntity> targets = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(6.0), e -> e != this);
                    for (LivingEntity e : targets) {
                        e.damage(this.getDamageSources().mobAttack(this), 10.0f);
                        double dx = e.getX() - this.getX();
                        double dz = e.getZ() - this.getZ();
                        e.takeKnockback(2.5, -dx, -dz);
                    }
                }
            }
        }
    }

    private void spawnAreaAttack() {
        List<PlayerEntity> players = this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(64.0), Entity::isAlive);
        if (players.isEmpty()) {
            LivingEntity t = this.getTarget();
            if (t instanceof PlayerEntity) players.add((PlayerEntity) t);
        }

        for (PlayerEntity player : players) {
            spawnFallingVillager(player);
        }
    }

    private void switchTargetInMultiplayer() {
        if (this.getWorld() instanceof ServerWorld) {
            List<PlayerEntity> players = this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(30.0), Entity::isAlive);
            if (players.size() > 1) {
                PlayerEntity newTarget = players.get(this.random.nextInt(players.size()));
                this.setTarget(newTarget);
            }
        }
    }

    private void startAerialPhase(boolean isSecond) {
        this.startY = this.getY();
        this.setAerialPhase(true);
        this.isSecondAerialPhase = isSecond;
        this.aerialTimer = 0;
        this.spawnedMinions.clear();

        if (isSecond) {
            this.hasTriggeredAerial2 = true;
        } else {
            this.hasTriggeredAerial1 = true;
        }

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            // Dil anahtarı: boss.esalium...
            String titleKey = isSecond ? "boss.esalium.phase_guards" : "boss.esalium.phase_aerial";
            BossBar.Color color = isSecond ? BossBar.Color.BLUE : BossBar.Color.PURPLE;

            this.bossBar.setName(Text.translatable(titleKey).formatted(isSecond ? Formatting.AQUA : Formatting.DARK_PURPLE));
            this.bossBar.setColor(color);
            this.bossBar.setStyle(BossBar.Style.PROGRESS);

            SimpleParticleType burstParticle = isSecond ? ParticleTypes.SONIC_BOOM : ParticleTypes.EXPLOSION_EMITTER;
            serverWorld.spawnParticles(burstParticle, this.getX(), this.getY() + 1, this.getZ(), 1, 0, 0, 0, 0);

            if (isSecond) {
                List<PlayerEntity> players = serverWorld.getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(64.0), Entity::isAlive);
                if (players.isEmpty() && this.getTarget() instanceof PlayerEntity) players.add((PlayerEntity)this.getTarget());

                for (PlayerEntity player : players) {
                    // Dil anahtarı: message.esalium...
                    player.sendMessage(Text.translatable("message.esalium.boss_warning").formatted(Formatting.RED, Formatting.BOLD), true);

                    for (int i = 0; i < 4; i++) {
                        PillagerEntity pillager = EntityType.PILLAGER.create(serverWorld);
                        if (pillager != null) {
                            double offsetX = (this.random.nextDouble() - 0.5) * 10.0;
                            double offsetZ = (this.random.nextDouble() - 0.5) * 10.0;
                            pillager.refreshPositionAndAngles(player.getX() + offsetX, player.getY(), player.getZ() + offsetZ, 0, 0);
                            pillager.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
                            serverWorld.spawnEntity(pillager);
                            spawnedMinions.add(pillager.getUuid());
                            serverWorld.spawnParticles(ParticleTypes.POOF, pillager.getX(), pillager.getY(), pillager.getZ(), 5, 0.2, 0.2, 0.2, 0.0);
                        }
                    }
                }
            } else {
                for (ServerPlayerEntity player : this.bossBar.getPlayers()) {
                    player.sendMessage(Text.translatable("message.esalium.boss_warning").formatted(Formatting.RED, Formatting.BOLD), true);
                }
            }

            this.playSound(isSecond ? SoundEvents.ENTITY_WARDEN_SONIC_BOOM : SoundEvents.ENTITY_WITHER_SPAWN, 1.0f, 0.5f);
        }
    }

    private void endAerialPhase() {
        this.setAerialPhase(false);
        this.setInvulnerable(false);
        this.setNoGravity(false);
        this.addVelocity(0, -3.0, 0);
        this.spawnedMinions.clear();

        if (isSecondAerialPhase) {
            this.setFinalPhase(true);
            updateBossBarName();
            this.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 0.8f);
        } else {
            this.bossBar.setName(Text.translatable("boss.esalium.phase_ground").formatted(Formatting.DARK_RED));
        }
        if (this.getWorld() instanceof ServerWorld sw) {
            sw.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
        }
    }

    private void spawnFallingVillager(LivingEntity target) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            VillagerEntity villager = EntityType.VILLAGER.create(serverWorld);
            if (villager != null) {
                villager.refreshPositionAndAngles(target.getX(), target.getY() + 15, target.getZ(), 0, 0);
                villager.setInvulnerable(true);
                villager.setCustomName(Text.literal("Boom!"));
                villager.setCustomNameVisible(true);
                villager.setVelocity(0, -1.0, 0);
                serverWorld.spawnEntity(villager);
                this.bombs.add(villager);
            }
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (this.isAerialPhase()) return false;
        boolean success = super.tryAttack(target);
        if (success && target instanceof LivingEntity livingTarget) {
            target.addVelocity(0.0, 0.5, 0.0);
            if (hasTriggeredAerial1 || isFinalPhase()) {
                this.phase2HitCounter++;
                if (this.phase2HitCounter % 2 == 0) performWallSlam(livingTarget);
            }
        }
        return success;
    }

    private void performWallSlam(LivingEntity target) {
        double xRatio = target.getX() - this.getX();
        double zRatio = target.getZ() - this.getZ();
        target.takeKnockback(3.0F, -xRatio, -zRatio);
        this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 2.0f, 0.8f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isAerialPhase()) return false;

        if (source.isOf(DamageTypes.IN_WALL) ||
                source.isOf(DamageTypes.FALL) ||
                source.isOf(DamageTypes.CRAMMING) ||
                source.isOf(DamageTypes.EXPLOSION)) {
            return false;
        }

        if (!hasTriggeredAerial1 && !isAerialPhase()) {
            if (source.getAttacker() instanceof PlayerEntity) {
                this.phase1DamageTakenCounter++;
                if (this.phase1DamageTakenCounter >= 4) {
                    this.phase1DamageTakenCounter = 0;
                    this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.5f, 1.0f);
                    this.getWorld().sendEntityStatus(this, (byte) 29);
                    if (source.getAttacker() instanceof LivingEntity attacker) {
                        performWallSlam(attacker);
                        if (!this.getWorld().isClient) ((PlayerEntity) attacker).sendMessage(Text.translatable("message.esalium.boss_blocked").formatted(Formatting.RED), true);
                    }
                    return false;
                }
            }
        }

        if (source.getAttacker() instanceof PlayerEntity player) {
            boolean isProjectile = source.getSource() instanceof ProjectileEntity;
            ItemStack heldItem = player.getMainHandStack();
            // İsimler Esalium olarak güncellendi
            boolean hasEsaliumTool = heldItem.isOf(ModItems.ESALIUM_SWORD) || heldItem.isOf(ModItems.ESALIUM_PICKAXE) || heldItem.isOf(ModItems.ESALIUM_AXE) || heldItem.isOf(ModItems.ESALIUM_SHOVEL) || heldItem.isOf(ModItems.ESALIUM_HOE);

            if (isProjectile || hasEsaliumTool) {
                if (!this.getWorld().isClient && !this.isAwakened()) {
                    this.setAwakened(true);
                    this.bossBar.setVisible(true);
                    this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.EVENT_RAID_HORN.value(), SoundCategory.HOSTILE, 1000.0f, 1.0f);
                    this.musicTicker = 0;
                    this.addVelocity(0, 0.4, 0);
                    this.setHealth(this.getMaxHealth());
                }
                return super.damage(source, amount);
            } else {
                if (!this.getWorld().isClient) {
                    player.sendMessage(Text.translatable("message.esalium.need_special_equipment"), true);
                    this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 0.5f);
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        for (ServerPlayerEntity player : this.bossBar.getPlayers()) {
            player.networkHandler.sendPacket(new StopSoundS2CPacket(ModSounds.BOSS_MUSIC.getId(), SoundCategory.RECORDS));
        }
        this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1.0f, 1.0f);
        if (!this.getWorld().isClient) {
            Entity attacker = damageSource.getAttacker();
            if (attacker instanceof PlayerEntity player) {
                // Paket yolu güncellendi
                MertEntity mert = com.epeshi.esalium.entity.ModEntities.MERT.create(this.getWorld());
                if (mert != null) {
                    mert.refreshPositionAndAngles(player.getX() + 5, player.getY(), player.getZ(), 0, 0);
                    mert.setEndGame(true);
                    this.getWorld().spawnEntity(mert);
                    player.sendMessage(Text.translatable("message.esalium.mert_return").formatted(Formatting.AQUA), true);
                }
            }
        }
    }

    public boolean isBlocking() { return this.dataTracker.get(BLOCKING); }
    public void setBlocking(boolean blocking) { this.dataTracker.set(BLOCKING, blocking); }
    public boolean isAerialPhase() { return this.dataTracker.get(AERIAL_PHASE); }
    public void setAerialPhase(boolean aerial) { this.dataTracker.set(AERIAL_PHASE, aerial); }
    public boolean isFinalPhase() { return this.dataTracker.get(FINAL_PHASE); }
    public void setFinalPhase(boolean finalPhase) { this.dataTracker.set(FINAL_PHASE, finalPhase); }
    public boolean isAwakened() { return this.dataTracker.get(AWAKENED); }
    public void setAwakened(boolean awakened) { this.dataTracker.set(AWAKENED, awakened); }

    @Override
    public void mobTick() {
        super.mobTick();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        this.bossBar.setVisible(this.isAwakened());
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) { super.onStartedTrackingBy(player); this.bossBar.addPlayer(player); }
    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) { super.onStoppedTrackingBy(player); this.bossBar.removePlayer(player); }
    @Override
    public boolean cannotDespawn() { return true; }
    @Override
    public void setCustomName(Text name) { super.setCustomName(name); this.bossBar.setName(this.getDisplayName()); }

    @Override
    protected void initGoals() {
        if (this.getNavigation() instanceof MobNavigation navigation) navigation.setCanPathThroughDoors(true);
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false) {
            @Override public boolean canStart() { return isAwakened() && !isAerialPhase() && super.canStart(); }
        });
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D) {
            @Override public boolean canStart() { return isAwakened() && !isAerialPhase() && getTarget() == null && super.canStart(); }
        });
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false, null) {
            @Override public boolean canStart() { return isAwakened() && super.canStart(); }
        });
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 500.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.0D)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.0D);
    }
}