package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.item.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
    public static Item CORN;
    public static Item CORN_KERNELS;
    public static Item CORN_ON_THE_COB;
    public static Item FRIKANDEL;
    public static Item FRIKANDEL_ROLL;
    public static Item GOLDEN_FRIKANDEL_ROLL;
    public static Item POPCORN;

    //Drinks
    public static Item BEER_BOTTLE;
    public static Item VODKA_BOTTLE;

    //Tools
    public static Item MORTAR_AND_PESTLE;

    public static void init() {
        BARLEY = new ItemBarley("barley");
        BARLEY_SEEDS = new ItemBarleySeeds("barley_seeds");
        BARLEY_GRAINS = new ItemBarleyGrains("barley_grains");
        POTATO_MUSH = new ItemPotatoMush("potato_mush");
        YEAST = new ItemYeast("yeast");

        CORN = new ItemCorn("corn", 3, false);
        CORN_KERNELS = new ItemCornKernels("corn_kernels", 1, false);
        CORN_ON_THE_COB = new ItemCornOnTheCob("corn_on_the_cob", 5, false);
        FRIKANDEL = new ItemFrikandel("frikandel", 4, false);
        FRIKANDEL_ROLL = new ItemFrikandelRoll("frikandel_roll", 8, false);
        GOLDEN_FRIKANDEL_ROLL = new ItemGoldenFrikandelRoll("golden_frikandel_roll", 20, false);
        POPCORN = new ItemPopcorn("popcorn", 2, false);

        BEER_BOTTLE = new ItemBeerBottle("beer_bottle");
        VODKA_BOTTLE = new ItemVodkaBottle("vodka_bottle");

        MORTAR_AND_PESTLE = new ItemMortarAndPestle("mortar_and_pestle");
    }

    public static void register() {
        registerItem(BARLEY);
        registerItem(BARLEY_GRAINS);
        registerItem(BARLEY_SEEDS);
        registerItem(POTATO_MUSH);
        registerItem(YEAST);
        registerItem(CORN);
        registerItem(CORN_KERNELS);
        registerItem(CORN_ON_THE_COB);
        registerItem(FRIKANDEL);
        registerItem(FRIKANDEL_ROLL);
        registerItem(GOLDEN_FRIKANDEL_ROLL);
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
        registerRender(CORN);
        registerRender(CORN_KERNELS);
        registerRender(CORN_ON_THE_COB);
        registerRender(FRIKANDEL);
        registerRender(FRIKANDEL_ROLL);
        registerRender(GOLDEN_FRIKANDEL_ROLL);
        registerRender(POPCORN);
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
