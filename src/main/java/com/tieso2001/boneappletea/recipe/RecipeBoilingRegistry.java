package com.tieso2001.boneappletea.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class RecipeBoilingRegistry
{
    private static Map<String, RecipeBoiling> recipeMap = new HashMap<>();

    public static void addRecipe(String recipeName, RecipeBoiling recipe)
    {
        recipeMap.put(recipeName, recipe);
    }

    public static void addRecipe(String recipeName, FluidStack inputFluid, ItemStack inputItemFirst, ItemStack inputItemSecond, FluidStack outputFluid, ItemStack outputItem, int boilTime)
    {
        recipeMap.put(recipeName, new RecipeBoiling(inputFluid, inputItemFirst, inputItemSecond, outputFluid, outputItem, boilTime));
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

    public static RecipeBoiling getRecipe(FluidStack inputFluid, ItemStack inputItemFirst, ItemStack inputItemSecond)
    {
        for (RecipeBoiling recipe : recipeMap.values())
        {
            if (recipe.getInputFluid().getFluid() == inputFluid.getFluid())
            {
                if ((!recipe.getInputItemFirst().isEmpty() || !recipe.getInputItemSecond().isEmpty()) && (!inputItemFirst.isEmpty() || !inputItemSecond.isEmpty()))
                {
                    if (recipe.getInputItemFirst().isEmpty() && ((recipe.getInputItemSecond().isItemEqual(inputItemFirst) && inputItemSecond.isEmpty()) || (recipe.getInputItemSecond().isItemEqual(inputItemSecond) && inputItemFirst.isEmpty())))
                    {
                        return recipe;
                    }
                    else if (recipe.getInputItemSecond().isEmpty() && ((recipe.getInputItemFirst().isItemEqual(inputItemFirst) && inputItemSecond.isEmpty()) || (recipe.getInputItemSecond().isItemEqual(inputItemSecond) && inputItemFirst.isEmpty())))
                    {
                        return recipe;
                    }
                    else if (recipe.getInputItemFirst().isItemEqual(inputItemFirst) && recipe.getInputItemSecond().isItemEqual(inputItemSecond) || recipe.getInputItemFirst().isItemEqual(inputItemSecond) && recipe.getInputItemSecond().isItemEqual(inputItemFirst))
                    {
                        return recipe;
                    }
                }
            }
        }
        return null;
    }
}
