package com.tieso2001.boneappletea.config;

import com.tieso2001.boneappletea.util.Reference;
import net.minecraftforge.common.config.Config;

@Config(modid = Reference.MOD_ID)
public class BoneAppleTeaConfig {

    public static SubCategoryWorld World = new SubCategoryWorld();
    public static SubCategoryFermenter Fermenter = new SubCategoryFermenter();

    public static class SubCategoryWorld{
        @Config.Comment(value = "If Barley Seeds drop from breaking (tall) grass, default = true, true = enabled, false = disabled")
        public boolean enableGrassSeedsBarley = true;

        @Config.Comment(value = "Rarity of Barley Seeds when breaking (tall) grass, default = 2, higher value = less rare, lower value = more rare")
        @Config.RangeInt(min = 1)
        public int grassSeedsBarleyWeight = 2;

        @Config.Comment(value = "If Corn Kernels drop from breaking (tall) grass, default = true, true = enabled, false = disabled")
        public boolean enableGrassSeedsCorn = true;

        @Config.Comment(value = "Rarity of Corn Kernels when breaking (tall) grass, default = 2, higher value = less rare, lower value = more rare")
        @Config.RangeInt(min = 1)
        public int grassSeedsCornWeight = 2;
    }

    public static class SubCategoryFermenter{
        @Config.Comment(value = "Number of ticks it takes for one fermenting operation, default = 800 (=40 seconds), higher value = slower, lower value = faster")
        @Config.RangeInt(min = 1)
        public int fermentingTime = 800;
    }

}