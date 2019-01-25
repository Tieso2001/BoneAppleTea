package com.tieso2001.boneappletea.tileentity;

import com.tieso2001.boneappletea.init.ModItems;
import com.tieso2001.boneappletea.recipe.FermenterRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;

public class TileFermenter extends TileEntity implements ITickable {

    public static final int SLOTS = 5;
    private int fermentTime;

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    private ItemStackHandler inputSlotHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) { TileFermenter.this.markDirty(); }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) { return FermenterRecipes.getInstance().isItemInputValid(stack); }
    };

    private ItemStackHandler yeastSlotHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) { TileFermenter.this.markDirty(); }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) { return FermenterRecipes.getInstance().isItemYeastValid(stack); }
    };

    private ItemStackHandler bottleSlotHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) { TileFermenter.this.markDirty(); }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) { return FermenterRecipes.getInstance().isItemBottleValid(stack); }

        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack) { return 1; }
    };

    public ItemStackHandler getBottleSlotHandler() { return bottleSlotHandler; }

    private CombinedInvWrapper inputHandler = new CombinedInvWrapper(inputSlotHandler, yeastSlotHandler);
    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputSlotHandler, yeastSlotHandler, bottleSlotHandler);

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
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
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
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsInput", inputSlotHandler.serializeNBT());
        compound.setTag("itemsYeast", yeastSlotHandler.serializeNBT());
        compound.setTag("itemsBottle", bottleSlotHandler.serializeNBT());
        return compound;
    }

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (this.canFerment()) {
                this.fermentItem();
                this.markDirty();
            }
        }
    }

    public static boolean isItemYeast(ItemStack item) {
        return item.getItem() == ModItems.YEAST;
    }

    private ItemStack getBottleIngredient() {
        ItemStack inputBottle1 = bottleSlotHandler.getStackInSlot(0);
        ItemStack inputBottle2 = bottleSlotHandler.getStackInSlot(1);
        ItemStack inputBottle3 = bottleSlotHandler.getStackInSlot(2);

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
        ItemStack inputItem = inputSlotHandler.getStackInSlot(0);
        ItemStack inputYeast = yeastSlotHandler.getStackInSlot(0);
        ItemStack inputBottle = getBottleIngredient();
        ItemStack result = FermenterRecipes.getInstance().getFermentingResult(inputItem, inputBottle).copy();

        if(inputItem.isEmpty() || inputYeast.isEmpty() || (inputBottle.isEmpty())) return false;
        if (result.isEmpty()) return false;
        if (!isItemYeast(inputYeast)) return false;

        return true;
    }

    public void fermentItem() {
        if (this.canFerment()) {
            ItemStack inputItem = inputSlotHandler.getStackInSlot(0);
            ItemStack inputBottle1 = bottleSlotHandler.getStackInSlot(0);
            ItemStack inputBottle2 = bottleSlotHandler.getStackInSlot(1);
            ItemStack inputBottle3 = bottleSlotHandler.getStackInSlot(2);
            ItemStack inputBottleIngredient = getBottleIngredient();
            ItemStack result = FermenterRecipes.getInstance().getFermentingResult(inputItem, inputBottleIngredient).copy();

            inputSlotHandler.extractItem(0,1, false);
            yeastSlotHandler.extractItem(0,1, false);

            if (FermenterRecipes.getInstance().compareItemStacks(inputBottleIngredient, inputBottle1)) {
                bottleSlotHandler.extractItem(0, 1,false);
                bottleSlotHandler.insertItem(0, result,false);
            }

            if (FermenterRecipes.getInstance().compareItemStacks(inputBottleIngredient, inputBottle2)) {
                bottleSlotHandler.extractItem(1, 1,false);
                bottleSlotHandler.insertItem(1, result,false);
            }

            if (FermenterRecipes.getInstance().compareItemStacks(inputBottleIngredient, inputBottle3)) {
                bottleSlotHandler.extractItem(2, 1,false);
                bottleSlotHandler.insertItem(2, result,false);
            }

            markDirty();
        }
    }

}
