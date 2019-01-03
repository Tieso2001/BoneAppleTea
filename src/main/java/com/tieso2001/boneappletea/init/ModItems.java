package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.Main;
import com.tieso2001.boneappletea.object.items.food.ItemCorn;
import com.tieso2001.boneappletea.object.items.food.ItemCornKernels;
import com.tieso2001.boneappletea.object.items.food.ItemCornOnTheCob;
import com.tieso2001.boneappletea.object.items.food.ItemPopcorn;
import com.tieso2001.boneappletea.object.items.item.ItemBarley;
import com.tieso2001.boneappletea.object.items.item.ItemBarleySeeds;
import com.tieso2001.boneappletea.object.items.item.ItemPotatoMush;
import com.tieso2001.boneappletea.object.items.item.ItemYeast;
import com.tieso2001.boneappletea.object.items.tools.ItemBeerBucket;
import com.tieso2001.boneappletea.object.items.tools.ItemMortarAndPestle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModItems {

    //Items
    public static Item BARLEY;
    public static Item BARLEY_SEEDS;
    public static Item POTATO_MUSH;
    public static Item YEAST;

    //Food
    public static Item CORN;
    public static Item CORN_KERNELS;
    public static Item CORN_ON_THE_COB;
    public static Item POPCORN;

    //Tools
    public static Item BEER_BUCKET;
    public static Item MORTAR_AND_PESTLE;

    public static void init() {
        BARLEY = new ItemBarley("barley");
        BARLEY_SEEDS = new ItemBarleySeeds("barley_seeds");
        POTATO_MUSH = new ItemPotatoMush("potato_mush");
        YEAST = new ItemYeast("yeast");

        CORN = new ItemCorn("corn", 3, false);
        CORN_KERNELS = new ItemCornKernels("corn_kernels", 1, false);
        CORN_ON_THE_COB = new ItemCornOnTheCob("corn_on_the_cob", 5, false);
        POPCORN = new ItemPopcorn("popcorn", 2, false);

        BEER_BUCKET = new ItemBeerBucket("beer_bucket");
        MORTAR_AND_PESTLE = new ItemMortarAndPestle("mortar_and_pestle");
    }

    public static void register() {
        registerItem(BARLEY);
        registerItem(BARLEY_SEEDS);
        registerItem(POTATO_MUSH);
        registerItem(YEAST);
        registerItem(CORN);
        registerItem(CORN_KERNELS);
        registerItem(CORN_ON_THE_COB);
        registerItem(POPCORN);
        registerItem(BEER_BUCKET);
        registerItem(MORTAR_AND_PESTLE);
    }

    public static void registerRenders() {
        registerRender(BARLEY);
        registerRender(BARLEY_SEEDS);
        registerRender(POTATO_MUSH);
        registerRender(YEAST);
        registerRender(CORN);
        registerRender(CORN_KERNELS);
        registerRender(CORN_ON_THE_COB);
        registerRender(POPCORN);
        registerRender(BEER_BUCKET);
        registerRender(MORTAR_AND_PESTLE);
    }

    public static void registerItem(Item item) {
        item.setCreativeTab(Main.TAB_BONEAPPLETEA);
        ForgeRegistries.ITEMS.register(item);
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
