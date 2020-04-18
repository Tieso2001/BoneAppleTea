package net.tieso2001.boneappletea.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class WorldGen {

    public static void setupWorldGen() {
        addBarley(Biomes.RIVER);
        addBarley(Biomes.PLAINS);
        addBarley(Biomes.SUNFLOWER_PLAINS);
        addBarley(Biomes.FOREST);
        addBarley(Biomes.WOODED_HILLS);
        addBarley(Biomes.FLOWER_FOREST);
        addBarley(Biomes.BIRCH_FOREST);
        addBarley(Biomes.BIRCH_FOREST_HILLS);
        addBarley(Biomes.TALL_BIRCH_FOREST);
        addBarley(Biomes.TALL_BIRCH_HILLS);
    }

    public static void addBarley(Biome biome) {
        //biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(ModFeatures.BARLEY, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(6)));
    }
}
