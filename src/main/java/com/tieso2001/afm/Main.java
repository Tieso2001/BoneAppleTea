package com.tieso2001.afm;

import com.tieso2001.afm.init.ItemInit;
import com.tieso2001.afm.proxy.CommonProxy;
import com.tieso2001.afm.tabs.AfmTab;
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
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    @Instance
    public static Main instance;

    public static final CreativeTabs afmtab = new AfmTab("afmtab");

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void PreInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        MinecraftForge.addGrassSeed(new ItemStack(ItemInit.CORN_KERNELS), 1);
        GameRegistry.addSmelting(new ItemStack(ItemInit.CORN_KERNELS), new ItemStack(ItemInit.POPCORN), 0.35F);
        GameRegistry.addSmelting(new ItemStack(ItemInit.CORN), new ItemStack(ItemInit.CORN_ON_THE_COB), 0.35F);
    }

    @EventHandler
    public static void PostInit(FMLPostInitializationEvent event) {

    }

}
