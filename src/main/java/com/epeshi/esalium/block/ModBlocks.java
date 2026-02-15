package com.epeshi.esalium.block;

import com.epeshi.esalium.Esalium;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    // İsimler güncellendi: SVETARIUM -> ESALIUM
    public static final Block ESALIUM_BLOCK = registerBlock("esalium_block",
            new Block(AbstractBlock.Settings.create().strength(4f)
                    .requiresTool().sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block RAW_ESALIUM_BLOCK = registerBlock("raw_esalium_block",
            new Block(AbstractBlock.Settings.create().strength(3f)
                    .requiresTool().sounds(BlockSoundGroup.DEEPSLATE_BRICKS)));

    public static final Block ESALIUM_ORE_BLOCK = registerBlock("esalium_ore_block",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5),
                    AbstractBlock.Settings.create().strength(3f).requiresTool()));

    public static final Block ESALIUM_ORE_DEEPSLATE_BLOCK = registerBlock("esalium_ore_deepslate_block",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6),
                    AbstractBlock.Settings.create().strength(4f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE)));

    public static final Block EPIC_BEACON = registerBlock("epic_beacon",
            new EpicBeaconBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.DIAMOND_BLUE)
                    .strength(3.0f)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.METAL)
                    .luminance(state -> 15)));

    public static final Block EFES_TABLE = registerBlock("efes_table",
            new Block(AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Esalium.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Esalium.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Esalium.LOGGER.info("Registering Mod Blocks For " + Esalium.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModBlocks.ESALIUM_BLOCK);
            fabricItemGroupEntries.add(ModBlocks.RAW_ESALIUM_BLOCK);
        });
    }
}