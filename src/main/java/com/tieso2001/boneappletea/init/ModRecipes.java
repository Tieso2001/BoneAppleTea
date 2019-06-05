package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.recipe.RecipeStockPotRegistry;
import com.tieso2001.boneappletea.recipe.RecipeFermentingRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{
    public static void initRecipes()
    {
        initGrassSeeds();
        initSmelting();
        initBoiling();
        initFermenting();
    }

    public static void initGrassSeeds()
    {
        MinecraftForge.addGrassSeed(new ItemStack(ModItems.BARLEY_SEEDS), 5);
        MinecraftForge.addGrassSeed(new ItemStack(ModItems.CORN_KERNELS), 5);
        MinecraftForge.addGrassSeed(new ItemStack(ModItems.HOPS_SEEDS), 5);
    }

    public static void initSmelting()
    {
        GameRegistry.addSmelting(ModItems.CORN, new ItemStack(ModItems.ROASTED_CORN), 0.35F);
        GameRegistry.addSmelting(ModItems.CORN_KERNELS, new ItemStack(ModItems.POPCORN), 0.35F);
    }

    public static void initBoiling()
    {
        RecipeStockPotRegistry.addRecipe("barley_malt", new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(ModItems.BARLEY_GRAINS), ItemStack.EMPTY, null, new ItemStack(ModItems.BARLEY_MALT), 1200);
        RecipeStockPotRegistry.addRecipe("sweet_wort", new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(ModItems.BARLEY_MALT_CRUSHED), ItemStack.EMPTY, new FluidStack(ModFluids.SWEET_WORT, 1000), ItemStack.EMPTY, 2400);
        RecipeStockPotRegistry.addRecipe("hopped_wort", new FluidStack(ModFluids.SWEET_WORT, 1000), new ItemStack(ModItems.HOPS), new ItemStack(ModItems.HOPS), new FluidStack(ModFluids.HOPPED_WORT, 1000), ItemStack.EMPTY, 2400);
        RecipeStockPotRegistry.addRecipe("bonemeal_to_yeast", new FluidStack(ModFluids.SWEET_WORT, 1000), new ItemStack(Items.DYE, 1, 15), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(ModItems.YEAST), 6000);
        RecipeStockPotRegistry.addRecipe("yeast_to_yeast", new FluidStack(ModFluids.SWEET_WORT, 1000), new ItemStack(ModItems.YEAST), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 1000), new ItemStack(ModItems.YEAST, 4), 3000);
    }

    public static void initFermenting()
    {
        RecipeFermentingRegistry.addRecipe("beer", new ItemStack(ModItems.YEAST), new FluidStack(ModFluids.HOPPED_WORT, 100), new FluidStack(FluidRegistry.LAVA, 100), 100);
    }
}
