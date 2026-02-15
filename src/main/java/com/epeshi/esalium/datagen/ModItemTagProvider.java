package com.epeshi.esalium.datagen;

import com.epeshi.esalium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.SWORDS).add(ModItems.ESALIUM_SWORD);
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(ModItems.ESALIUM_PICKAXE);
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(ModItems.ESALIUM_SHOVEL);
        getOrCreateTagBuilder(ItemTags.AXES).add(ModItems.ESALIUM_AXE);
        getOrCreateTagBuilder(ItemTags.HOES).add(ModItems.ESALIUM_HOE);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ESALIUM_HELMET)
                .add(ModItems.ESALIUM_CHESTPLATE)
                .add(ModItems.ESALIUM_LEGGINGS)
                .add(ModItems.ESALIUM_BOOTS);
    }
}