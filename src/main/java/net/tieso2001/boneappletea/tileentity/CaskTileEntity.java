package net.tieso2001.boneappletea.tileentity;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CaskTileEntity extends AbstractFluidTileEntity {

    public CaskTileEntity() {
        super(ModTileEntityTypes.CASK.get(), 1, 8000);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return tankCapability.cast();
        }
        return super.getCapability(cap, side);
    }
}
