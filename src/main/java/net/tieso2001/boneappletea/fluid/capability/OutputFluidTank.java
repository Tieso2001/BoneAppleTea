package net.tieso2001.boneappletea.fluid.capability;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class OutputFluidTank extends FluidTank {

    public OutputFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return 0;
    }

    public int fillInternal(FluidStack resource, FluidAction action) {
        return super.fill(resource, action);
    }
}
