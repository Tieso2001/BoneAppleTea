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
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.tieso2001.boneappletea.client.gui.screen.inventory.CaskScreen;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.tieso2001.boneappletea.recipe.BrewingRecipe;

import java.util.*;
import java.util.stream.Collectors;

public class BrewingCategory implements IRecipeCategory<BrewingRecipe> {

    private final IDrawable background;
    private IDrawable icon;
    private IDrawable slotDrawable;
    private IDrawable arrow;

    public BrewingCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(CaskScreen.BACKGROUND_TEXTURE, 37, 18, 102, 50).build();
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.OAK_CASK.get()));
        slotDrawable = guiHelper.getSlotDrawable();
        arrow = guiHelper.drawableBuilder(CaskScreen.BACKGROUND_TEXTURE, 176, 0, 25, 16)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return BoneAppleTeaPlugin.BREWING_UID;
    }

    @Override
    public Class<? extends BrewingRecipe> getRecipeClass() {
        return BrewingRecipe.class;
    }

    @Override
    public String getTitle() {
        return "Brewing";
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
    public void draw(BrewingRecipe recipe, double mouseX, double mouseY) {
        arrow.draw(50, 17);
    }

    @Override
    public void setIngredients(BrewingRecipe recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, recipe.getIngredientFluid());
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getIngredientMap().keySet().stream()
                .map(ingredient -> Arrays.asList(ingredient.getMatchingStacks()))
                .collect(Collectors.toList()));
        ingredients.setOutput(VanillaTypes.FLUID, recipe.getResult());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BrewingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        itemStacks.init(0, true, 24, 5);
        itemStacks.init(1, true, 24, 28);

        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(0, true, 1, 1, 16, 48, 4000, true, null);
        fluidStacks.init(1, false, 85, 1, 16, 48, 4000, true, null);

        int i = 0;
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredientMap().entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer count = entry.getValue();
            itemStacks.set(i++, Arrays.stream(ingredient.getMatchingStacks())
                    .map(stack -> {
                        ItemStack itemStack = stack.copy();
                        itemStack.setCount(count);
                        return itemStack;
                    })
                    .collect(Collectors.toList())
            );
        }
        fluidStacks.set(0, recipe.getIngredientFluid());
        fluidStacks.set(1, recipe.getResult());
    }
}
