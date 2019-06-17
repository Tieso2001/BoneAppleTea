package com.tieso2001.boneappletea.proxy;

import com.tieso2001.boneappletea.network.ModPacketHandler;
import com.tieso2001.boneappletea.network.PacketFluidTankUpdate;
import com.tieso2001.boneappletea.util.IProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy implements IProxy
{
    public void preInit(FMLPreInitializationEvent preEvent)
    {

    }

    public void init(FMLInitializationEvent event)
    {
        ModPacketHandler.INSTANCE.registerMessage(PacketFluidTankUpdate.PacketHandler.class, PacketFluidTankUpdate.class, 0, Side.SERVER);
    }

    public void postInit(FMLPostInitializationEvent postEvent)
    {

    }
}
