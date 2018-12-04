package com.tieso2001.afm.init;

import com.tieso2001.afm.objects.items.food.*;
import com.tieso2001.afm.objects.items.item.ItemBarley;
import com.tieso2001.afm.objects.items.item.ItemBarleySeeds;
import com.tieso2001.afm.objects.items.item.ItemPotatoMush;
import com.tieso2001.afm.objects.items.item.ItemYeast;
import com.tieso2001.afm.objects.items.tools.ItemMortarAndPestle;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    //Items
    public static final Item BARLEY = new ItemBarley("barley");
    public static final Item BARLEY_SEEDS = new ItemBarleySeeds("barley_seeds");
    public static final Item POTATO_MUSH = new ItemPotatoMush("potato_mush");
    public static final Item YEAST = new ItemYeast("yeast");

    //Food
    public static final Item CORN = new ItemCorn("corn", 3, false);
    public static final Item CORN_KERNELS = new ItemCornKernels("corn_kernels", 1, false);
    public static final Item CORN_ON_THE_COB = new ItemCornOnTheCob("corn_on_the_cob", 5, false);
    public static final Item POPCORN = new ItemPopcorn("popcorn", 2, false);

    //Tools
    public static final Item MORTAR_AND_PESTLE = new ItemMortarAndPestle("mortar_and_pestle");
}
