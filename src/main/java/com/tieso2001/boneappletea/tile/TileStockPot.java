package com.tieso2001.boneappletea.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileStockPot extends TileEntity
{
    public int SLOTS = 2;

    private ItemStackHandler itemStackHandler = new ItemStackHandler(SLOTS)
    {
        @Override
        protected void onContentsChanged(int slot) { markDirty(); }
    };

    private FluidTank fluidTank = new FluidTank(1000)
    {
        @Override
        protected void onContentsChanged() { markDirty(); }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        if (compound.hasKey("fluids")) fluidTank.readFromNBT(compound.getCompoundTag("fluids"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        NBTTagCompound fluidTankNBT = new NBTTagCompound();
        fluidTank.writeToNBT(fluidTankNBT);
        compound.setTag("fluids", fluidTankNBT);
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
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank);
        return super.getCapability(capability, facing);
    }
}
