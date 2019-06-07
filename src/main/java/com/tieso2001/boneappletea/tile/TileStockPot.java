package com.tieso2001.boneappletea.tile;

import com.tieso2001.boneappletea.recipe.RecipeBoiling;
import com.tieso2001.boneappletea.recipe.RecipeBoilingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileStockPot extends TileEntity implements ITickable
{
    public int inputSlots = 1;
    public int outputSlots = 1;
    public int slots = inputSlots + outputSlots;
    public int tankCapacity = 1000;
    public int boilTime = 0;
    public int maxBoilTime = 1;
    public boolean hasFire = false;

    private ItemStackHandler inputItemStackHandler = new ItemStackHandler(inputSlots)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            markDirty();
        }
    };

    private ItemStackHandler outputItemStackHandler = new ItemStackHandler(outputSlots)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return false;
        }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputItemStackHandler, outputItemStackHandler);

    private FluidTank inputFluidTank = new FluidTank(tankCapacity)
    {
        @Override
        protected void onContentsChanged()
        {
            markDirty();
        }
    };

    private FluidTank outputFluidTank = new FluidTank(tankCapacity)
    {
        @Override
        protected void onContentsChanged()
        {
            markDirty();
        }

        @Override
        public boolean canFill()
        {
            return false;
        }
    };

    public FluidTank getFluidTank(int tankID)
    {
        if (tankID == 0) return inputFluidTank;
        if (tankID == 1) return outputFluidTank;
        return inputFluidTank;
    }

    @Override
    public void update()
    {
        ItemStack inputItem = inputItemStackHandler.getStackInSlot(0);
        FluidStack inputFluid = inputFluidTank.getFluid();
        RecipeBoiling recipe = RecipeBoilingRegistry.getRecipe(inputItem, inputFluid);

        if (recipe == null)
        {
            resetRecipe();
            return;
        }

        if (!inputFluid.containsFluid(recipe.getInputFluid().copy()))
        {
            resetRecipe();
            return;
        }

        if (recipe.getInputItem().getCount() > inputItem.getCount())
        {
            resetRecipe();
            return;
        }

        if (outputItemStackHandler.insertItem(0, recipe.getOutputItem().copy(), true).isItemEqual(recipe.getOutputItem().copy()))
        {
            resetRecipe();
            return;
        }

        if (recipe.getOutputFluid() != null)
        {
            if (outputFluidTank.fillInternal(recipe.getOutputFluid().copy(), false) != recipe.getOutputFluid().amount)
            {
                resetRecipe();
                return;
            }
        }

        if (boilTime <= 0)
        {
            boilTime = recipe.getBoilTime();
            maxBoilTime = recipe.getBoilTime();
        }

        if (boilTime == 1)
        {
            if (!recipe.getInputItem().isEmpty()) inputItemStackHandler.getStackInSlot(0).shrink(recipe.getInputItem().getCount());
            inputFluidTank.drain(recipe.getInputFluid().amount, true);
            if (!recipe.getOutputItem().isEmpty()) outputItemStackHandler.insertItem(0, recipe.getOutputItem().copy(), false);
            if (recipe.getOutputFluid() != null) outputFluidTank.fillInternal(recipe.getOutputFluid().copy(), true);
            resetRecipe();
        }

        if (boilTime >= 2)
        {
            boilTime--;
        }
    }

    private void resetRecipe()
    {
        boilTime = 0;
        maxBoilTime = 1;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsInput")) inputItemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsInput"));
        if (compound.hasKey("itemsOutput")) outputItemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOutput"));
        if (compound.hasKey("fluidsInput")) inputFluidTank.readFromNBT(compound.getCompoundTag("fluidsInput"));
        if (compound.hasKey("fluidsOutput")) outputFluidTank.readFromNBT(compound.getCompoundTag("fluidsOutput"));
        boilTime = compound.getInteger("boilTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setTag("itemsInput", inputItemStackHandler.serializeNBT());
        compound.setTag("itemsOutput", outputItemStackHandler.serializeNBT());

        NBTTagCompound inputFluidTankNBT = new NBTTagCompound();
        inputFluidTank.writeToNBT(inputFluidTankNBT);
        compound.setTag("fluidsInput", inputFluidTankNBT);

        NBTTagCompound outputFluidTankNBT = new NBTTagCompound();
        outputFluidTank.writeToNBT(outputFluidTankNBT);
        compound.setTag("fluidsOutput", outputFluidTankNBT);

        compound.setInteger("boilTime", boilTime);

        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing == EnumFacing.DOWN) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(outputFluidTank);
        else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputFluidTank);
        return super.getCapability(capability, facing);
    }
}
