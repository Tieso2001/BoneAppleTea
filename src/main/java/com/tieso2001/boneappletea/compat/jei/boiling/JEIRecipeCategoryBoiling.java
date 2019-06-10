package com.tieso2001.boneappletea.compat.jei.boiling;

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
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class JEIRecipeCategoryBoiling implements IRecipeCategory<JEIRecipeWrapperBoiling>
{
    private final IDrawable background;
    private final IDrawable tankOverlay;
    private final IDrawable fire;

    public JEIRecipeCategoryBoiling(IGuiHelper guiHelper)
    {
        ResourceLocation location = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/cauldron.png");
        ResourceLocation tankOverlay = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/elements/fluidtank_overlay.png");
        ResourceLocation fire = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/elements/fire.png");

        this.background = guiHelper.createDrawable(location, 25, 14, 126, 57);
        this.tankOverlay = guiHelper.createDrawable(tankOverlay, 0, 0, 16, 55);
        this.fire = guiHelper.createDrawable(fire, 0, 0, 14, 14);

    }

    @Override
    public String getUid()
    {
        return PluginJEI.BOILING_ID;
    }

    @Override
    public String getTitle()
    {
        return "Boiling";
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
    public void drawExtras(Minecraft minecraft)
    {
        fire.draw(minecraft, 56, 41);
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, JEIRecipeWrapperBoiling jeiRecipeWrapperBoiling, IIngredients iIngredients)
    {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = iRecipeLayout.getFluidStacks();

        List<ItemStack> inputItems = iIngredients.getInputs(VanillaTypes.ITEM).get(0);
        List<FluidStack> inputFluids = iIngredients.getInputs(VanillaTypes.FLUID).get(0);
        List<ItemStack> outputItems = iIngredients.getOutputs(VanillaTypes.ITEM).get(0);
        List<FluidStack> outputFluids = iIngredients.getOutputs(VanillaTypes.FLUID).get(0);

        guiItemStacks.init(0, true, 0, 19);
        guiFluidStacks.init(1, true, 28, 1, 16, 55, 1000, true, tankOverlay);
        guiItemStacks.init(2, true, 108, 19);
        guiFluidStacks.init(3, true, 82, 1, 16, 55, 1000, true, tankOverlay);

        guiItemStacks.set(0, inputItems);
        guiFluidStacks.set(1, inputFluids);
        guiItemStacks.set(2, outputItems);
        guiFluidStacks.set(3, outputFluids);
    }
}
