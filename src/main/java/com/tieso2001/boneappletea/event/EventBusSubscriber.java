package com.tieso2001.boneappletea.event;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.block.BlockBarley;
import com.tieso2001.boneappletea.block.BlockCorn;
import com.tieso2001.boneappletea.block.BlockStockPot;
import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.item.ItemMortarAndPestle;
import com.tieso2001.boneappletea.tile.TileStockPot;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MODID)
public class EventBusSubscriber
{
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        final Block[] blocks = {
                new BlockBarley().setRegistryName("barley").setTranslationKey(BoneAppleTea.MODID + "." + "barley"),
                new BlockCorn().setRegistryName("corn").setTranslationKey(BoneAppleTea.MODID + "." + "corn"),
                new BlockStockPot().setRegistryName("stock_pot").setTranslationKey(BoneAppleTea.MODID + "." + "stock_pot").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA)
        };

        event.getRegistry().registerAll(blocks);
        GameRegistry.registerTileEntity(TileStockPot.class, BoneAppleTea.MODID + "_stock_pot");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        final Item[] items = {
                new Item().setRegistryName("barley").setTranslationKey(BoneAppleTea.MODID + "." + "barley").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new Item().setRegistryName("barley_grains").setTranslationKey(BoneAppleTea.MODID + "." + "barley_grains").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new Item().setRegistryName("barley_malt").setTranslationKey(BoneAppleTea.MODID + "." + "barley_malt").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemSeeds(ModBlocks.BARLEY, Blocks.FARMLAND).setRegistryName("barley_seeds").setTranslationKey(BoneAppleTea.MODID + "." + "barley_seeds").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemFood(1, 0.6F,false).setRegistryName("corn").setTranslationKey(BoneAppleTea.MODID + "." + "corn").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemSeedFood(1, 0.3F, ModBlocks.CORN, Blocks.FARMLAND).setRegistryName("corn_kernels").setTranslationKey(BoneAppleTea.MODID + "." + "corn_kernels").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new Item().setRegistryName("crushed_barley_malt").setTranslationKey(BoneAppleTea.MODID + "." + "crushed_barley_malt").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemMortarAndPestle().setRegistryName("mortar_and_pestle").setTranslationKey(BoneAppleTea.MODID + "." + "mortar_and_pestle").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemFood(2, 0.6F, false).setRegistryName("popcorn").setTranslationKey(BoneAppleTea.MODID + "." + "popcorn").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA),
                new ItemFood(4, 0.6F, false).setRegistryName("roasted_corn").setTranslationKey(BoneAppleTea.MODID + "." + "roasted_corn").setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA)
        };

        final Item[] itemBlocks = {
                new ItemBlock(ModBlocks.STOCK_POT).setRegistryName(ModBlocks.STOCK_POT.getRegistryName())
        };

        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);
    }
}
