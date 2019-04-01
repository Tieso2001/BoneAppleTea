package com.tieso2001.boneappletea.tab;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabCreative extends CreativeTabs
{
    public TabCreative(String id)
    {
        super(id);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ModItems.CORN);
    }
}
