package com.tieso2001.boneappletea.network;

import com.tieso2001.boneappletea.tile.TileStockPot;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketFluidTankUpdate implements IMessage
{
    public PacketFluidTankUpdate() {}

    private BlockPos pos;
    private FluidStack fluidStack;
    private int tankID;

    public PacketFluidTankUpdate(TileEntity tileEntity, FluidStack fluidStack, int tankID)
    {
        this.pos = tileEntity.getPos();
        this.fluidStack = fluidStack;
        this.tankID = tankID;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());

        NBTTagCompound compound = new NBTTagCompound();
        fluidStack.writeToNBT(compound);
        ByteBufUtils.writeTag(buf, compound);

        buf.writeInt(tankID);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        int x = buf.readInt();
        int y = buf.readInt();
        int z = buf.readInt();
        pos = new BlockPos(x, y , z);

        NBTTagCompound compound = ByteBufUtils.readTag(buf);
        fluidStack = FluidStack.loadFluidStackFromNBT(compound);

        tankID = buf.readInt();
    }

    @SideOnly(Side.CLIENT)
    public static class PacketHandler implements IMessageHandler<PacketFluidTankUpdate, IMessage>
    {
        @Override
        public IMessage onMessage(PacketFluidTankUpdate message, MessageContext ctx)
        {
            EntityPlayerSP player = Minecraft.getMinecraft().player;

            BlockPos pos = message.pos;
            FluidStack fluidStack = message.fluidStack;
            int tankID = message.tankID;

            TileEntity tileEntity = player.world.getTileEntity(pos);

            if (tileEntity != null)
            {
                if (tileEntity instanceof TileStockPot)
                {
                    if (player.world.isBlockLoaded(pos))
                    {
                        ((TileStockPot) tileEntity).getFluidTank(tankID).setFluid(fluidStack);
                    }
                }
            }
            return null;
        }
    }
}
