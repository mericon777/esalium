package com.epeshi.esalium.world;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ModConfiguredFeatures {
    // İsim güncellendi: esalium_ore
    public static final RegistryKey<ConfiguredFeature<?, ?>> ESALIUM_ORE_KEY = registerKey("esalium_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        // Blok isimleri güncellendi (Esalium Ore)
        List<OreFeatureConfig.Target> overworldEsaliumOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.ESALIUM_ORE_BLOCK.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.ESALIUM_ORE_DEEPSLATE_BLOCK.getDefaultState()));

        // 4 = Damar büyüklüğü
        register(context, ESALIUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldEsaliumOres, 4));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Esalium.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}