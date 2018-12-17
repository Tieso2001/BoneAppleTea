package com.tieso2001.afm.proxy;

import com.tieso2001.afm.init.ModBlocks;
import com.tieso2001.afm.init.ModItems;
import com.tieso2001.afm.init.ModTileEntities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent preEvent) {
        ModBlocks.init();
        ModItems.init();

        ModBlocks.register();
        ModItems.register();
        ModTileEntities.register();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent postEvent) {

    }

}
