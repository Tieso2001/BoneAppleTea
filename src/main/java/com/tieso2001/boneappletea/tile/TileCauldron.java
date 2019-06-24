package com.tieso2001.boneappletea.tile;

import com.tieso2001.boneappletea.recipe.RecipeBoiling;
import com.tieso2001.boneappletea.recipe.RecipeBoilingRegistry;
import net.minecraft.init.Blocks;
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

public class TileCauldron extends TileEntity implements ITickable
{
    public int inputSlots = 1;
    public int outputSlots = 1;
    public int slots = inputSlots + outputSlots;
    public int tankCapacity = 1000;
    public int boilTime = 0;
    public int maxBoilTime = 1;
    public int heatAmount = 0;
    public int maxHeatAmount = 300;

    private ItemStackHandler inputItemStackHandler = new ItemStackHandler(inputSlots)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            for (RecipeBoiling recipe : RecipeBoilingRegistry.getRecipeMap().values())
            {
                if (recipe.getInputItem().isItemEqual(stack)) return true;
            }
            return false;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
        {
            if (!isItemValid(slot, stack)) return stack;
            return super.insertItem(slot, stack, simulate);
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

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
        {
            if (!isItemValid(slot, stack)) return stack;
            return super.insertItem(slot, stack, simulate);
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
        if (world.getBlockState(pos.down()).getBlock() == Blocks.FIRE)
        {
            if (heatAmount < maxHeatAmount) heatAmount++;
        }
        else if (heatAmount > 0) heatAmount--;

        if (heatAmount <= 0)
        {
            resetRecipe();
            return;
        }

        ItemStack inputItem = inputItemStackHandler.getStackInSlot(0);
        FluidStack inputFluid = inputFluidTank.getFluid();
        ItemStack outputItem = outputItemStackHandler.getStackInSlot(0);
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

        if (!outputItem.isEmpty() && !outputItem.isItemEqual(recipe.getOutputItem().copy()) || outputItem.getCount() + recipe.getOutputItem().getCount() > outputItemStackHandler.getSlotLimit(0))
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
            if (!recipe.getOutputItem().isEmpty()) outputItemStackHandler.setStackInSlot(0, new ItemStack(recipe.getOutputItem().getItem(), outputItem.getCount() + recipe.getOutputItem().getCount()));
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
        maxBoilTime = compound.getInteger("maxBoilTime");
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
        compound.setInteger("maxBoilTime", maxBoilTime);

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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (facing == EnumFacing.DOWN) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputItemStackHandler);
            else return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            if (facing == EnumFacing.UP) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputFluidTank);
            else return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(outputFluidTank);
        }
        return super.getCapability(capability, facing);
    }
}
