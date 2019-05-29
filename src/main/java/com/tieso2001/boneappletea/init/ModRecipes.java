package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.recipe.RecipeStockPotRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
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
        RecipeStockPotRegistry.addRecipe("barley_malt", new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(ModItems.BARLEY_GRAINS), ItemStack.EMPTY, null, new ItemStack(ModItems.BARLEY_MALT), 1200);
    }
}
