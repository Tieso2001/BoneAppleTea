package net.tieso2001.boneappletea.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;

import java.util.function.Supplier;

public class setStackInSlotPacket {

    private final ItemStack stack;
    private final BlockPos pos;

    public setStackInSlotPacket(PacketBuffer buf) {
        stack = buf.readItemStack();
        pos = buf.readBlockPos();
    }

    public setStackInSlotPacket(ItemStack stack, BlockPos pos) {
        this.stack = stack;
        this.pos = pos;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeItemStack(stack);
        buf.writeBlockPos(pos);
    }

    @OnlyIn(Dist.CLIENT)
    public void handle(Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            World world = Minecraft.getInstance().world;
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity == null) return;

            if (tileEntity instanceof FruitPressTileEntity) {
                FruitPressTileEntity fruitPressTileEntity = (FruitPressTileEntity) tileEntity;
                fruitPressTileEntity.slot.setStackInSlot(0, stack.copy());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
