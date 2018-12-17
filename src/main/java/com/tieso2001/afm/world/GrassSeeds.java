package com.tieso2001.afm.world;

import com.tieso2001.afm.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class GrassSeeds {

    public static void register() {
        addGrassSeed(ModItems.BARLEY_SEEDS, 1);
        addGrassSeed(ModItems.CORN_KERNELS, 1);
    }

    public static void addGrassSeed(Item seed, int weight) {
        MinecraftForge.addGrassSeed(new ItemStack(seed), weight);
    }

}
