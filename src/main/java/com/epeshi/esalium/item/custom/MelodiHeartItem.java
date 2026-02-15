package com.epeshi.esalium.item.custom;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.villager.ModVillagers;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;

import java.util.List;

public class MelodiHeartItem extends Item {
    public MelodiHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (Screen.hasShiftDown()) {
            // İsimler Melodi olarak güncellendi
            tooltip.add(Text.translatable("item.esalium.melodi_heart.tooltip.story").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
            tooltip.add(Text.translatable("item.esalium.melodi_heart.tooltip.instruction.1").formatted(Formatting.GOLD));
            tooltip.add(Text.translatable("item.esalium.melodi_heart.tooltip.instruction.2").formatted(Formatting.GOLD, Formatting.BOLD));
            tooltip.add(Text.translatable("item.esalium.melodi_heart.tooltip.instruction.3").formatted(Formatting.GOLD));
        } else {
            tooltip.add(Text.translatable("tooltip.esalium.shift_required").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos().up();
        PlayerEntity player = context.getPlayer();

        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;

            // 1. Efe'yi Bul
            Box box = new Box(pos).expand(10);
            List<VillagerEntity> villagers = serverWorld.getEntitiesByClass(VillagerEntity.class, box, entity -> true);

            VillagerEntity efeEntity = null;
            for (VillagerEntity villager : villagers) {
                if (villager.getVillagerData().getProfession() == ModVillagers.EFE) {
                    efeEntity = villager;
                    break;
                }
            }

            if (efeEntity != null) {
                // --- MELODI OLUŞTURMA ---
                VillagerEntity melodi = EntityType.VILLAGER.create(serverWorld);
                if (melodi != null) {
                    melodi.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);

                    // A) Meslek ve İsim (MELODI)
                    VillagerData melodiData = melodi.getVillagerData().withProfession(ModVillagers.MELODI).withLevel(1);
                    melodi.setVillagerData(melodiData);
                    // İsim Melodi oldu
                    melodi.setCustomName(Text.translatable("entity.esalium.melodi").formatted(Formatting.LIGHT_PURPLE));
                    melodi.setCustomNameVisible(true);
                    melodi.setPersistent();

                    melodi.getOffers();

                    melodi.setVillagerData(melodiData.withLevel(5));
                    melodi.setExperience(250);

                    serverWorld.spawnEntity(melodi);

                    forceEfeLevelUp(efeEntity);

                    serverWorld.spawnParticles(ParticleTypes.HEART, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.1);
                    serverWorld.spawnParticles(ParticleTypes.HAPPY_VILLAGER, efeEntity.getX(), efeEntity.getBodyY(0.5) + 0.5, efeEntity.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
                    serverWorld.playSound(null, pos, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1.0f, 1.0f);

                    efeEntity.lookAtEntity(melodi, 30, 30);
                    melodi.lookAtEntity(efeEntity, 30, 30);

                    if (player instanceof ServerPlayerEntity serverPlayer) {
                        Identifier advId = Identifier.of(Esalium.MOD_ID, "reunion");
                        AdvancementEntry entry = serverPlayer.getServer().getAdvancementLoader().get(advId);
                        if (entry != null) {
                            serverPlayer.getAdvancementTracker().grantCriterion(entry, "impossible");
                        }
                    }

                    if (player != null && !player.isCreative()) {
                        context.getStack().decrement(1);
                    }
                    if (player != null) {
                        player.sendMessage(Text.translatable("message.esalium.reunion_success").formatted(Formatting.GOLD), true);
                    }
                }
                return ActionResult.SUCCESS;
            } else {
                if (player != null) {
                    player.sendMessage(Text.translatable("message.esalium.efe_not_found").formatted(Formatting.RED), true);
                }
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.SUCCESS;
    }

    private void forceEfeLevelUp(VillagerEntity villager) {
        int startLevel = villager.getVillagerData().getLevel();
        for (int i = startLevel; i < 5; i++) {
            int nextLevel = i + 1;
            villager.setVillagerData(villager.getVillagerData().withLevel(nextLevel));
            int xpToSet = switch(nextLevel) {
                case 2 -> 10;
                case 3 -> 70;
                case 4 -> 150;
                case 5 -> 250;
                default -> 250;
            };
            villager.setExperience(xpToSet);
            villager.getOffers();
        }
    }
}