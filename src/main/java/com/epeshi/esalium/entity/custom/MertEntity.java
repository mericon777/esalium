package com.epeshi.esalium.entity.custom;

import com.epeshi.esalium.screen.MertScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MertEntity extends PathAwareEntity {
    private static final TrackedData<Boolean> IS_END_GAME = DataTracker.registerData(MertEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public MertEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(Text.literal("Mert"));
        this.setCustomNameVisible(true);
        this.setPersistent();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_END_GAME, false);
    }

    public boolean isEndGame() { return this.dataTracker.get(IS_END_GAME); }
    public void setEndGame(boolean endGame) { this.dataTracker.set(IS_END_GAME, endGame); }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient && isEndGame()) {
            PlayerEntity player = this.getWorld().getClosestPlayer(this, 20);
            if (player != null && this.distanceTo(player) > 3.0) {
                this.getNavigation().startMovingTo(player, 1.0);
            }
        }
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30D);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            if (this.getWorld().isClient) {
                MertScreen.open(this.isEndGame());
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return ActionResult.PASS;
    }
}