package com.tieso2001.boneappletea;

import com.tieso2001.boneappletea.handler.GuiHandler;
import com.tieso2001.boneappletea.proxy.CommonProxy;
import com.tieso2001.boneappletea.creativetab.TabBoneAppleTea;
import com.tieso2001.boneappletea.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class BoneAppleTea {

    @Instance
    public static BoneAppleTea instance;

    public static final CreativeTabs TAB_BONEAPPLETEA = new TabBoneAppleTea();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    static { FluidRegistry.enableUniversalBucket(); }

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
