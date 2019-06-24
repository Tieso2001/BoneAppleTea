package com.tieso2001.boneappletea;

import com.tieso2001.boneappletea.compat.oredict.ModOreDictionary;
import com.tieso2001.boneappletea.init.ModFluids;
import com.tieso2001.boneappletea.init.ModItems;
import com.tieso2001.boneappletea.init.ModRecipes;
import com.tieso2001.boneappletea.handler.GuiHandler;
import com.tieso2001.boneappletea.util.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = BoneAppleTea.MODID, name = BoneAppleTea.NAME, version = BoneAppleTea.VERSION, acceptedMinecraftVersions = BoneAppleTea.MC_VERSION)
public class BoneAppleTea
{
    public static final String MODID = "boneappletea";
    public static final String NAME = "Bone Apple Tea";
    public static final String VERSION = "b1.2.0";
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

        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_)
        {
            p_78018_1_.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.BEER, 1000)));
            p_78018_1_.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.UNCARBONATED_BEER, 1000)));
            p_78018_1_.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.HOPPED_WORT, 1000)));
            p_78018_1_.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.SWEET_WORT, 1000)));
            super.displayAllRelevantItems(p_78018_1_);
        }
    };

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public static void PreInit(FMLPreInitializationEvent preEvent)
    {
        proxy.preInit(preEvent);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        ModRecipes.initRecipes();
        NetworkRegistry.INSTANCE.registerGuiHandler(BoneAppleTea.instance, new GuiHandler());
        ModOreDictionary.init();
    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent postEvent)
    {
        proxy.postInit(postEvent);
    }
}
