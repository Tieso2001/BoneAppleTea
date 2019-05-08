package com.tieso2001.boneappletea.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeStockPot
{
    private final FluidStack inputFluid;
    private final ItemStack inputItemFirst;
    private final ItemStack inputItemSecond;
    private final FluidStack outputFluid;
    private final int boilTime;

    public RecipeStockPot(FluidStack inputFluid, ItemStack inputItemFirst, ItemStack inputItemSecond, FluidStack outputFluid, int boilTime)
    {
        this.inputFluid = inputFluid;
        this.inputItemFirst = inputItemFirst;
        this.inputItemSecond = inputItemSecond;
        this.outputFluid = outputFluid;
        this.boilTime = boilTime;
    }

    public FluidStack getInputFluid()
    {
        return inputFluid;
    }

    public ItemStack getInputItemFirst()
    {
        return inputItemFirst;
    }

    public ItemStack getInputItemSecond()
    {
        return inputItemSecond;
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
