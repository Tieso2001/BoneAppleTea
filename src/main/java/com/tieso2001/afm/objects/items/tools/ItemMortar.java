package com.tieso2001.afm.objects.items.tools;

import com.tieso2001.afm.Main;
import com.tieso2001.afm.init.ItemInit;
import com.tieso2001.afm.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMortar extends Item implements IHasModel {

    public ItemMortar(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.afmtab);

        setMaxStackSize(1);
        setMaxDamage(15);

        ItemInit.ITEMS.add(this);
    }


    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = new ItemStack(ItemInit.MORTAR, 1);
        stack.setItemDamage(itemStack.getItemDamage() + 1);
        return stack;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }


}
