package com.tieso2001.boneappletea.event;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.block.BlockCorn;
import com.tieso2001.boneappletea.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeedFood;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MODID)
public class EventBusSubscriber
{
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        final Block[] blocks = {
                new BlockCorn().setRegistryName("corn").setTranslationKey("corn")
        };

        event.getRegistry().registerAll(blocks);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        final Item[] items = {
                new ItemFood(1, 0.6F,false).setRegistryName("corn").setTranslationKey("corn").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemSeedFood(1, 0.3F, ModBlocks.CORN, Blocks.FARMLAND).setRegistryName("corn_kernels").setTranslationKey("corn_kernels").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemFood(2, 0.6F, false).setRegistryName("popcorn").setTranslationKey("popcorn").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemFood(4, 0.6F, false).setRegistryName("roasted_corn").setTranslationKey("roasted_corn").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA)
        };

        final Item[] itemBlocks = {

        };

        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);
    }
}
