package com.tieso2001.boneappletea;

import com.tieso2001.boneappletea.recipe.ModRecipes;
import com.tieso2001.boneappletea.tab.TabCreative;
import com.tieso2001.boneappletea.util.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = BoneAppleTea.MODID, name = BoneAppleTea.NAME, version = BoneAppleTea.VERSION, acceptedMinecraftVersions = BoneAppleTea.MC_VERSION)
public class BoneAppleTea
{
    public static final String MODID = "boneappletea";
    public static final String NAME = "Bone Apple Tea";
    public static final String VERSION = "0.0.0";
    public static final String MC_VERSION = "[1.12.2]";

    public static final Logger LOGGER = LogManager.getLogger(BoneAppleTea.MODID);

    public static final String CLIENT = "com.tieso2001.boneappletea.proxy.ClientProxy";
    public static final String SERVER = "com.tieso2001.boneappletea.proxy.ServerProxy";
    @SidedProxy(clientSide = BoneAppleTea.CLIENT, serverSide = BoneAppleTea.SERVER)
    public static IProxy proxy;

    public static final CreativeTabs TAB_BONE_APPLE_TEA = new TabCreative(BoneAppleTea.MODID);

    @Mod.EventHandler
    public static void PreInit(FMLPreInitializationEvent preEvent)
    {
        proxy.preInit(preEvent);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        ModRecipes.initGrassSeeds();
        ModRecipes.initSmelting();
    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent postEvent)
    {
        proxy.postInit(postEvent);
    }
}
