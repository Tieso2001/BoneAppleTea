package net.tieso2001.boneappletea.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.stream.IntStream;
/*
public class CaskTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IFluidHandler {

    public static final int INPUT_1_SLOT = 0;
    public static final int INPUT_2_SLOT = 1;
    public static final int INPUT_TANK_INPUT_SLOT = 2;
    public static final int INPUT_TANK_OUTPUT_SLOT = 3;
    public static final int OUTPUT_TANK_INPUT_SLOT = 4;

    public final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot) {
                case INPUT_1_SLOT:
                case INPUT_2_SLOT:
                    return isInputSlotItem(stack);
                case INPUT_TANK_INPUT_SLOT:
                    return isFluidInputSlotItem(stack);
                case INPUT_TANK_OUTPUT_SLOT:
                case OUTPUT_TANK_INPUT_SLOT:
                    return isFluidOutputSlotItem(stack);
                default:
                    return false;
            }
        }

        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            switch (slot) {
                case INPUT_1_SLOT:
                    return super.getStackLimit(slot, stack);;
                case INPUT_2_SLOT:
                    return super.getStackLimit(slot, stack);;
                case INPUT_TANK_INPUT_SLOT:
                    return 1;
                case INPUT_TANK_OUTPUT_SLOT:
                case OUTPUT_TANK_INPUT_SLOT:
                    return 1;
                default:
                    return super.getStackLimit(slot, stack);;
            };
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            CaskTileEntity.this.markDirty();
        }
    };

    protected final FluidTank[] tanks;

    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);
    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_TANK_INPUT_SLOT, OUTPUT_TANK_INPUT_SLOT + 1));
    private final LazyOptional<IFluidHandler> tankCapability;

    public short maxProcessTime = -1;
    public short processTimeLeft = -1;

    public CaskTileEntity() {
        super(ModTileEntityTypes.CASK.get());
        this.tanks = IntStream.range(0, 2).mapToObj(k -> new FluidTank(8000)).toArray(FluidTank[]::new);
        this.tankCapability = LazyOptional.of(() -> this);
    }

    private boolean isInputSlotItem(ItemStack stack) {

    }

    private boolean isFluidInputSlotItem(ItemStack stack) {

    }

    private boolean isFluidOutputSlotItem(ItemStack stack) {

    }

    private BrewingRecipe getRecipe(final ItemStack input) {
        return getRecipe(new Inventory(input));
    }

    private BrewingRecipe getRecipe(final IInventory inventory) {
        return world == null ? null : world.getRecipeManager().getRecipe(BrewingRecipe.recipeType, inventory, world).orElse(null);
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) {
            return;
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("inventory"));
        this.inputTank.readFromNBT(compound);
        this.outputTank.readFromNBT(compound);
        this.maxProcessTime = compound.getShort("max_process_time");
        this.processTimeLeft = compound.getShort("process_time_left");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("inventory", this.inventory.serializeNBT());
        inputTank.writeToNBT(compound);
        outputTank.writeToNBT(compound);
        compound.putShort("max_process_time", this.maxProcessTime);
        compound.putShort("process_time_left", this.processTimeLeft);
        return compound;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                return inventoryCapabilityDown.cast();
            }
            return inventoryCapability.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return inputTankCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public int getTanks() {
        return tanks.length;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank < 0 || tank >= this.getTanks()) {
            return FluidStack.EMPTY;
        }
        return this.tanks.;
    }

    public LazyOptional<IFluidHandler> getTankCapability() {
        return tankCapability;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Cask");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CaskContainer(id, world, pos, playerInventory, playerEntity);
    }
}
 */
