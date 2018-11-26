package com.tieso2001.afm.objects.items.tools;

import com.tieso2001.afm.Main;
import com.tieso2001.afm.init.ItemInit;
import com.tieso2001.afm.util.IHasModel;
import net.minecraft.item.Item;

public class ItemMortar extends Item implements IHasModel {

    public ItemMortar(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.afmtab);
        setNoRepair();

        setMaxStackSize(1);
        setMaxDamage(15);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }


}
