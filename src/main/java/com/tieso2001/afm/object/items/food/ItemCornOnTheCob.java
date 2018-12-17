package com.tieso2001.afm.object.items.food;

import net.minecraft.item.ItemFood;

public class ItemCornOnTheCob extends ItemFood {

    public ItemCornOnTheCob(String name, int amount, boolean isWolfFood) {
        super(amount, isWolfFood);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

}