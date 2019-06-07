package com.tieso2001.boneappletea.compat.jei.boiling;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;

public class JEIRecipeWrapperBoiling implements ICraftingRecipeWrapper
{
    private final JEIRecipeBoiling recipe;

    public JEIRecipeWrapperBoiling(JEIRecipeBoiling recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients iIngredients)
    {
        iIngredients.setInput(VanillaTypes.ITEM, recipe.getRecipe().getInputItem());
        iIngredients.setInput(VanillaTypes.FLUID, recipe.getRecipe().getInputFluid());
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.getRecipe().getOutputItem());
        iIngredients.setOutput(VanillaTypes.FLUID, recipe.getRecipe().getOutputFluid());
    }
}
