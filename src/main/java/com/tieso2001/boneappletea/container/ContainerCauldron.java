package com.tieso2001.boneappletea.container;

import com.tieso2001.boneappletea.network.ModPacketHandler;
import com.tieso2001.boneappletea.network.PacketFluidTankUpdate;
import com.tieso2001.boneappletea.tile.TileCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCauldron extends Container
{
    private TileCauldron tileEntity;
    private int boilTime;
    private int maxBoilTime;
    private int heatAmount;
    private int maxHeatAmount;

    public ContainerCauldron(IInventory playerInventory, TileCauldron tileEntity)
    {
        this.tileEntity = tileEntity;
        addTileSlots();
        addPlayerSlots(playerInventory);
    }

    private void addTileSlots()
    {
        IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x; int y;

        x = 26; y = 34;
        addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y));

        x = 134; y = 34;
        addSlotToContainer(new SlotItemHandler(itemHandler, 1, x, y));
    }

    private void addPlayerSlots(IInventory playerInventory)
    {
        // Main Inventory
        for (int row = 0; row < 3; ++row)
        {
            for (int col = 0; col < 9; ++col)
            {
                int x = 8 + col * 18;
                int y = 84 + row * 18;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Hotbar
        for (int row = 0; row < 9; ++row)
        {
            int x = 8 + row * 18;
            int y = 142;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < tileEntity.slots)
            {
                if (!this.mergeItemStack(itemstack1, tileEntity.slots, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, tileEntity.slots, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return playerIn.getDistanceSq(playerIn.getPosition().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (IContainerListener listener : listeners)
        {
            if (listener instanceof EntityPlayerMP)
            {
                ModPacketHandler.INSTANCE.sendToAll(new PacketFluidTankUpdate(tileEntity, tileEntity.getFluidTank(0).getFluid(), 0));
                ModPacketHandler.INSTANCE.sendToAll(new PacketFluidTankUpdate(tileEntity, tileEntity.getFluidTank(1).getFluid(), 1));
            }
        }

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener iContainerListener = this.listeners.get(i);
            if (this.boilTime != this.tileEntity.boilTime) iContainerListener.sendWindowProperty(this, 0, tileEntity.boilTime);
            if (this.maxBoilTime != this.tileEntity.maxBoilTime) iContainerListener.sendWindowProperty(this, 1, tileEntity.maxBoilTime);
            if (this.heatAmount != this.tileEntity.heatAmount) iContainerListener.sendWindowProperty(this, 2, tileEntity.heatAmount);
            if (this.maxHeatAmount != this.tileEntity.maxHeatAmount) iContainerListener.sendWindowProperty(this, 3, tileEntity.maxHeatAmount);
        }
    }

    @Override
    public void updateProgressBar(int id, int data)
    {
        if (id == 0) this.tileEntity.boilTime = data;
        if (id == 1) this.tileEntity.maxBoilTime = data;
        if (id == 2) this.tileEntity.heatAmount = data;
        if (id == 3) this.tileEntity.maxHeatAmount = data;
    }
}
