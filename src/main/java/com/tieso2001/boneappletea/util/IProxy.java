package com.tieso2001.boneappletea.util;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy
{
    public void preInit(FMLPreInitializationEvent preEvent);

    public void init(FMLInitializationEvent event);

    public void postInit(FMLPostInitializationEvent postEvent);
}
