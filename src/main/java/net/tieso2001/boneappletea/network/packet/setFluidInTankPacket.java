package net.tieso2001.boneappletea.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;

import java.util.function.Supplier;

public class setFluidInTankPacket {

    private final FluidStack fluid;
    private final BlockPos pos;

    public setFluidInTankPacket(PacketBuffer buf) {
        fluid = buf.readFluidStack();
        pos = buf.readBlockPos();
    }

    public setFluidInTankPacket(FluidStack fluid, BlockPos pos) {
        this.fluid = fluid;
        this.pos = pos;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeFluidStack(fluid);
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
                fruitPressTileEntity.tank.setFluid(fluid.copy());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
