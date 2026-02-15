package com.epeshi.esalium;

import com.epeshi.esalium.datagen.ModItemTagProvider;
import com.epeshi.esalium.datagen.ModModelProvider;
import com.epeshi.esalium.datagen.ModRecipeProvider;
import com.epeshi.esalium.world.ModConfiguredFeatures;
import com.epeshi.esalium.world.ModPlacedFeatures;
import com.epeshi.esalium.world.ModWorldGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class EsaliumDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModWorldGenerator::new);
        pack.addProvider(ModItemTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
}