package com.tieso2001.boneappletea.block;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;

public class BlockBarley extends BlockCrops
{
    public BlockBarley()
    {

    }

    @Override
    protected Item getSeed()
    {
        return ModItems.BARLEY_SEEDS;
    }

    @Override
    protected Item getCrop()
    {
        return ModItems.BARLEY;
    }
}
