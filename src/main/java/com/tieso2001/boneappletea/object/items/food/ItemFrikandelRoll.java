package com.tieso2001.boneappletea.object.items.food;

import net.minecraft.item.ItemFood;

public class ItemFrikandelRoll extends ItemFood {

    public ItemFrikandelRoll(String name, int amount, boolean isWolfFood) {
        super(amount, isWolfFood);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

}
