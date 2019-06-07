package com.tieso2001.boneappletea.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class RecipeBoilingRegistry
{
    private static Map<String, RecipeBoiling> recipeMap = new HashMap<>();

    public static void addRecipe(String recipeName, RecipeBoiling recipe)
    {
        recipeMap.put(recipeName, recipe);
    }

    public static void addRecipe(String recipeName, ItemStack inputItem, @Nonnull FluidStack inputFluid, ItemStack outputItem, FluidStack outputFluid, int boilTime)
    {
        recipeMap.put(recipeName, new RecipeBoiling(inputItem, inputFluid, outputItem, outputFluid, boilTime));
    }

    public static Map<String, RecipeBoiling> getRecipeMap()
    {
        return recipeMap;
    }

    public static RecipeBoiling getRecipe(String recipeName)
    {
        if (recipeMap.containsKey(recipeName))
        {
            RecipeBoiling recipe = recipeMap.get(recipeName);
            return recipe;
        }
        return null;
    }

    public static RecipeBoiling getRecipe(ItemStack inputItem, FluidStack inputFluid)
    {
        for (RecipeBoiling recipe : recipeMap.values())
        {
            if (recipe.getInputFluid().isFluidEqual(inputFluid))
            {
                if (recipe.getInputItem().isEmpty() && inputItem.isEmpty() || recipe.getInputItem().isItemEqual(inputItem)) return recipe;
            }
        }
        return null;
    }
}
