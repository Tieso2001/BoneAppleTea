package com.tieso2001.boneappletea.compat.jei.boiling;

import com.tieso2001.boneappletea.recipe.RecipeBoiling;

public class JEIRecipeBoiling
{
    private final RecipeBoiling recipe;

    public JEIRecipeBoiling(RecipeBoiling recipe)
    {
        this.recipe = recipe;
    }

    public RecipeBoiling getRecipe()
    {
        return recipe;
    }
}
