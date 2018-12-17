package com.tieso2001.afm.object.blocks.fermenter;

import com.tieso2001.afm.init.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileFermenter extends TileEntity implements ITickable {

    public static final int ITEM_SLOT = 1;
    public static final int FUEL_SLOT = 1;
    public static final int BUCKET_SLOT = 1;

    public static final int INPUT_SLOTS = ITEM_SLOT + FUEL_SLOT + BUCKET_SLOT;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public static final int MAX_CONTENTS = 5000;

    private FluidTank fermenterInput = new FluidTank(MAX_CONTENTS) {
        @Override
        protected void onContentsChanged() {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            markDirty();
        }
    };

    private FluidTank fermenterOutput = new FluidTank(MAX_CONTENTS) {
        @Override
        protected void onContentsChanged() {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            markDirty();
        }
    };

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        NBTTagCompound tankNBT = new NBTTagCompound();
        fermenterInput.writeToNBT(tankNBT);
        fermenterOutput.writeToNBT(tankNBT);
        nbtTag.setTag("fermenter", tankNBT);
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        fermenterInput.readFromNBT(packet.getNbtCompound().getCompoundTag("fermenter"));
        fermenterOutput.readFromNBT(packet.getNbtCompound().getCompoundTag("fermenter"));
    }

    // Fuel slot
    private ItemStackHandler fuelInputHandler = new ItemStackHandler(FUEL_SLOT) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    // Input slot
    private ItemStackHandler itemInputHandler = new ItemStackHandler(ITEM_SLOT) {
        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    // Bucket slot
    private ItemStackHandler bucketInputHandler = new ItemStackHandler(BUCKET_SLOT) {
        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    // Output slot
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    private CombinedInvWrapper inputHandler = new CombinedInvWrapper(fuelInputHandler, itemInputHandler, bucketInputHandler);
    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("fuelIn")) {
            fuelInputHandler.deserializeNBT((NBTTagCompound) compound.getTag("fuelIn"));
        }
        if (compound.hasKey("itemsIn")) {
            itemInputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if (compound.hasKey("bucketIn")) {
            bucketInputHandler.deserializeNBT((NBTTagCompound) compound.getTag("bucketIn"));
        }
        if (compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("fuelIn", fuelInputHandler.serializeNBT());
        compound.setTag("itemsIn", itemInputHandler.serializeNBT());
        compound.setTag("bucketIn", bucketInputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    public FluidTank getInputTank() {
        return fermenterInput;
    }

    public FluidTank getOutputTank() {
        return fermenterOutput;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            }
            else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            }
            else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
            }
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (fermenterOutput.getFluidAmount() > 0) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fermenterOutput);
            }
            else return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fermenterInput);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            ItemStack input = inputHandler.extractItem(1, 1, true);
            ItemStack output = outputHandler.insertItem(0, new ItemStack(ModItems.BARLEY), true);
            if (input.getItem() == ModItems.YEAST) {
                if (fermenterInput.getFluidAmount() >= 1000) {
                    if (output.isEmpty()) {
                        if (fermenterOutput.canFillFluidType(new FluidStack(FluidRegistry.LAVA, 1000))) {
                            inputHandler.extractItem(1, 1, false);
                            outputHandler.insertItem(0, new ItemStack(ModItems.BARLEY), false);
                            fermenterInput.drain(1000, true);
                            fermenterOutput.fill(new FluidStack(FluidRegistry.LAVA, 1000), true);
                            markDirty();
                        }
                    }
                }
            }
        }
    }
}
