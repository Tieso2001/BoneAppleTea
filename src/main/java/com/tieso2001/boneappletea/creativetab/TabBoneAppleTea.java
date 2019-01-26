package com.tieso2001.boneappletea.creativetab;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabBoneAppleTea extends CreativeTabs {

    public TabBoneAppleTea() {
        super("tab_boneappletea");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.CORN);
    }

}