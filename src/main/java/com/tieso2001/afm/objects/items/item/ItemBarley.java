package com.tieso2001.afm.objects.items.item;

import com.tieso2001.afm.Main;
import com.tieso2001.afm.init.ItemInit;
import com.tieso2001.afm.util.IHasModel;
import net.minecraft.item.Item;

public class ItemBarley extends Item implements IHasModel {

    public ItemBarley(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.afmtab);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
