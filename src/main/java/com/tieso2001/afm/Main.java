package com.tieso2001.afm;

import com.tieso2001.afm.init.ModItems;
import com.tieso2001.afm.proxy.CommonProxy;
import com.tieso2001.afm.proxy.GuiHandler;
import com.tieso2001.afm.tabs.afmTab;
import com.tieso2001.afm.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    @Instance
    public static Main instance;

    public static final CreativeTabs afmtab = new afmTab("afmtab");

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
        MinecraftForge.addGrassSeed(new ItemStack(ModItems.BARLEY_SEEDS), 1);
        MinecraftForge.addGrassSeed(new ItemStack(ModItems.CORN_KERNELS), 1);
        GameRegistry.addSmelting(new ItemStack(ModItems.CORN_KERNELS), new ItemStack(ModItems.POPCORN), 0.35F);
        GameRegistry.addSmelting(new ItemStack(ModItems.CORN), new ItemStack(ModItems.CORN_ON_THE_COB), 0.35F);
    }

    @EventHandler
    public static void PostInit(FMLPostInitializationEvent postEvent) {
        proxy.postInit(postEvent);
    }

}
