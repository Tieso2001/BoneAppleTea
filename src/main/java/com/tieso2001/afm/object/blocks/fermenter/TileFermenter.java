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
import net.minecraftforge.fluids.Fluid;
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

    public static final int FUEL_SLOT = 1;
    public static final int ITEM_SLOT_ONE = 1;
    public static final int ITEM_SLOT_TWO = 1;
    public static final int BUCKET_SLOT = 1;
    public static final int OUTPUT_SLOT = 1;

    public static final int INPUT_SLOTS = FUEL_SLOT + ITEM_SLOT_ONE + ITEM_SLOT_TWO + BUCKET_SLOT;
    public static final int OUTPUT_SLOTS = OUTPUT_SLOT;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public static final int MAX_CONTENTS = 5000;

    private int INPUT_TANK_AMOUNT = 0;
    private int OUTPUT_TANK_AMOUNT = 0;

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

    //Fuel Slot
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

    // Input Slot One
    private ItemStackHandler itemInputOneHandler = new ItemStackHandler(ITEM_SLOT_ONE) {
        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    // Input Slot Two
    private ItemStackHandler itemInputTwoHandler = new ItemStackHandler(ITEM_SLOT_TWO) {
        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    //Bucket Slot
    private ItemStackHandler bucketInputHandler = new ItemStackHandler(BUCKET_SLOT) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return SlotFurnaceFuel.isBucket(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    //Output Slot
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOT) {
        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    private CombinedInvWrapper inputHandler = new CombinedInvWrapper(fuelInputHandler, itemInputOneHandler, itemInputTwoHandler, bucketInputHandler);
    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("fuelIn")) {
            fuelInputHandler.deserializeNBT((NBTTagCompound) compound.getTag("fuelIn"));
        }
        if (compound.hasKey("itemsOneIn")) {
            itemInputOneHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOneIn"));
        }
        if (compound.hasKey("itemsTwoIn")) {
            itemInputTwoHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsTwoIn"));
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
        compound.setTag("itemsOneIn", itemInputOneHandler.serializeNBT());
        compound.setTag("itemsTwoIn", itemInputTwoHandler.serializeNBT());
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
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fermenterInput);
        }
        return super.getCapability(capability, facing);
    }

    public void setINPUT_TANK_AMOUNT(int INPUT_TANK_AMOUNT) {
        this.INPUT_TANK_AMOUNT = INPUT_TANK_AMOUNT;
    }

    public int getINPUT_TANK_AMOUNT() {
        return INPUT_TANK_AMOUNT;
    }

    public void setOUTPUT_TANK_AMOUNT(int OUTPUT_TANK_AMOUNT) {
        this.OUTPUT_TANK_AMOUNT = OUTPUT_TANK_AMOUNT;
    }

    public int getOUTPUT_TANK_AMOUNT() {
        return OUTPUT_TANK_AMOUNT;
    }

    public void canFerment() {
        ItemStack inputSlotOne = inputHandler.extractItem(1,1, true);
        ItemStack inputSlotTwo = inputHandler.extractItem(2,1,true);
    }

    @Override
    public void update() {

        if (!world.isRemote) {

            setINPUT_TANK_AMOUNT(getInputTank().getFluidAmount());
            setOUTPUT_TANK_AMOUNT(getOutputTank().getFluidAmount());
            getInputFluidPercentage();
            getOutputFluidPercentage();

            ItemStack input = inputHandler.extractItem(1, 1, true);
            ItemStack output = outputHandler.insertItem(0, new ItemStack(ModItems.BARLEY), true);
            if (input.getItem() == ModItems.YEAST) {
                if ((fermenterInput.getFluidAmount() >= 1000) && (fermenterOutput.getFluidAmount() <= 4000)) {
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

    //Fluid Tank GUI Updating
    public float getInputFluidPercentage()
    {
        return (float) getINPUT_TANK_AMOUNT() / (float) MAX_CONTENTS;
    }

    public int getInputFluidGuiHeight(int maxHeight)
    {
        return (int) Math.ceil(getInputFluidPercentage() * (float) maxHeight);
    }

    public float getOutputFluidPercentage()
    {
        return (float) getOUTPUT_TANK_AMOUNT() / (float) MAX_CONTENTS;
    }

    public int getOutputFluidGuiHeight(int maxHeight)
    {
        return (int) Math.ceil(getOutputFluidPercentage() * (float) maxHeight);
    }
}

