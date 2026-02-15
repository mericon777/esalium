package com.epeshi.esalium.entity.custom;

import com.epeshi.esalium.screen.SelimScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SelimEntity extends PathAwareEntity {

    public SelimEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent(); // Despawn olmasın
        this.setCustomName(Text.literal("Selim"));
        this.setCustomNameVisible(true);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        // Sadece yaratıcı moddaki oyuncular vurabilsin
        if (source.isSourceCreativePlayer() || source.isSourceCreativePlayer()) {
            return super.damage(source, amount);
        }
        return false;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            if (this.getWorld().isClient) {
                // Ekranı aç
                SelimScreen.open();
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return ActionResult.PASS;
    }
}