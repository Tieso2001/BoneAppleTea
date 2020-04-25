package net.tieso2001.boneappletea.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.network.packet.setStackInSlotPacket;
import net.tieso2001.boneappletea.network.packet.setFluidInTankPacket;

public class ModPackets {

    private static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BoneAppleTea.MOD_ID, BoneAppleTea.MOD_ID), () ->
            PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, setStackInSlotPacket.class, setStackInSlotPacket::toBytes, setStackInSlotPacket::new, setStackInSlotPacket::handle);
        INSTANCE.registerMessage(id++, setFluidInTankPacket.class, setFluidInTankPacket::toBytes, setFluidInTankPacket::new, setFluidInTankPacket::handle);
    }
}
