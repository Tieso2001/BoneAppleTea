package com.tieso2001.boneappletea.event;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MODID, value = Side.CLIENT)
public class ClientEventSubscriber
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        registerModel(ModItems.BARLEY);
        registerModel(ModItems.BARLEY_GRAINS);
        registerModel(ModItems.BARLEY_MALT);
        registerModel(ModItems.BARLEY_MALT_CRUSHED);
        registerModel(ModItems.BARLEY_SEEDS);
        registerModel(ModItems.CORN);
        registerModel(ModItems.CORN_KERNELS);
        registerModel(ModItems.HOPS);
        registerModel(ModItems.HOPS_SEEDS);
        registerModel(ModItems.MORTAR_AND_PESTLE);
        registerModel(ModItems.POPCORN);
        registerModel(ModItems.ROASTED_CORN);
        registerModel(Item.getItemFromBlock(ModBlocks.STOCK_POT));
        registerModel(Item.getItemFromBlock(ModBlocks.WOODEN_BARREL));
        registerModel(ModItems.YEAST);
    }

    private static void registerModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
