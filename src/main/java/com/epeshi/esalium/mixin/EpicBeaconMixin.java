package com.epeshi.esalium.mixin;

import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BeaconBlockEntity.class)
public class EpicBeaconMixin {

    @Inject(method = "applyPlayerEffects", at = @At("HEAD"), cancellable = true)
    private static void applyPowerfulEffects(World world, BlockPos pos, int beaconLevel, RegistryEntry<StatusEffect> primaryEffect, RegistryEntry<StatusEffect> secondaryEffect, CallbackInfo ci) {

        if (world.isClient || primaryEffect == null) {
            return;
        }

        Identifier blockId = world.getRegistryManager()
                .get(RegistryKeys.BLOCK)
                .getId(world.getBlockState(pos).getBlock());

        // KRİTİK: "svetariumingot" -> "esalium" olarak güncellendi
        if (blockId != null && blockId.equals(Identifier.of("esalium", "epic_beacon"))) {
            int ultraAmplifier = 19;
            double range = 150.0;
            Box box = new Box(pos).expand(range).expand(0.0, (double) world.getHeight(), 0.0);

            List<PlayerEntity> players = world.getNonSpectatingEntities(PlayerEntity.class, box);

            for (PlayerEntity player : players) {
                player.addStatusEffect(new StatusEffectInstance(primaryEffect, 400, ultraAmplifier, true, true));

                if (secondaryEffect != null) {
                    player.addStatusEffect(new StatusEffectInstance(secondaryEffect, 400, ultraAmplifier, true, true));
                }
            }
            ci.cancel();
        }
    }
}