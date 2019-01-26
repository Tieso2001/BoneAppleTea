package com.tieso2001.boneappletea.item;

import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        setTranslationKey(name);
        setRegistryName(name);
    }

}
