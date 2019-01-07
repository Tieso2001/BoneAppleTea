package com.tieso2001.boneappletea.object.blocks.fermenter;

import com.tieso2001.boneappletea.init.ModFluids;
import com.tieso2001.boneappletea.recipe.FermenterRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class TileFermenter extends TileEntity implements ITickable {

    public static final int FUEL_SLOTS = 1;
    public static final int ITEM_SLOTS = 1;
    public static final int BUCKET_INPUT_SLOTS = 2;
    public static final int BUCKET_OUTPUT_SLOTS = 2;
    public static final int SLOTS = FUEL_SLOTS + ITEM_SLOTS + BUCKET_INPUT_SLOTS + BUCKET_OUTPUT_SLOTS;

    public static final int MAX_TANK_CONTENTS = 5000;

    public int fermenterBurnTime = 0;
    public int fermentTime = 0;
    public int totalFermentTime = 200;

    // Create Input Tank
    private FluidTank inputTank = new FluidTank(MAX_TANK_CONTENTS) {
        @Override
        protected void onContentsChanged() {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);

            markDirty();
        }
    };

    public FluidTank getInputTank() {
        return inputTank;
    }

    // Create Output Tank
    private FluidTank outputTank = new FluidTank(MAX_TANK_CONTENTS) {
        @Override
        protected void onContentsChanged() {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);

            markDirty();
        }
    };

    public FluidTank getOutputTank() {
        return outputTank;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        NBTTagCompound tankNBT = new NBTTagCompound();
        inputTank.writeToNBT(tankNBT);
        outputTank.writeToNBT(tankNBT);
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
        inputTank.readFromNBT(packet.getNbtCompound().getCompoundTag("fermenter"));
        outputTank.readFromNBT(packet.getNbtCompound().getCompoundTag("fermenter"));
    }

    // Create Fuel Slot Handler
    private ItemStackHandler fuelSlotHandler = new ItemStackHandler(FUEL_SLOTS) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    // Create Item Slot Handler
    private ItemStackHandler itemSlotHandler = new ItemStackHandler(ITEM_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    // Create Bucket Input Slot Handler
    private ItemStackHandler bucketInputSlotHandler = new ItemStackHandler(BUCKET_INPUT_SLOTS) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return SlotFurnaceFuel.isBucket(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    // Create Bucket Output Slot Handler
    private ItemStackHandler bucketOutputSlotHandler = new ItemStackHandler(BUCKET_OUTPUT_SLOTS) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return SlotFurnaceFuel.isBucket(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileFermenter.this.markDirty();
        }
    };

    // Combine Input Slots into one handler
    private CombinedInvWrapper inputHandler = new CombinedInvWrapper(fuelSlotHandler, itemSlotHandler, bucketInputSlotHandler);

    // Combine Input Slots with Output Slots into one handler
    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, bucketOutputSlotHandler);

    // Read NBT Data
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("fuelIn")) {
            fuelSlotHandler.deserializeNBT((NBTTagCompound) compound.getTag("fuelIn"));
        }
        if (compound.hasKey("itemsIn")) {
            itemSlotHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if (compound.hasKey("bucketsIn")) {
            bucketInputSlotHandler.deserializeNBT((NBTTagCompound) compound.getTag("bucketsIn"));
        }
        if (compound.hasKey("bucketsOut")) {
            bucketOutputSlotHandler.deserializeNBT((NBTTagCompound) compound.getTag("bucketsOut"));
        }
    }

    // Write NBT Data
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("fuelIn", fuelSlotHandler.serializeNBT());
        compound.setTag("itemsIn", itemSlotHandler.serializeNBT());
        compound.setTag("bucketsIn", bucketInputSlotHandler.serializeNBT());
        compound.setTag("bucketsOut", bucketOutputSlotHandler.serializeNBT());
        return compound;
    }

    // Check if player is close enough to interact with TileEntity
    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    // Check for capabilities
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

    // Get capabilities
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            } else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemSlotHandler);
            } else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(bucketOutputSlotHandler);
            }
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(inputTank);
        }
        return super.getCapability(capability, facing);
    }

    public boolean isBurning()
    {
        return fermenterBurnTime > 0;
    }

    public boolean canFerment(boolean doFerment) {
        // Item Slot
        ItemStack input = itemSlotHandler.getStackInSlot(0);

        // Input Tank
        FluidStack inputTankStack = inputTank.getFluid();
        int inputTankLevel = ModFluids.getAmount(inputTankStack);
        setInputTankAmount(inputTankLevel);

        // Output Tank
        FluidStack outputTankStack = outputTank.getFluid();
        int outputTankLevel = ModFluids.getAmount(outputTankStack);
        setOutputTankAmount(outputTankLevel);

        if (inputTankStack == null || inputTankStack.getFluid() == null) return false;

        for (Object object : FermenterRecipes.RECIPES.entrySet()) {
            Map.Entry recipe = (Map.Entry) object;
            ItemStack recipeInputItem = (ItemStack) recipe.getKey();
            FluidStack[] recipeFluidStacks = (FluidStack[]) recipe.getValue();
            FluidStack recipeInputFluid = recipeFluidStacks[0];
            FluidStack recipeOutputFluid = recipeFluidStacks[1];

            if (input.getItem() == recipeInputItem.getItem()) {
                if (input.getCount() >= recipeInputItem.getCount()) {
                    if (ModFluids.compareFluid(inputTankStack.getFluid(), recipeInputFluid.getFluid())) {
                        if (outputTankLevel == 0 || outputTankStack == null || ModFluids.compareFluid(outputTankStack.getFluid(), recipeOutputFluid.getFluid())) {
                            if (inputTankLevel >= recipeInputFluid.amount && (outputTankLevel + recipeOutputFluid.amount) <= MAX_TANK_CONTENTS) {
                                if (doFerment) {
                                    ferment(recipeInputItem, recipeInputFluid, recipeOutputFluid);
                                    return true;
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void ferment(ItemStack recipeInputItem, FluidStack recipeInputFluid, FluidStack recipeOutputFluid) {
        itemSlotHandler.extractItem(0, recipeInputItem.getCount(), false);
        inputTank.drain(recipeInputFluid.amount, true);
        outputTank.fill(recipeOutputFluid, true);
        markDirty();
    }

    public boolean canFuelFermenter() {
        ItemStack fuel = fuelSlotHandler.getStackInSlot(0);
        return TileEntityFurnace.isItemFuel(fuel);
    }

    public void fuelFermenter() {
        ItemStack fuel = fuelSlotHandler.getStackInSlot(0);
        fuelSlotHandler.extractItem(0, 1, false);
        fermenterBurnTime = TileEntityFurnace.getItemBurnTime(fuel) + 1;
        markDirty();
        if (fuel.getItem() == Items.LAVA_BUCKET) {
            fuelSlotHandler.insertItem(0, new ItemStack(Items.BUCKET, 1), false);
            markDirty();
        }
    }

    @Override
    public void update() {
        if (isBurning()) --fermenterBurnTime;
        if (!world.isRemote) {
            if (!isBurning() && canFerment(false)) {
                if (canFuelFermenter()) { fuelFermenter(); }
                else {
                    fermentTime = 0;
                    fermenterBurnTime = 0;
                }
            }
            else if (isBurning() && !canFerment(false)) {
                fermentTime = 0;
            }
            else if (isBurning() && canFerment(false)) {
                ++fermentTime;

                if (fermentTime == totalFermentTime) {
                    fermentTime = 0;
                    canFerment(true);
                }
            }
            else if (!isBurning() && !canFerment(false)) {
                fermentTime = 0;
                fermenterBurnTime = 0;
            }
            else fermentTime = 0;
        }
    }

    private int inputTankAmount = 0;
    public int getInputTankAmount() { return inputTankAmount; }
    public void setInputTankAmount(int inputTankAmount) { this.inputTankAmount = inputTankAmount; }

    private int outputTankAmount = 0;
    public int getOutputTankAmount() { return outputTankAmount; }
    public void setOutputTankAmount(int outputTankAmount) { this.outputTankAmount = outputTankAmount; }

}

