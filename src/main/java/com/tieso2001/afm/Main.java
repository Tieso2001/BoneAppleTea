package com.tieso2001.afm;

import com.tieso2001.afm.proxy.CommonProxy;
import com.tieso2001.afm.handler.GuiHandler;
import com.tieso2001.afm.tab.TabAFM;
import com.tieso2001.afm.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    @Instance
    public static Main instance;

    public static final CreativeTabs AFM = new TabAFM();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void PreInit(FMLPreInitializationEvent preEvent) {
        proxy.preInit(preEvent);
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @EventHandler
    public static void PostInit(FMLPostInitializationEvent postEvent) {
        proxy.postInit(postEvent);
    }

}
