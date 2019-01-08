package com.tieso2001.boneappletea.object.blocks.fermenter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFermenter extends Container {

    private final TileFermenter tileEntity;
    private int fermenterBurnTime, fermentTime, totalFermentTime, inputTankAmount, outputTankAmount;

    public ContainerFermenter(IInventory playerInventory, TileFermenter tileEntity) {
        this.tileEntity = tileEntity;

        // This container references items out of our own inventory (the 9 slots we hold ourselves)
        // as well as the slots from the player inventory so that the user can transfer items between
        // both inventories. The two calls below make sure that slots are defined for both inventories.
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 8 + col * 18;
                int y = 84 + row * 18;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 142;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int slotIndex = 0;
        int x; int y;

        //Fuel Slot
        x = 80; y = 54;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        //Input Slot
        x = 80; y = 15;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        //Bucket Input Slot 1
        x = 12; y = 12;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        //Bucket Output Slot 1
        x = 12; y = 54;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        //Bucket Input Slot 2
        x = 148; y = 12;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        //Bucket Output Slot 2
        x = 148; y = 54;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileFermenter.SLOTS) {
                if (!this.mergeItemStack(itemstack1, TileFermenter.SLOTS, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileFermenter.SLOTS, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.canInteractWith(playerIn);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for(int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener listener = (IContainerListener)this.listeners.get(i);

            if(this.fermenterBurnTime != this.tileEntity.getField(0)) listener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
            if(this.fermentTime != this.tileEntity.getField(1)) listener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
            if(this.totalFermentTime != this.tileEntity.getField(2)) listener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
            if(this.inputTankAmount != this.tileEntity.getField(3)) listener.sendWindowProperty(this, 3, this.tileEntity.getField(3));
            if(this.outputTankAmount != this.tileEntity.getField(4)) listener.sendWindowProperty(this, 4, this.tileEntity.getField(4));

        }
        this.fermenterBurnTime = this.tileEntity.getField(0);
        this.fermentTime = this.tileEntity.getField(1);
        this.totalFermentTime = this.tileEntity.getField(2);
        this.inputTankAmount = this.tileEntity.getField(3);
        this.outputTankAmount = this.tileEntity.getField(4);
    }

    @Override
    public void updateProgressBar(int id, int data) {
        this.tileEntity.setField(id, data);
    }

}

