package net.tieso2001.boneappletea.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.IntStream;

public abstract class AbstractFluidTileEntity extends TileEntity implements IFluidHandler {

    public final FluidTank[] tanks;
    protected final LazyOptional<IFluidHandler> tankCapability;

    public AbstractFluidTileEntity(TileEntityType<?> tileEntityType, int tankCount, int tankCapacity) {
        super(tileEntityType);
        this.tanks = IntStream.range(0, tankCount).mapToObj(k -> new FluidTank(tankCapacity)).toArray(FluidTank[]::new);
        this.tankCapability = LazyOptional.of(() -> this);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        ListNBT list = compound.getList("tanks", 10);
        for (int i = 0; i < tanks.length && i < list.size(); ++i) {
            INBT nbt = list.get(i);
            tanks[i].setFluid(FluidStack.loadFluidStackFromNBT((CompoundNBT) nbt));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT list = new ListNBT();
        for (FluidTank tank : tanks) {
            list.add(tank.writeToNBT(new CompoundNBT()));
        }
        compound.put("tanks", list);
        return super.write(compound);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    public FluidTank getTank(int tank) {
        if (tank < 0 || tank >= tanks.length) {
            return null;
        }
        return tanks[tank];
    }

    @Override
    public int getTanks() {
        return tanks.length;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        FluidTank fluidTank = getTank(tank);
        return fluidTank == null ? FluidStack.EMPTY : fluidTank.getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        FluidTank fluidTank = getTank(tank);
        return fluidTank == null ? 0 : fluidTank.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        FluidTank fluidTank = getTank(tank);
        return fluidTank != null && fluidTank.isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        for (int i = 0; i < getTanks(); ++i) {
            FluidStack fluidInTank = getFluidInTank(i);
            if (isFluidValid(i, resource) && (fluidInTank.isEmpty() || resource.isFluidEqual(fluidInTank))) {
                return getTank(i).fill(resource, action);
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }
        for (int i = 0; i < getTanks(); ++i) {
            if (resource.isFluidEqual(getFluidInTank(i))) {
                return getTank(i).drain(resource, action);
            }
        }
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        for (int i = 0; i < getTanks(); ++i) {
            if (getTank(i).getFluidAmount() > 0) {
                return getTank(i).drain(maxDrain, action);
            }
        }
        return FluidStack.EMPTY;
    }
}
