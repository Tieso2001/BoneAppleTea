package com.tieso2001.boneappletea.compat.jei.fermenting;

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

public class JEIRecipeCategoryFermenting implements IRecipeCategory<JEIRecipeWrapperFermenting>
{
    private final IDrawable background;
    private final IDrawable tankOverlay;

    public JEIRecipeCategoryFermenting(IGuiHelper guiHelper)
    {
        ResourceLocation location = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/wooden_fermenting_barrel.png");
        ResourceLocation tankOverlay = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/elements/fluidtank_overlay.png");
        this.background = guiHelper.createDrawable(location, 25, 14, 99, 57);
        this.tankOverlay = guiHelper.createDrawable(tankOverlay, 0, 0, 16, 55);
    }

    @Override
    public String getUid()
    {
        return PluginJEI.FERMENTING_ID;
    }

    @Override
    public String getTitle()
    {
        return "Fermenting";
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
    public void setRecipe(IRecipeLayout iRecipeLayout, JEIRecipeWrapperFermenting jeiRecipeWrapperFermenting, IIngredients iIngredients)
    {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = iRecipeLayout.getFluidStacks();

        List<ItemStack> inputItems = iIngredients.getInputs(VanillaTypes.ITEM).get(0);
        List<FluidStack> inputFluids = iIngredients.getInputs(VanillaTypes.FLUID).get(0);
        List<FluidStack> outputFluids = iIngredients.getOutputs(VanillaTypes.FLUID).get(0);

        guiItemStacks.init(0, true, 0, 19); // input item
        guiFluidStacks.init(1, true, 28, 1, 16, 55, 1000, true, tankOverlay); // input fluid
        guiFluidStacks.init(2, true, 82, 1, 16, 55, 1000, true, tankOverlay); // output fluid

        guiItemStacks.set(0, inputItems);
        guiFluidStacks.set(1, inputFluids);
        guiFluidStacks.set(2, outputFluids);
    }
}
