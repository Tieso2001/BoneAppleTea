package com.tieso2001.boneappletea.compat.jei.stock_pot;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.compat.jei.PluginJEI;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class JEIRecipeCategoryStockPot implements IRecipeCategory<JEIRecipeWrapperStockPot>
{
    private final IDrawable background;
    private final IDrawable tankOverlay;

    public JEIRecipeCategoryStockPot(IGuiHelper guiHelper)
    {
        ResourceLocation location = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/gui_recipes.png");
        ResourceLocation tankOverlay = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/elements/fluidtank_overlay.png");
        this.background = guiHelper.createDrawable(location, 0, 0, 125, 57);
        this.tankOverlay = guiHelper.createDrawable(tankOverlay, 0, 0, 16, 55);
    }

    @Override
    public String getUid()
    {
        return PluginJEI.STOCK_POT_ID;
    }

    @Override
    public String getTitle()
    {
        return "Stock Pot";
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public String getModName()
    {
        return BoneAppleTea.NAME;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, JEIRecipeWrapperStockPot jeiRecipeWrapperStockPot, IIngredients iIngredients)
    {
        IGuiFluidStackGroup guiFluidStacks = iRecipeLayout.getFluidStacks();
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();

        guiFluidStacks.init(0, true, 28, 1, 16, 55, 1000, true, tankOverlay); // input fluid
        guiFluidStacks.init(1, true, 81, 1, 16, 55, 1000, true, tankOverlay); // output fluid

        guiItemStacks.init(2, false, 0, 7); // input item 1
        guiItemStacks.init(3, false, 0, 33); // input item 2
        guiItemStacks.init(4, true, 107, 20); // output item

        List<FluidStack> inputFluids = iIngredients.getInputs(VanillaTypes.FLUID).get(0);
        List<FluidStack> outputFluids = iIngredients.getOutputs(VanillaTypes.FLUID).get(0);
        List<ItemStack> inputItemsFirst = iIngredients.getInputs(VanillaTypes.ITEM).get(0);
        List<ItemStack> inputItemsSecond = iIngredients.getInputs(VanillaTypes.ITEM).get(1);
        List<ItemStack> outputItems = iIngredients.getOutputs(VanillaTypes.ITEM).get(0);

        guiFluidStacks.set(0, inputFluids);
        guiFluidStacks.set(1, outputFluids);
        guiItemStacks.set(2, inputItemsFirst);
        guiItemStacks.set(3, inputItemsSecond);
        guiItemStacks.set(4, outputItems);
    }
}
