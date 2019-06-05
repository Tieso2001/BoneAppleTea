package com.tieso2001.boneappletea.compat.jei.fermenting;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;

public class JEIRecipeWrapperFermenting implements ICraftingRecipeWrapper
{
    private final JEIRecipeFermenting recipe;

    public JEIRecipeWrapperFermenting(JEIRecipeFermenting recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients iIngredients)
    {
        iIngredients.setInput(VanillaTypes.ITEM, recipe.getRecipe().getFermentItem());
        iIngredients.setInput(VanillaTypes.FLUID, recipe.getRecipe().getInputFluid());
        iIngredients.setOutput(VanillaTypes.FLUID, recipe.getRecipe().getOutputFluid());
    }
}
