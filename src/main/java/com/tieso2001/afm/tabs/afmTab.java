package com.tieso2001.afm.tabs;

import com.tieso2001.afm.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class afmTab extends CreativeTabs {
    public afmTab(String label) {
        super("afmtab");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.CORN);
    }
}
