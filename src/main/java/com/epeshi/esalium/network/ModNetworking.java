package com.epeshi.esalium.network;

import com.epeshi.esalium.screen.EpicBeaconScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

public class ModNetworking {

    public static void registerPackets() {
        PayloadTypeRegistry.playC2S().register(EffectC2SPacket.ID, EffectC2SPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(EffectC2SPacket.ID, (payload, context) -> {
            context.server().execute(() -> {
                var player = context.player();

                if (player.currentScreenHandler instanceof EpicBeaconScreenHandler handler) {
                    if (handler.consumeIngot()) {
                        RegistryEntry<StatusEffect> effect = switch (payload.effectType()) {
                            case 1 -> StatusEffects.SPEED;
                            case 2 -> StatusEffects.HASTE;
                            case 3 -> StatusEffects.RESISTANCE;
                            case 4 -> StatusEffects.JUMP_BOOST;
                            case 5 -> StatusEffects.STRENGTH;
                            case 6 -> StatusEffects.REGENERATION;
                            default -> null;
                        };

                        if (effect != null) {
                            int durationToAdd = 6000;
                            int powerLevel;
                            if (effect.equals(StatusEffects.JUMP_BOOST)) {
                                powerLevel = 1;
                            } else {
                                powerLevel = 4;
                            }

                            int currentDuration = 0;
                            if (player.hasStatusEffect(effect)) {
                                currentDuration = player.getStatusEffect(effect).getDuration();
                            }

                            player.addStatusEffect(new StatusEffectInstance(
                                    effect,
                                    currentDuration + durationToAdd,
                                    powerLevel,
                                    false,
                                    true
                            ));
                        }
                    }
                }
            });
        });
    }
}