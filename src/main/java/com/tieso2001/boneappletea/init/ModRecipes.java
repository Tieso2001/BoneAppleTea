package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.recipe.RecipeBoilingRegistry;
import com.tieso2001.boneappletea.recipe.RecipeFermenting;
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
        RecipeBoilingRegistry.addRecipe("barley_malt", new ItemStack(ModItems.BARLEY_GRAINS), new FluidStack(FluidRegistry.WATER, 250), new ItemStack(ModItems.BARLEY_MALT), null, 1000);
        RecipeBoilingRegistry.addRecipe("sweet_wort", new ItemStack(ModItems.BARLEY_MALT_CRUSHED), new FluidStack(FluidRegistry.WATER, 250), ItemStack.EMPTY, new FluidStack(ModFluids.SWEET_WORT, 250), 1000);
        RecipeBoilingRegistry.addRecipe("hopped_wort", new ItemStack(ModItems.HOPS), new FluidStack(ModFluids.SWEET_WORT, 250), ItemStack.EMPTY, new FluidStack(ModFluids.HOPPED_WORT, 250), 500);
        RecipeBoilingRegistry.addRecipe("yeast_creation", new ItemStack(Items.DYE, 1, 15), new FluidStack(FluidRegistry.WATER, 250), new ItemStack(ModItems.YEAST), null, 8000);
        RecipeBoilingRegistry.addRecipe("yeast_production", new ItemStack(ModItems.YEAST), new FluidStack(ModFluids.SWEET_WORT, 125), new ItemStack(ModItems.YEAST, 2), new FluidStack(FluidRegistry.WATER, 125), 2000);
    }

    public static void initFermenting()
    {
        RecipeFermentingRegistry.addRecipe("uncarbonated_beer", new ItemStack(ModItems.YEAST), new FluidStack(ModFluids.HOPPED_WORT, 125), new FluidStack(ModFluids.UNCARBONATED_BEER, 125), 6000);
        RecipeFermentingRegistry.addRecipe("beer", new ItemStack(Items.SUGAR), new FluidStack(ModFluids.UNCARBONATED_BEER, 250), new FluidStack(ModFluids.BEER, 250), 3000);
    }
}
