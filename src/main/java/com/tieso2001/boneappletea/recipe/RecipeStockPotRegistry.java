package com.tieso2001.boneappletea.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class RecipeStockPotRegistry
{
    private static Map<String, RecipeStockPot> recipeMap = new HashMap<>();

    public static void addRecipe(String recipeName, RecipeStockPot recipe)
    {
        recipeMap.put(recipeName, recipe);
    }

    public static void addRecipe(String recipeName, FluidStack inputFluid, ItemStack inputItemFirst, ItemStack inputItemSecond, FluidStack outputFluid, int boilTime)
    {
        recipeMap.put(recipeName, new RecipeStockPot(inputFluid, inputItemFirst, inputItemSecond, outputFluid, boilTime));
    }

    public static Map<String, RecipeStockPot> getRecipeMap()
    {
        return recipeMap;
    }

    public static RecipeStockPot getRecipe(String recipeName)
    {
        if (recipeMap.containsKey(recipeName))
        {
            RecipeStockPot recipe = recipeMap.get(recipeName);
            return recipe;
        }
        return null;
    }

    public static RecipeStockPot getRecipe(FluidStack inputFluid, ItemStack inputItemFirst, ItemStack inputItemSecond)
    {
        for (RecipeStockPot recipe : recipeMap.values())
        {
            if (recipe.getInputFluid().getFluid() == inputFluid.getFluid())
            {
                if (recipe.getInputItemFirst().getItem() == inputItemFirst.getItem() && recipe.getInputItemSecond().getItem() == inputItemSecond.getItem() || recipe.getInputItemFirst().getItem() == inputItemSecond.getItem() && recipe.getInputItemSecond().getItem() == inputItemFirst.getItem())
                {
                    return recipe;
                }
            }
        }
        return null;
    }
}
