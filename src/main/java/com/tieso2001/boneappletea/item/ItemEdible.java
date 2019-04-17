package com.tieso2001.boneappletea.item;

import com.tieso2001.boneappletea.BoneAppleTea;
import net.minecraft.item.ItemFood;

public class ItemEdible extends ItemFood
{
    public ItemEdible(String name, int amount, boolean isWolfFood)
    {
        super(amount, isWolfFood);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(BoneAppleTea.TAB_BONE_APPLE_TEA);
    }
}
