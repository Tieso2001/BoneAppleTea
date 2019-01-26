package com.tieso2001.boneappletea.proxy;

import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.init.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent preEvent) {
        super.preInit(preEvent);
        ModBlocks.registerRenders();
        ModItems.registerRenders();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent postEvent) {
        super.postInit(postEvent);
    }

}