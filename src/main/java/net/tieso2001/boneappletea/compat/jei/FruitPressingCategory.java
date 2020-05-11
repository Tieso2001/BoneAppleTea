package net.tieso2001.boneappletea.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.tieso2001.boneappletea.client.gui.screen.inventory.FruitPressScreen;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.tieso2001.boneappletea.recipe.FruitPressingRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FruitPressingCategory implements IRecipeCategory<FruitPressingRecipe> {

    private final IDrawable background;
    private IDrawable icon;
    private IDrawable slotDrawable;
    private IDrawable arrow;

    public FruitPressingCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(FruitPressScreen.BACKGROUND_TEXTURE, 43, 18, 91, 50).build();
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.OAK_FRUIT_PRESS.get()));
        slotDrawable = guiHelper.getSlotDrawable();
        arrow = guiHelper.drawableBuilder(FruitPressScreen.BACKGROUND_TEXTURE, 176, 0, 32, 16)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return BoneAppleTeaPlugin.FRUIT_PRESSING_UID;
    }

    @Override
    public Class<? extends FruitPressingRecipe> getRecipeClass() {
        return FruitPressingRecipe.class;
    }

    @Override
    public String getTitle() {
        return "Fruit Pressing";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(FruitPressingRecipe recipe, double mouseX, double mouseY) {
        arrow.draw(29, 17);
    }

    @Override
    public void setIngredients(FruitPressingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getIngredient()));
        ingredients.setOutput(VanillaTypes.FLUID, recipe.getResult());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FruitPressingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 0, 16);

        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(0, false, 74, 1, 16, 48, 1000, true, null);

        List<ItemStack> inputs = new ArrayList<>();
        Arrays.stream(recipe.getIngredient().getMatchingStacks()).map(itemStack -> {
            ItemStack stack = itemStack.copy();
            stack.setCount(recipe.getIngredientCount());
            return stack;
        }).forEach(inputs::add);

        itemStacks.set(0, inputs);
        fluidStacks.set(0, recipe.getResult());
    }
}
