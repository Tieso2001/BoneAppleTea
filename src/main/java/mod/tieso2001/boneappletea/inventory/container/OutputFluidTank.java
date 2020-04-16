package mod.tieso2001.boneappletea.inventory.container;

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
}
