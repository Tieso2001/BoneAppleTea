package com.tieso2001.boneappletea.event;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MODID)
public class EventSubscriber
{
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        final Block[] blocks = {

        };

        event.getRegistry().registerAll(blocks);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        final Item[] items = {
                ModItems.CORN
        };

        final Item[] itemBlocks = {

        };

        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);
    }
}
