package com.tieso2001.boneappletea.compat.oredict;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDictionary
{
    public static void init()
    {
        registerItems();
        registerLists();
    }

    private static void registerItems()
    {
        OreDictionary.registerOre("bottleBeer", ModItems.BOTTLE_BEER);

        OreDictionary.registerOre("cropBarley", ModItems.BARLEY);
        OreDictionary.registerOre("cropCorn", ModItems.CORN);
        OreDictionary.registerOre("cropHops", ModItems.HOPS);

        OreDictionary.registerOre("foodPopcorn", ModItems.POPCORN);
        OreDictionary.registerOre("foodRoastedCorn", ModItems.ROASTED_CORN);

        OreDictionary.registerOre("grainBarley", ModItems.BARLEY_GRAINS);

        OreDictionary.registerOre("maltBarley", ModItems.BARLEY_MALT);
        OreDictionary.registerOre("maltBarleyCrushed", ModItems.BARLEY_MALT_CRUSHED);

        OreDictionary.registerOre("seedBarley", ModItems.BARLEY_SEEDS);
        OreDictionary.registerOre("seedCorn", ModItems.CORN_KERNELS);
        OreDictionary.registerOre("seedHops", ModItems.HOPS_SEEDS);

        OreDictionary.registerOre("toolMortarandpestle", ModItems.MORTAR_AND_PESTLE);

        OreDictionary.registerOre("yeast", ModItems.YEAST);
    }

    private static void registerLists()
    {
        OreDictionary.registerOre("listAllgrain", ModItems.BARLEY);

        OreDictionary.registerOre("listAllseed", ModItems.BARLEY_SEEDS);
        OreDictionary.registerOre("listAllseed", ModItems.CORN_KERNELS);
        OreDictionary.registerOre("listAllseed", ModItems.HOPS_SEEDS);
    }

}
