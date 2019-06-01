package com.tieso2001.boneappletea.block;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.item.Item;

public class BlockHops extends BlockTallCrops
{
    @Override
    protected Item getCrop()
    {
        return ModItems.HOPS;
    }

    @Override
    protected Item getSeed()
    {
        return ModItems.HOPS_SEEDS;
    }

    @Override
    public int getMaxHeight()
    {
        return 2;
    }
}
