package de.dafuqs.spectrum.deeper_down;

import com.google.common.collect.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.block.*;
import net.minecraft.structure.rule.*;
import net.minecraft.util.*;
import net.minecraft.util.math.intprovider.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.heightprovider.*;
import net.minecraft.world.gen.placementmodifier.*;

import static de.dafuqs.spectrum.helpers.WorldgenHelper.*;
import static net.minecraft.world.gen.feature.OreConfiguredFeatures.*;

public class DDPlacedFeatures {

    public static final RuleTest BLACKSLAG_ORE_REPLACEABLE_TEST = new TagMatchRuleTest(SpectrumBlockTags.BLACKSLAG_ORE_REPLACEABLES);

    public static void register() {
        registerSpectrumOres();
        registerVanillaOres();
        registerGeodes();
        registerDecorators();
    }

    public static void registerAndAddOreFeature(String identifier, OreFeatureConfig oreFeatureConfig, PlacementModifier... placementModifiers) {
        Identifier id = SpectrumCommon.locate(identifier);
        registerConfiguredAndPlacedFeature(id, Feature.ORE, oreFeatureConfig, placementModifiers);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));
    }

    public static void registerSpectrumOres() {
        registerAndAddOreFeature("dd_malachite_ore",
                new OreFeatureConfig(ImmutableList.of(
                        OreFeatureConfig.createTarget(BLACKSLAG_ORE_REPLACEABLE_TEST, SpectrumBlocks.BLACKSLAG_MALACHITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, SpectrumBlocks.MALACHITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, SpectrumBlocks.DEEPSLATE_MALACHITE_ORE.getDefaultState())
                ), 7, 0.75F),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(4), YOffset.aboveBottom(64)),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(32), YOffset.belowTop(256)),
                CountPlacementModifier.of(40));

        registerAndAddOreFeature("dd_shimmerstone_ore",
                new OreFeatureConfig(ImmutableList.of(
                        OreFeatureConfig.createTarget(BLACKSLAG_ORE_REPLACEABLE_TEST, SpectrumBlocks.BLACKSLAG_SHIMMERSTONE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, SpectrumBlocks.SHIMMERSTONE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, SpectrumBlocks.DEEPSLATE_SHIMMERSTONE_ORE.getDefaultState())
                ), 7),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(16), YOffset.belowTop(128)),
                CountPlacementModifier.of(24));
    }

    public static void registerVanillaOres() {
        registerAndAddOreFeature("dd_iron_ore",
                new OreFeatureConfig(ImmutableList.of(
                        OreFeatureConfig.createTarget(BLACKSLAG_ORE_REPLACEABLE_TEST, SpectrumBlocks.BLACKSLAG_IRON_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, Blocks.IRON_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_IRON_ORE.getDefaultState())
                ), 25),
                HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(192), YOffset.aboveBottom(384)),
                CountPlacementModifier.of(20));

        registerAndAddOreFeature("dd_iron_ore2",
                new OreFeatureConfig(ImmutableList.of(
                        OreFeatureConfig.createTarget(BLACKSLAG_ORE_REPLACEABLE_TEST, SpectrumBlocks.BLACKSLAG_IRON_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, Blocks.IRON_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_IRON_ORE.getDefaultState())
                ), 25),
                HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(4), YOffset.belowTop(4)),
                CountPlacementModifier.of(10));

        registerAndAddOreFeature("dd_gold_ore",
                new OreFeatureConfig(ImmutableList.of(
                        OreFeatureConfig.createTarget(BLACKSLAG_ORE_REPLACEABLE_TEST, SpectrumBlocks.BLACKSLAG_GOLD_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, Blocks.GOLD_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_GOLD_ORE.getDefaultState())
                ), 15),
                HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(128), YOffset.aboveBottom(256)),
                CountPlacementModifier.of(20));

        registerAndAddOreFeature("dd_gold_ore2",
                new OreFeatureConfig(ImmutableList.of(
                        OreFeatureConfig.createTarget(BLACKSLAG_ORE_REPLACEABLE_TEST, SpectrumBlocks.BLACKSLAG_GOLD_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, Blocks.GOLD_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_GOLD_ORE.getDefaultState())
                ), 15),
                HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(4), YOffset.belowTop(4)),
                CountPlacementModifier.of(8));

        registerAndAddOreFeature("dd_diamond_ore",
                new OreFeatureConfig(ImmutableList.of(
                        OreFeatureConfig.createTarget(BLACKSLAG_ORE_REPLACEABLE_TEST, SpectrumBlocks.BLACKSLAG_DIAMOND_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, Blocks.DIAMOND_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState())
                ), 9),
                HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(0), YOffset.aboveBottom(256)),
                CountPlacementModifier.of(3));

        registerAndAddOreFeature("dd_emerald_ore",
                new OreFeatureConfig(ImmutableList.of(
                        OreFeatureConfig.createTarget(DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_EMERALD_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(BLACKSLAG_ORE_REPLACEABLE_TEST, SpectrumBlocks.BLACKSLAG_EMERALD_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, Blocks.EMERALD_ORE.getDefaultState())
                ), 18),
                HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(4), YOffset.aboveBottom(128)),
                RarityFilterPlacementModifier.of(3));
    }

    public static void registerGeodes() {
        Identifier MOONSTONE_GEODE_IDENTIFIER = SpectrumCommon.locate("moonstone_geode");
        registerConfiguredAndPlacedFeature(
                MOONSTONE_GEODE_IDENTIFIER,
                DDConfiguredFeatures.MOONSTONE_GEODE,
                RarityFilterPlacementModifier.of(SpectrumCommon.CONFIG.MoonstoneGeodeChunkChance),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(16), YOffset.aboveBottom(128)),
                BiomePlacementModifier.of()
        );

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.LOCAL_MODIFICATIONS, RegistryKey.of(Registry.PLACED_FEATURE_KEY, MOONSTONE_GEODE_IDENTIFIER));
    }

    public static void registerDecorators() {
        Identifier id = SpectrumCommon.locate("dd_tuff");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.TUFF_DISK, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()), CountPlacementModifier.of(32));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_gravel");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.GRAVEL_DISK, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()), CountPlacementModifier.of(32));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_infested");
        registerPlacedFeature(id, OreConfiguredFeatures.ORE_INFESTED, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()), CountPlacementModifier.of(32));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_granite");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.GRANITE_DISK, HeightRangePlacementModifier.uniform(YOffset.belowTop(192), YOffset.belowTop(64)), CountPlacementModifier.of(8));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_diorite");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.DIORITE_DISK, HeightRangePlacementModifier.uniform(YOffset.belowTop(128), YOffset.getTop()), CountPlacementModifier.of(8));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_andesite");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.ANDESITE_DISK, HeightRangePlacementModifier.uniform(YOffset.belowTop(128), YOffset.getTop()), CountPlacementModifier.of(8));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_bedrock");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.DOWNSTONE_DISK, HeightRangePlacementModifier.uniform(YOffset.belowTop(128), YOffset.getTop()), CountPlacementModifier.of(16));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_bedrock_sloped");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.DOWNSTONE_DISK_SLOPED, HeightRangePlacementModifier.of(VeryBiasedToBottomHeightProvider.create(YOffset.getBottom(), YOffset.aboveBottom(50), 24)), CountPlacementModifier.of(16));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_stone");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.STONE_DISK, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()), CountPlacementModifier.of(32));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_springs");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.WATER_SPRING, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()), CountPlacementModifier.of(256));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_lichen");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.GLOW_LICHEN, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.getTop()), CountPlacementModifier.of(UniformIntProvider.create(84, 127)), SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG, -2147483648, -13));
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));

        id = SpectrumCommon.locate("dd_bismuth");
        registerConfiguredAndPlacedFeature(id, DDConfiguredFeatures.BISMUTH_BUDS, HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.aboveBottom(192)), RarityFilterPlacementModifier.of(6), CountPlacementModifier.of(UniformIntProvider.create(64, 96)), SquarePlacementModifier.of());
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DDDimension.DEEPER_DOWN_BIOME_KEY), GenerationStep.Feature.UNDERGROUND_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));
    }

}