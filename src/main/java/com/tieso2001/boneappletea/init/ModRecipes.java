package com.tieso2001.boneappletea.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{
    public static void initGrassSeeds()
    {
        MinecraftForge.addGrassSeed(new ItemStack(ModItems.BARLEY_SEEDS), 5);
        MinecraftForge.addGrassSeed(new ItemStack(ModItems.CORN_KERNELS), 5);
    }

    public static void initSmelting()
    {
        GameRegistry.addSmelting(ModItems.CORN, new ItemStack(ModItems.ROASTED_CORN), 0.35F);
        GameRegistry.addSmelting(ModItems.CORN_KERNELS, new ItemStack(ModItems.POPCORN), 0.35F);
    }

    public static void initBoiling()
    {

    }
}
