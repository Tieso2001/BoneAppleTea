package com.tieso2001.boneappletea.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeFermenting
{
    private final ItemStack fermentItem;
    private final FluidStack inputFluid;
    private final FluidStack outputFluid;
    private final int fermentTime;

    public RecipeFermenting(ItemStack fermentItem, FluidStack inputFluid, FluidStack outputFluid, int fermentTime)
    {
        this.fermentItem = fermentItem;
        this.inputFluid = inputFluid;
        this.outputFluid = outputFluid;
        this.fermentTime = fermentTime;
    }

    public ItemStack getFermentItem()
    {
        return fermentItem;
    }

    public FluidStack getInputFluid()
    {
        return inputFluid;
    }

    public FluidStack getOutputFluid()
    {
        return outputFluid;
    }

    public int getFermentTime()
    {
        return fermentTime;
    }
}
