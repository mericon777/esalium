package com.epeshi.esalium.datagen;

import com.epeshi.esalium.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // --- ESALIUM ALETLER (Diamond -> Esalium) ---
        offerEsaliumUpgrade(exporter, Items.DIAMOND_SWORD, RecipeCategory.COMBAT, ModItems.ESALIUM_SWORD);
        offerEsaliumUpgrade(exporter, Items.DIAMOND_PICKAXE, RecipeCategory.TOOLS, ModItems.ESALIUM_PICKAXE);
        offerEsaliumUpgrade(exporter, Items.DIAMOND_AXE, RecipeCategory.TOOLS, ModItems.ESALIUM_AXE);
        offerEsaliumUpgrade(exporter, Items.DIAMOND_SHOVEL, RecipeCategory.TOOLS, ModItems.ESALIUM_SHOVEL);
        offerEsaliumUpgrade(exporter, Items.DIAMOND_HOE, RecipeCategory.TOOLS, ModItems.ESALIUM_HOE);

        // --- ESALIUM ZIRHLAR ---
        offerEsaliumUpgrade(exporter, Items.DIAMOND_HELMET, RecipeCategory.COMBAT, ModItems.ESALIUM_HELMET);
        offerEsaliumUpgrade(exporter, Items.DIAMOND_CHESTPLATE, RecipeCategory.COMBAT, ModItems.ESALIUM_CHESTPLATE);
        offerEsaliumUpgrade(exporter, Items.DIAMOND_LEGGINGS, RecipeCategory.COMBAT, ModItems.ESALIUM_LEGGINGS);
        offerEsaliumUpgrade(exporter, Items.DIAMOND_BOOTS, RecipeCategory.COMBAT, ModItems.ESALIUM_BOOTS);
    }

    private void offerEsaliumUpgrade(RecipeExporter exporter, Item baseItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItems(ModItems.ESALIUM_UPGRADE_SMITHING_TEMPLATE), // Sol slot: Template
                        Ingredient.ofItems(baseItem),                                    // Orta slot: Diamond Eşya
                        Ingredient.ofItems(ModItems.ESALIUM_INGOT),                      // Sağ slot: Esalium Ingot
                        category,
                        resultItem                                                       // Sonuç: Esalium Eşya
                ).criterion(hasItem(ModItems.ESALIUM_INGOT), conditionsFromItem(ModItems.ESALIUM_INGOT))
                .offerTo(exporter, getItemPath(resultItem) + "_smithing");
    }
}