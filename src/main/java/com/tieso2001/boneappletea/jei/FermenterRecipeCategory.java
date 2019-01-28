package com.tieso2001.boneappletea.jei;

import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.util.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class FermenterRecipeCategory implements IRecipeCategory<FermenterRecipeWrapper> {

    private final IDrawable background;
    private final IDrawable icon;

    private static final int yeastSlot = 0;
    private static final int inputSlot = 1;
    private static final int bottleSlot1 = 2;
    private static final int bottleSlot2 = 3;
    private static final int bottleSlot3 = 4;
    private static final int outputSlot = 5;

    public FermenterRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei_boneappletea.png");
        background = guiHelper.createDrawable(location, 0, 0, 125, 60);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.FERMENTER));
    }

    @Nonnull
    @Override
    public String getUid() {
        return JEIPlugin.FERMENTER_ID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Fermenter";
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FermenterRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(yeastSlot, true, 0, 1);
        guiItemStacks.init(inputSlot, true, 62, 1);
        guiItemStacks.init(bottleSlot1, true, 39, 35);
        guiItemStacks.init(bottleSlot2, true, 62, 42);
        guiItemStacks.init(bottleSlot3, true, 85, 35);
        guiItemStacks.init(outputSlot, false, 107, 1);

        guiItemStacks.set(ingredients);
    }

    @Override
    public String getModName() {
        return Reference.NAME;
    }

}