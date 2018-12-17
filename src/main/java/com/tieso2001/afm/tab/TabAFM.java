package com.tieso2001.afm.tab;

import com.tieso2001.afm.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabAFM extends CreativeTabs {

    public TabAFM() {
        super("tabafm");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.CORN);
    }

}
