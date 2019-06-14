package com.tieso2001.boneappletea.container;

import com.tieso2001.boneappletea.network.ModPacketHandler;
import com.tieso2001.boneappletea.network.PacketFluidTankUpdate;
import com.tieso2001.boneappletea.tile.TileWoodenBarrel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ContainerWoodenBarrel extends Container
{
    private TileWoodenBarrel tileEntity;

    public ContainerWoodenBarrel(IInventory playerInventory, TileWoodenBarrel tileEntity)
    {
        this.tileEntity = tileEntity;
        addPlayerSlots(playerInventory);
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
            }
        }
    }
}
