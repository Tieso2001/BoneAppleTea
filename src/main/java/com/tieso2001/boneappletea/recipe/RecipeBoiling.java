package com.tieso2001.boneappletea.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class RecipeBoiling
{
    private final ItemStack inputItem;
    private final FluidStack inputFluid;
    private final ItemStack outputItem;
    private final FluidStack outputFluid;
    private final int boilTime;

    public RecipeBoiling(ItemStack inputItem, @Nonnull FluidStack inputFluid, ItemStack outputItem, FluidStack outputFluid, int boilTime)
    {
        this.inputItem = inputItem;
        this.inputFluid = inputFluid;
        this.outputItem = outputItem;
        this.outputFluid = outputFluid;
        this.boilTime = boilTime;
    }

    public ItemStack getInputItem()
    {
        return inputItem;
    }

    public FluidStack getInputFluid()
    {
        return inputFluid;
    }

    public ItemStack getOutputItem()
    {
        return outputItem;
    }

    public FluidStack getOutputFluid()
    {
        return outputFluid;
    }

    public int getBoilTime()
    {
        return boilTime;
    }
}
