package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.Main;
import com.tieso2001.boneappletea.object.blocks.BlockFluid;
import com.tieso2001.boneappletea.object.blocks.crops.BlockBarley;
import com.tieso2001.boneappletea.object.blocks.crops.BlockCorn;
import com.tieso2001.boneappletea.object.blocks.fermenter.BlockFermenter;
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

    //Liquids
    public static Block BEER;

    public static void init() {
        BARLEY = new BlockBarley("barley");
        CORN = new BlockCorn("corn");
        FERMENTER = new BlockFermenter("fermenter", Material.IRON);
        BEER = new BlockFluid("beer", ModFluids.BEER, Material.WATER);
    }

    public static void register() {
        registerBlock(BARLEY, false);
        registerBlock(CORN, false);
        registerBlock(FERMENTER, true);
        registerBlock(BEER, false);
    }

    public static void registerRenders() {
        registerRender(BARLEY);
        registerRender(CORN);
        registerRender(FERMENTER);
        registerRender(BEER);
    }

    public static void registerBlock(Block block, boolean registerItem) {
        ForgeRegistries.BLOCKS.register(block);

        if (registerItem) {
            registerItemBlock(block);
            block.setCreativeTab(Main.TAB_BONEAPPLETEA);
        }

    }

    public static void registerItemBlock(Block block) {
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    public static void registerRender(Block block) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
