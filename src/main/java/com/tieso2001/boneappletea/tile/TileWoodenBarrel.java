package com.tieso2001.boneappletea.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class TileWoodenBarrel extends TileEntity
{
    public int tankCapacity = 4000;

    private FluidTank fluidTank = new FluidTank(tankCapacity)
    {
        @Override
        protected void onContentsChanged()
        {
            markDirty();
        }
    };

    public FluidTank getFluidTank(int tankID)
    {
        if (tankID == 0) return fluidTank;
        return fluidTank;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("fluids")) fluidTank.readFromNBT(compound.getCompoundTag("fluids"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

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
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank);
        return super.getCapability(capability, facing);
    }
}
