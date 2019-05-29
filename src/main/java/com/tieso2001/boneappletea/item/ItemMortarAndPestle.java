package com.tieso2001.boneappletea.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends Item
{
    public ItemMortarAndPestle()
    {

    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        return itemStack.copy();
    }
}
