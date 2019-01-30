package com.tieso2001.boneappletea.integration;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDictionary {

    public static void register() {
        addOreDict(ModItems.BARLEY, "cropBarley");
        addOreDict(ModItems.BARLEY_GRAINS, "grainBarley");
        addOreDict(ModItems.BARLEY_SEEDS, "seedBarley");
        addOreDict(ModItems.CORN, "cropCorn");
        addOreDict(ModItems.CORN_KERNELS, "seedCorn");
        addOreDict(ModItems.POPCORN, "foodPopcorn");
    }

    public static void addOreDict(Item item, String name) {
        OreDictionary.registerOre(name, item);
    }

    public static void addOreDict(Block block, String name) {
        OreDictionary.registerOre(name, block);
    }

}