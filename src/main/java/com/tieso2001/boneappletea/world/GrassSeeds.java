package com.tieso2001.boneappletea.world;

import com.tieso2001.boneappletea.config.BoneAppleTeaConfig;
import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class GrassSeeds {

    public static void register() {
        // Default: grassSeedsBarley = true, grassSeedsBarleyWeight = 2
        if (BoneAppleTeaConfig.World.enableGrassSeedsBarley) { addGrassSeed(ModItems.BARLEY_SEEDS, BoneAppleTeaConfig.World.grassSeedsBarleyWeight); }

        // Default: grassSeedsCorn = true, grassSeedsCornWeight = 2
        if (BoneAppleTeaConfig.World.enableGrassSeedsCorn) { addGrassSeed(ModItems.CORN_KERNELS, BoneAppleTeaConfig.World.grassSeedsCornWeight); }
    }

    private static void addGrassSeed(Item seed, int weight) {
        MinecraftForge.addGrassSeed(new ItemStack(seed), weight);
    }

}