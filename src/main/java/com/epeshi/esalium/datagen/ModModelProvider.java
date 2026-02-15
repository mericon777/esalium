package com.epeshi.esalium.datagen;

import com.epeshi.esalium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ESALIUM_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.ESALIUM_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);

        itemModelGenerator.register(ModItems.ESALIUM_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ESALIUM_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ESALIUM_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ESALIUM_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ESALIUM_HOE, Models.HANDHELD);

        itemModelGenerator.registerArmor(((ArmorItem) ModItems.ESALIUM_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.ESALIUM_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.ESALIUM_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem) ModItems.ESALIUM_BOOTS));
    }
}