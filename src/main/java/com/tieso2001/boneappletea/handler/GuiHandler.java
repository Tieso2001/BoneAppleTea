package com.tieso2001.boneappletea.handler;

import com.tieso2001.boneappletea.container.ContainerStockPot;
import com.tieso2001.boneappletea.gui.GuiStockPot;
import com.tieso2001.boneappletea.tile.TileStockPot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileStockPot)
        {
            TileStockPot tileStockPot = (TileStockPot) tileEntity;
            return new ContainerStockPot(player.inventory, tileStockPot);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileStockPot)
        {
            TileStockPot tileStockPot = (TileStockPot) tileEntity;
            return new GuiStockPot(tileStockPot, new ContainerStockPot(player.inventory, tileStockPot));
        }
        return null;
    }
}
