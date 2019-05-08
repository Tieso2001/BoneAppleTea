package com.tieso2001.boneappletea;

import com.tieso2001.boneappletea.init.ModItems;
import com.tieso2001.boneappletea.init.ModRecipes;
import com.tieso2001.boneappletea.handler.GuiHandler;
import com.tieso2001.boneappletea.network.ModPacketHandler;
import com.tieso2001.boneappletea.network.PacketFluidTankUpdate;
import com.tieso2001.boneappletea.util.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = BoneAppleTea.MODID, name = BoneAppleTea.NAME, version = BoneAppleTea.VERSION, acceptedMinecraftVersions = BoneAppleTea.MC_VERSION)
public class BoneAppleTea
{
    public static final String MODID = "boneappletea";
    public static final String NAME = "Bone Apple Tea";
    public static final String VERSION = "0.0.0";
    public static final String MC_VERSION = "[1.12.2]";

    @Mod.Instance
    public static BoneAppleTea instance;

    public static final String CLIENT = "com.tieso2001.boneappletea.proxy.ClientProxy";
    public static final String SERVER = "com.tieso2001.boneappletea.proxy.ServerProxy";
    @SidedProxy(clientSide = BoneAppleTea.CLIENT, serverSide = BoneAppleTea.SERVER)
    public static IProxy proxy;

    public static CreativeTabs TAB_BONE_APPLE_TEA = new CreativeTabs(BoneAppleTea.MODID)
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.CORN);
        }
    };

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
        ModRecipes.initBoiling();
        NetworkRegistry.INSTANCE.registerGuiHandler(BoneAppleTea.instance, new GuiHandler());
        ModPacketHandler.INSTANCE.registerMessage(PacketFluidTankUpdate.PacketHandler.class, PacketFluidTankUpdate.class, 0, Side.CLIENT);
    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent postEvent)
    {
        proxy.postInit(postEvent);
    }
}
