package com.tieso2001.boneappletea.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends Item
{
    public ItemMortarAndPestle()
    {
        this.setMaxStackSize(1);
        this.setMaxDamage(15);
    }

    @Override
    public boolean isRepairable()
    {
        return false;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack stack = itemStack.copy();
        stack.setItemDamage(itemStack.getItemDamage() + 1);
        return stack;
    }
}
