package com.tieso2001.afm.proxy;

import com.tieso2001.afm.objects.blocks.fermenter.ContainerFermenter;
import com.tieso2001.afm.objects.blocks.fermenter.GuiFermenter;
import com.tieso2001.afm.objects.blocks.fermenter.TileFermenter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileFermenter) {
            return new ContainerFermenter(player.inventory, (TileFermenter) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileFermenter) {
            TileFermenter containerTileEntity = (TileFermenter) te;
            return new GuiFermenter(containerTileEntity, new ContainerFermenter(player.inventory, containerTileEntity));
        }
        return null;
    }

}

