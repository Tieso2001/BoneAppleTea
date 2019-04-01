package com.tieso2001.boneappletea.event;

import com.tieso2001.boneappletea.BoneAppleTea;
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
        registerModel(ModItems.CORN);
    }

    private static void registerModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
