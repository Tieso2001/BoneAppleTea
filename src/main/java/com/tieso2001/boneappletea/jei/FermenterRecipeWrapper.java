package com.tieso2001.boneappletea.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class FermenterRecipeWrapper implements IRecipeWrapper {

    public FermenterRecipeWrapper() {
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {

    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

}