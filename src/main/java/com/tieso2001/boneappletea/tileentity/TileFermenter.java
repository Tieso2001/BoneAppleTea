package com.tieso2001.boneappletea.tileentity;

import com.tieso2001.boneappletea.state.FermenterState;
import com.tieso2001.boneappletea.gui.handler.FermenterBottleSlotHandler;
import com.tieso2001.boneappletea.gui.handler.FermenterInputSlotHandler;
import com.tieso2001.boneappletea.gui.handler.FermenterYeastSlotHandler;
import com.tieso2001.boneappletea.init.ModItems;
import com.tieso2001.boneappletea.recipe.FermenterRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;

public class TileFermenter extends TileEntity implements ITickable, IInventory {

    public static final int SLOTS = 5;
    private int fermentTime;
    private int defaultTime = 400;
    private int timeMultiplier = 2;
    public int fermentingTime = defaultTime * timeMultiplier;
    private FermenterState state = FermenterState.INACTIVE;
    private String fermenterCustomName;

    @Override
    public String getName() {
        return this.hasCustomName() ? this.fermenterCustomName : "container.fermenter";
    }

    @Override
    public boolean hasCustomName() {
        return this.fermenterCustomName != null && !this.fermenterCustomName.isEmpty();
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger("state", state.ordinal());
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        int stateIndex = packet.getNbtCompound().getInteger("state");

        if (world.isRemote && stateIndex != state.ordinal()) {
            state = FermenterState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    public void setState(FermenterState state) {
        if (this.state != state) {
            this.state = state;
            markDirty();
            IBlockState blockState = world.getBlockState(pos);
            getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
        }
    }

    public FermenterState getState() {
        return state;
    }

    private ItemStackHandler inputSlotHandler = new FermenterInputSlotHandler(1) {
        @Override
        protected void onContentsChanged(int slot) { TileFermenter.this.markDirty(); }
    };

    private ItemStackHandler yeastSlotHandler = new FermenterYeastSlotHandler(1) {
        @Override
        protected void onContentsChanged(int slot) { TileFermenter.this.markDirty(); }
    };

    private ItemStackHandler bottleSlotHandler = new FermenterBottleSlotHandler(3){
        @Override
        protected void onContentsChanged(int slot) { TileFermenter.this.markDirty(); }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputSlotHandler, yeastSlotHandler, bottleSlotHandler);

    @Override
    public int getSizeInventory() {
        return SLOTS;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return combinedHandler.getStackInSlot(index);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        combinedHandler.extractItem(index, 1, false);
        markDirty();
        return combinedHandler.extractItem(index, 1, true);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack itemstack = combinedHandler.getStackInSlot(index);
        combinedHandler.setStackInSlot(index, ItemStack.EMPTY);
        markDirty();
        return itemstack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        combinedHandler.setStackInSlot(index, stack);
        markDirty();
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        }
        else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 0) {
            return FermenterRecipes.getInstance().isItemInputValid(stack);
        }
        else if (index == 1) {
            return FermenterRecipes.getInstance().isItemYeastValid(stack);
        }
        else if (index == 2 || index == 3 || index == 4) {
            return FermenterRecipes.getInstance().isItemBottleValid(stack);
        }
        else return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < SLOTS; i++) {
            combinedHandler.setStackInSlot(i, ItemStack.EMPTY);
            markDirty();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            }
            else if (facing == EnumFacing.DOWN) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(bottleSlotHandler);
            }
            else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsInput")) {
            inputSlotHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsInput"));
        }
        if (compound.hasKey("itemsYeast")) {
            yeastSlotHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsYeast"));
        }
        if (compound.hasKey("itemsBottle")) {
            bottleSlotHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsBottle"));
        }
        this.fermentTime = compound.getInteger("fermentTime");
        this.state = FermenterState.VALUES[compound.getInteger("state")];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsInput", inputSlotHandler.serializeNBT());
        compound.setTag("itemsYeast", yeastSlotHandler.serializeNBT());
        compound.setTag("itemsBottle", bottleSlotHandler.serializeNBT());
        compound.setInteger("fermentTime", (short)this.fermentTime);
        compound.setInteger("state", state.ordinal());
        return compound;
    }

    @Override
    public void update() {


            if (!world.isRemote) {

                boolean flag = this.canFerment();
                boolean flag1 = this.fermentTime > 0;

                if (flag1) {
                    --this.fermentTime;
                    boolean flag2 = this.fermentTime == 0;

                    if (flag2 && flag) {
                        this.fermentItem();
                        this.markDirty();
                    } else if (!flag) {
                        this.fermentTime = 0;
                        this.markDirty();
                        setState(FermenterState.INACTIVE);
                    }
                } else if (flag) {
                    this.fermentTime = this.fermentingTime;
                    this.markDirty();
                    setState(FermenterState.ACTIVE);
                }

                if (fermentTime > 0) {
                    setState(FermenterState.ACTIVE);
                }
                else {
                    setState(FermenterState.INACTIVE);
                }

            }

    }

    public static boolean isItemYeast(ItemStack item) {
        return item.getItem() == ModItems.YEAST;
    }

    private ItemStack getBottleIngredient() {
        ItemStack inputBottle1 = this.bottleSlotHandler.getStackInSlot(0);
        ItemStack inputBottle2 = this.bottleSlotHandler.getStackInSlot(1);
        ItemStack inputBottle3 = this.bottleSlotHandler.getStackInSlot(2);

        // bottle1 = item, bottle2 = empty, bottle3 = empty
        if (!inputBottle1.isEmpty() && inputBottle2.isEmpty() && inputBottle3.isEmpty()) return inputBottle1;

        // bottle1 = empty, bottle2 = item, bottle3 = empty
        if (inputBottle1.isEmpty() && !inputBottle2.isEmpty() && inputBottle3.isEmpty()) return inputBottle2;

        // bottle1 = empty, bottle2 = empty, bottle3 = item
        if (inputBottle1.isEmpty() && inputBottle2.isEmpty() && !inputBottle3.isEmpty()) return inputBottle3;

        // bottle1 = bottle2, bottle3 = empty
        if (FermenterRecipes.getInstance().compareItemStacks(inputBottle1, inputBottle2) && inputBottle3.isEmpty()) return inputBottle1;

        // bottle1 = empty, bottle2 = bottle3
        if (inputBottle1.isEmpty() && FermenterRecipes.getInstance().compareItemStacks(inputBottle2, inputBottle3)) return inputBottle2;

        // bottle1 = bottle3, bottle2 = empty
        if (FermenterRecipes.getInstance().compareItemStacks(inputBottle1, inputBottle3) && inputBottle2.isEmpty()) return inputBottle1;

        // bottle1 = bottle2 = bottle3
        if (FermenterRecipes.getInstance().compareItemStacks(inputBottle1, inputBottle2) && FermenterRecipes.getInstance().compareItemStacks(inputBottle2, inputBottle3) && FermenterRecipes.getInstance().compareItemStacks(inputBottle1, inputBottle3)) return inputBottle1;

        return ItemStack.EMPTY;
    }

    private boolean canFerment() {
        ItemStack inputItem = this.inputSlotHandler.getStackInSlot(0);
        ItemStack inputYeast = this.yeastSlotHandler.getStackInSlot(0);
        ItemStack inputBottle = getBottleIngredient();
        ItemStack result = FermenterRecipes.getInstance().getFermentingResult(inputItem, inputBottle).copy();

        if(inputItem.isEmpty() || inputYeast.isEmpty() || (inputBottle.isEmpty())) return false;
        if (result.isEmpty()) return false;
        if (!isItemYeast(inputYeast)) return false;

        return true;
    }

    public void fermentItem() {
        if (this.canFerment()) {
            ItemStack inputItem = this.inputSlotHandler.getStackInSlot(0);
            ItemStack inputBottle1 = this.bottleSlotHandler.getStackInSlot(0);
            ItemStack inputBottle2 = this.bottleSlotHandler.getStackInSlot(1);
            ItemStack inputBottle3 = this.bottleSlotHandler.getStackInSlot(2);
            ItemStack inputBottleIngredient = getBottleIngredient();

            Item resultItem = FermenterRecipes.getInstance().getFermentingResult(inputItem, inputBottleIngredient).copy().getItem();
            ItemStack resultStack = new ItemStack(resultItem, 1);

            this.inputSlotHandler.extractItem(0,1, false);
            this.yeastSlotHandler.extractItem(0,1, false);
            this.markDirty();

            if (FermenterRecipes.getInstance().compareItemStacks(inputBottleIngredient, inputBottle1)) {
                this.bottleSlotHandler.setStackInSlot(0, resultStack.copy());
                this.markDirty();
            }

            if (FermenterRecipes.getInstance().compareItemStacks(inputBottleIngredient, inputBottle2)) {
                this.bottleSlotHandler.setStackInSlot(1, resultStack.copy());
                this.markDirty();
            }

            if (FermenterRecipes.getInstance().compareItemStacks(inputBottleIngredient, inputBottle3)) {
                this.bottleSlotHandler.setStackInSlot(2, resultStack.copy());
                this.markDirty();
            }

            this.markDirty();
        }
    }

    public int getField(int id) {
        switch (id) {
            case 0:
                return this.fermentTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.fermentTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 1;
    }

}