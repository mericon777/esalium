package com.epeshi.esalium.entity.custom;

import com.epeshi.esalium.screen.LauraScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LauraEntity extends PathAwareEntity {

    public LauraEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
        this.setCustomName(Text.literal("Laura"));
        this.setCustomNameVisible(true);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isSourceCreativePlayer() || source.isOf(DamageTypes.OUT_OF_WORLD)) {
            return super.damage(source, amount);
        }
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient && this.age % 40 == 0) {
            this.getWorld().addParticle(ParticleTypes.ANGRY_VILLAGER,
                    this.getX(), this.getY() + 2.3, this.getZ(),
                    0, 0, 0);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            if (this.getWorld().isClient) {
                // Yeni paket yolundan ekranı açıyoruz
                LauraScreen.open();
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return ActionResult.PASS;
    }
}