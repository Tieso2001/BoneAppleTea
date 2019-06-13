package com.tieso2001.boneappletea.compat.jei.boiling;

import com.tieso2001.boneappletea.util.GuiUtil;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.client.Minecraft;

import java.awt.*;

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

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {
        String time = GuiUtil.ticksToTime(recipe.getRecipe().getBoilTime());
        minecraft.fontRenderer.drawString(time, 126 - minecraft.fontRenderer.getStringWidth(time), 62, Color.gray.getRGB());
    }
}
