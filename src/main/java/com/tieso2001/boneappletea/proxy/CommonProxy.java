package com.tieso2001.boneappletea.proxy;

import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.init.ModItems;
import com.tieso2001.boneappletea.init.ModTileEntities;
import com.tieso2001.boneappletea.recipe.FurnaceRecipes;
import com.tieso2001.boneappletea.world.GrassSeeds;
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
        FurnaceRecipes.register();
        GrassSeeds.register();
    }

    public void postInit(FMLPostInitializationEvent postEvent) {
    }

}