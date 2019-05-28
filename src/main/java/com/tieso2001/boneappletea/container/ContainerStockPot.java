package com.tieso2001.boneappletea.container;

import com.tieso2001.boneappletea.network.ModPacketHandler;
import com.tieso2001.boneappletea.network.PacketFluidTankUpdate;
import com.tieso2001.boneappletea.tile.TileStockPot;
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

public class ContainerStockPot extends Container
{
    private TileStockPot tileEntity;

    public ContainerStockPot(IInventory playerInventory, TileStockPot tileEntity)
    {
        this.tileEntity = tileEntity;
        addTileSlots();
        addPlayerSlots(playerInventory);
    }

    private void addTileSlots()
    {
        IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x; int y;

        x = 53; y = 21;
        addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y));

        x = 53; y = 47;
        addSlotToContainer(new SlotItemHandler(itemHandler, 1, x, y));

        x = 122; y = 34;
        addSlotToContainer(new SlotItemHandler(itemHandler, 2, x, y));
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

            if (index < tileEntity.SLOTS)
            {
                if (!this.mergeItemStack(itemstack1, tileEntity.SLOTS, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, tileEntity.SLOTS, false))
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
                if (tileEntity.getFluidTank().getFluid() != null) ModPacketHandler.INSTANCE.sendToAll(new PacketFluidTankUpdate(tileEntity, tileEntity.getFluidTank().getFluid()));
                else ModPacketHandler.INSTANCE.sendToAll(new PacketFluidTankUpdate(tileEntity, new FluidStack(FluidRegistry.WATER, 0)));
            }
        }
    }
}
