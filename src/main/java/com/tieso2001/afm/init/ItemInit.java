package com.tieso2001.afm.init;

import com.tieso2001.afm.objects.items.food.ItemAfmFood;
import com.tieso2001.afm.objects.items.food.ItemCornKernels;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    //Food
    public static final Item CORN = new ItemAfmFood("corn", 3, false);
    public static final Item CORN_KERNELS = new ItemCornKernels("corn_kernels", 1, false);
    public static final Item POPCORN = new ItemAfmFood("popcorn", 2, false);
    public static final Item CORN_ON_THE_COB = new ItemAfmFood("corn_on_the_cob", 5, false);
}
