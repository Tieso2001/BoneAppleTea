package com.tieso2001.boneappletea.handler;

import com.tieso2001.boneappletea.container.ContainerCauldron;
import com.tieso2001.boneappletea.container.ContainerWoodenBarrel;
import com.tieso2001.boneappletea.container.ContainerWoodenFermentingBarrel;
import com.tieso2001.boneappletea.gui.GuiCauldron;
import com.tieso2001.boneappletea.gui.GuiWoodenBarrel;
import com.tieso2001.boneappletea.gui.GuiWoodenFermentingBarrel;
import com.tieso2001.boneappletea.tile.TileCauldron;
import com.tieso2001.boneappletea.tile.TileWoodenBarrel;
import com.tieso2001.boneappletea.tile.TileWoodenFermentingBarrel;
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
        if (tileEntity instanceof TileCauldron)
        {
            TileCauldron tileCauldron = (TileCauldron) tileEntity;
            return new ContainerCauldron(player.inventory, tileCauldron);
        }
        if (tileEntity instanceof TileWoodenBarrel)
        {
            TileWoodenBarrel tileWoodenBarrel = (TileWoodenBarrel) tileEntity;
            return new ContainerWoodenBarrel(player.inventory, tileWoodenBarrel);
        }
        if (tileEntity instanceof TileWoodenFermentingBarrel)
        {
            TileWoodenFermentingBarrel tileWoodenFermentingBarrel = (TileWoodenFermentingBarrel) tileEntity;
            return new ContainerWoodenFermentingBarrel(player.inventory, tileWoodenFermentingBarrel);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileCauldron)
        {
            TileCauldron tileCauldron = (TileCauldron) tileEntity;
            return new GuiCauldron(tileCauldron, new ContainerCauldron(player.inventory, tileCauldron));
        }
        if (tileEntity instanceof TileWoodenBarrel)
        {
            TileWoodenBarrel tileWoodenBarrel = (TileWoodenBarrel) tileEntity;
            return new GuiWoodenBarrel(tileWoodenBarrel, new ContainerWoodenBarrel(player.inventory, tileWoodenBarrel));
        }
        if (tileEntity instanceof TileWoodenFermentingBarrel)
        {
            TileWoodenFermentingBarrel tileWoodenFermentingBarrel = (TileWoodenFermentingBarrel) tileEntity;
            return new GuiWoodenFermentingBarrel(tileWoodenFermentingBarrel, new ContainerWoodenFermentingBarrel(player.inventory, tileWoodenFermentingBarrel));
        }
        return null;
    }
}
