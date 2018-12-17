package com.tieso2001.afm.object.items.food;

import net.minecraft.item.ItemFood;

public class ItemPopcorn extends ItemFood {

    public ItemPopcorn(String name, int amount, boolean isWolfFood) {
        super(amount, isWolfFood);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

}

