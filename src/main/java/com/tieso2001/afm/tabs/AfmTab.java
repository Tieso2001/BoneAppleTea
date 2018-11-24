package com.tieso2001.afm.tabs;

import com.tieso2001.afm.init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class AfmTab extends CreativeTabs {
    public AfmTab(String label) {
        super("afmtab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemInit.CORN);
    }
}
