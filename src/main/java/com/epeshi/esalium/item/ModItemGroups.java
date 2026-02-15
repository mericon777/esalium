package com.epeshi.esalium.item;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ESALIUM_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Esalium.MOD_ID, "esalium"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.ESALIUM_INGOT))
                    .displayName(Text.translatable("itemgroup.esalium.esalium_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.RAW_ESALIUM_BLOCK);
                        entries.add(ModBlocks.ESALIUM_BLOCK);

                        entries.add(ModBlocks.ESALIUM_ORE_BLOCK);
                        entries.add(ModBlocks.ESALIUM_ORE_DEEPSLATE_BLOCK);
                        entries.add(ModBlocks.EPIC_BEACON);

                        entries.add(ModItems.ESALIUM_INGOT);
                        entries.add(ModItems.ESALIUM_ORE);

                        entries.add(ModItems.ESALIUM_UPGRADE_SMITHING_TEMPLATE);
                        entries.add(ModItems.ESALIUM_SWORD);
                        entries.add(ModItems.ESALIUM_PICKAXE);
                        entries.add(ModItems.ESALIUM_SHOVEL);
                        entries.add(ModItems.ESALIUM_AXE);
                        entries.add(ModItems.ESALIUM_HOE);
                        entries.add(ModItems.ESALIUM_CHESTPLATE);
                        entries.add(ModItems.ESALIUM_HELMET);
                        entries.add(ModItems.ESALIUM_LEGGINGS);
                        entries.add(ModItems.ESALIUM_BOOTS);
                        entries.add(ModBlocks.EFES_TABLE);
                        entries.add(ModItems.EMPERROR_PU_SPAWN_EGG);
                        // Sveta Heart -> Melodi Heart
                        entries.add(ModItems.MELODI_HEART);
                        entries.add(ModItems.EMPERRORS_TEARS);

                    }).build());

    public static void registerItemGroups() {
        Esalium.LOGGER.info("Registering Item Groups for " + Esalium.MOD_ID);
    }
}