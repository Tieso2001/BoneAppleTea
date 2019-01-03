package com.tieso2001.boneappletea.object.items.tools;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortarAndPestle extends Item {

    public ItemMortarAndPestle(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(1);
        setMaxDamage(15);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = new ItemStack(ModItems.MORTAR_AND_PESTLE, 1);
        stack.setItemDamage(itemStack.getItemDamage() + 1);
        return stack;
    }

}
