package com.tieso2001.boneappletea.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class RecipeFermentingRegistry
{
    private static Map<String, RecipeFermenting> recipeMap = new HashMap<>();

    public static void addRecipe(String recipeName, RecipeFermenting recipe)
    {
        recipeMap.put(recipeName, recipe);
    }

    public static void addRecipe(String recipeName, ItemStack fermentItem, @Nonnull FluidStack inputFluid, FluidStack outputFluid, int fermentTime)
    {
        recipeMap.put(recipeName, new RecipeFermenting(fermentItem, inputFluid, outputFluid, fermentTime));
    }

    public static Map<String, RecipeFermenting> getRecipeMap()
    {
        return recipeMap;
    }

    public static RecipeFermenting getRecipe(String recipeName)
    {
        if (recipeMap.containsKey(recipeName))
        {
            RecipeFermenting recipe = recipeMap.get(recipeName);
            return recipe;
        }
        return null;
    }

    public static RecipeFermenting getRecipe(ItemStack fermentItem, FluidStack inputFluid)
    {
        for (RecipeFermenting recipe : recipeMap.values())
        {
            if (recipe.getInputFluid().isFluidEqual(inputFluid))
            {
                if (recipe.getFermentItem().isItemEqual(fermentItem)) return recipe;
            }
        }
        return null;
    }
}
