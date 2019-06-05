package com.tieso2001.boneappletea.tile;

import com.tieso2001.boneappletea.recipe.RecipeFermenting;
import com.tieso2001.boneappletea.recipe.RecipeFermentingRegistry;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileWoodenBarrel extends TileEntity implements ITickable
{
    public int slots = 1;
    public int tankCapacity = 1000;
    public int fermentTime = 0;
    public int maxFermentTime = 1;

    private ItemStackHandler itemStackHandler = new ItemStackHandler(slots)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            for (RecipeFermenting recipe : RecipeFermentingRegistry.getRecipeMap().values())
            {
                if (recipe.getFermentItem().isItemEqual(stack)) return true;
            }
            return false;
        }
    };

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
        ItemStack fermentItem = itemStackHandler.getStackInSlot(0);
        FluidStack inputFluid = inputFluidTank.getFluid();
        if (inputFluid == null || fermentItem.isEmpty())
        {
            resetRecipe();
            return;
        }

        RecipeFermenting recipe = RecipeFermentingRegistry.getRecipe(fermentItem, inputFluid);
        if (recipe == null)
        {
            resetRecipe();
            return;
        }

        if (!inputFluid.containsFluid(recipe.getInputFluid()) || recipe.getFermentItem().getCount() > fermentItem.getCount() || outputFluidTank.fillInternal(recipe.getOutputFluid(), false) != recipe.getOutputFluid().amount)
        {
            resetRecipe();
            return;
        }

        if (fermentTime <= 0)
        {
            fermentTime = recipe.getFermentTime();
            maxFermentTime = recipe.getFermentTime();
        }

        if (fermentTime == 1)
        {
            itemStackHandler.getStackInSlot(0).shrink(recipe.getFermentItem().getCount());
            inputFluidTank.drain(recipe.getInputFluid().amount, true);
            outputFluidTank.fillInternal(recipe.getOutputFluid().copy(), true);
            resetRecipe();
        }

        if (fermentTime >= 2)
        {
            fermentTime--;
        }
    }

    private void resetRecipe()
    {
        fermentTime = 0;
        maxFermentTime = 1;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        if (compound.hasKey("fluidsInput")) inputFluidTank.readFromNBT(compound.getCompoundTag("fluidsInput"));
        if (compound.hasKey("fluidsOutput")) outputFluidTank.readFromNBT(compound.getCompoundTag("fluidsOutput"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setTag("items", itemStackHandler.serializeNBT());

        NBTTagCompound inputFluidTankNBT = new NBTTagCompound();
        inputFluidTank.writeToNBT(inputFluidTankNBT);
        compound.setTag("fluidsInput", inputFluidTankNBT);

        NBTTagCompound outputFluidTankNBT = new NBTTagCompound();
        outputFluidTank.writeToNBT(outputFluidTankNBT);
        compound.setTag("fluidsOutput", outputFluidTankNBT);

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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing == EnumFacing.DOWN) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(outputFluidTank);
        else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputFluidTank);
        return super.getCapability(capability, facing);
    }
}
