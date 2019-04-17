package com.tieso2001.boneappletea.block;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.item.Item;

public class BlockCorn extends BlockTallCrops
{
    @Override
    public Item getCrop()
    {
        return ModItems.CORN;
    }

    @Override
    public Item getSeed()
    {
        return ModItems.CORN_KERNELS;
    }

    @Override
    public int getMaxHeight()
    {
        return 3;
    }
}
