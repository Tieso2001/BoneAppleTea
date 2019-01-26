package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.block.BlockBaseCrops;
import com.tieso2001.boneappletea.block.BlockFermenter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks {

    //Crops
    public static Block BARLEY;
    public static Block CORN;

    //Machines
    public static Block FERMENTER;

    public static void init() {
        BARLEY = new BlockBaseCrops("barley", ModItems.BARLEY_SEEDS, ModItems.BARLEY);
        CORN = new BlockBaseCrops("corn", ModItems.CORN_KERNELS, ModItems.CORN);
        FERMENTER = new BlockFermenter("fermenter", Material.IRON);
    }

    public static void register() {
        registerBlock(BARLEY, false);
        registerBlock(CORN, false);
        registerBlock(FERMENTER, true);
    }

    public static void registerRenders() {
        registerRender(BARLEY);
        registerRender(CORN);
        registerRender(FERMENTER);
    }

    public static void registerBlock(Block block, boolean registerItem) {
        ForgeRegistries.BLOCKS.register(block);

        if (registerItem) {
            registerItemBlock(block);
            block.setCreativeTab(BoneAppleTea.TAB_BONEAPPLETEA);
        }

    }

    public static void registerItemBlock(Block block) {
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    public static void registerRender(Block block) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}