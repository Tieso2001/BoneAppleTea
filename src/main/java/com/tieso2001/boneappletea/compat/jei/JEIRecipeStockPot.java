package com.tieso2001.boneappletea.compat.jei;

import com.tieso2001.boneappletea.recipe.RecipeStockPot;

public class JEIRecipeStockPot
{
    private final RecipeStockPot recipe;

    public JEIRecipeStockPot(RecipeStockPot recipe)
    {
        this.recipe = recipe;
    }

    public RecipeStockPot getRecipe()
    {
        return recipe;
    }
}
