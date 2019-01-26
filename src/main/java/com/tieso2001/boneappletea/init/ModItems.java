package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.item.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModItems {

    //Items
    public static Item BARLEY;
    public static Item BARLEY_GRAINS;
    public static Item BARLEY_SEEDS;
    public static Item POTATO_MUSH;
    public static Item YEAST;

    //Food
    public static Item COOKED_MINCED_MEAT_SAUSAGE;
    public static Item CORN;
    public static Item CORN_KERNELS;
    public static Item CORN_ON_THE_COB;
    public static Item MINCED_MEAT;
    public static Item MINCED_MEAT_SAUSAGE;
    public static Item MINCED_MEAT_SAUSAGE_ROLL;
    public static Item POPCORN;

    //Drinks
    public static Item BEER_BOTTLE;
    public static Item VODKA_BOTTLE;

    //Tools
    public static Item MORTAR_AND_PESTLE;

    public static void init() {
        BARLEY = new ItemBase("barley");
        BARLEY_SEEDS = new ItemBaseSeeds("barley_seeds", ModBlocks.BARLEY);
        BARLEY_GRAINS = new ItemBase("barley_grains");
        POTATO_MUSH = new ItemBase("potato_mush");
        YEAST = new ItemBase("yeast");

        COOKED_MINCED_MEAT_SAUSAGE = new ItemBaseFood("cooked_minced_meat_sausage", 4, false);
        CORN = new ItemBaseFood("corn", 3, false);
        CORN_KERNELS = new ItemCornKernels("corn_kernels", 1, false);
        CORN_ON_THE_COB = new ItemBaseFood("corn_on_the_cob", 5, false);
        MINCED_MEAT = new ItemBaseFood("minced_meat", 2, false);
        MINCED_MEAT_SAUSAGE = new ItemBaseFood("minced_meat_sausage", 3, false);
        MINCED_MEAT_SAUSAGE_ROLL = new ItemBaseFood("minced_meat_sausage_roll", 6, false);
        POPCORN = new ItemBaseFood("popcorn", 2, false);

        BEER_BOTTLE = new ItemBaseDrinks("beer_bottle", MobEffects.STRENGTH);
        VODKA_BOTTLE = new ItemBaseDrinks("vodka_bottle", MobEffects.SPEED);

        MORTAR_AND_PESTLE = new ItemMortarAndPestle("mortar_and_pestle");
    }

    public static void register() {
        registerItem(BARLEY);
        registerItem(BARLEY_GRAINS);
        registerItem(BARLEY_SEEDS);
        registerItem(POTATO_MUSH);
        registerItem(YEAST);
        registerItem(COOKED_MINCED_MEAT_SAUSAGE);
        registerItem(CORN);
        registerItem(CORN_KERNELS);
        registerItem(CORN_ON_THE_COB);
        registerItem(MINCED_MEAT);
        registerItem(MINCED_MEAT_SAUSAGE);
        registerItem(MINCED_MEAT_SAUSAGE_ROLL);
        registerItem(POPCORN);
        registerItem(BEER_BOTTLE);
        registerItem(VODKA_BOTTLE);
        registerItem(MORTAR_AND_PESTLE);
    }

    public static void registerRenders() {
        registerRender(BARLEY);
        registerRender(BARLEY_GRAINS);
        registerRender(BARLEY_SEEDS);
        registerRender(POTATO_MUSH);
        registerRender(YEAST);
        registerRender(COOKED_MINCED_MEAT_SAUSAGE);
        registerRender(CORN);
        registerRender(CORN_KERNELS);
        registerRender(CORN_ON_THE_COB);
        registerRender(POPCORN);
        registerRender(MINCED_MEAT);
        registerRender(MINCED_MEAT_SAUSAGE);
        registerRender(MINCED_MEAT_SAUSAGE_ROLL);
        registerRender(BEER_BOTTLE);
        registerRender(VODKA_BOTTLE);
        registerRender(MORTAR_AND_PESTLE);
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(BoneAppleTea.TAB_BONEAPPLETEA);
        ForgeRegistries.ITEMS.register(item);
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}