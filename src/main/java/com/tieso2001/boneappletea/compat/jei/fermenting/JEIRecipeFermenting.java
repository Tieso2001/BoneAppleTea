package com.tieso2001.boneappletea.compat.jei.fermenting;

import com.tieso2001.boneappletea.recipe.RecipeFermenting;

public class JEIRecipeFermenting
{
    private final RecipeFermenting recipe;

    public JEIRecipeFermenting(RecipeFermenting recipe)
    {
        this.recipe = recipe;
    }

    public RecipeFermenting getRecipe()
    {
        return recipe;
    }
}
