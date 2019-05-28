package com.tieso2001.boneappletea.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JEIRecipeWrapperStockPot implements ICraftingRecipeWrapper
{
    private final JEIRecipeStockPot recipe;

    public JEIRecipeWrapperStockPot(JEIRecipeStockPot recipe)
    {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients iIngredients)
    {
        iIngredients.setOutput(VanillaTypes.FLUID, recipe.getRecipe().getOutputFluid());
        iIngredients.setInput(VanillaTypes.FLUID, recipe.getRecipe().getInputFluid());

        iIngredients.setOutput(VanillaTypes.ITEM, recipe.getRecipe().getOutputItem());
        List<ItemStack> inputItems = new ArrayList<>();
        inputItems.add(recipe.getRecipe().getInputItemFirst());
        inputItems.add(recipe.getRecipe().getInputItemSecond());
        iIngredients.setInputs(VanillaTypes.ITEM,  inputItems);
    }
}
