package com.tieso2001.boneappletea.block;

import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BlockCorn extends BlockTallCrops
{
    public BlockCorn(String name)
    {
        super();
        setRegistryName(name);
        setTranslationKey(name);
    }

    @Override
    protected Item getSeed()
    {
        return ModItems.CORN_KERNELS;
    }

    @Override
    protected Item getCrop()
    {
        return ModItems.CORN;
    }

    @Override
    public Block getCropBlock()
    {
        return ModBlocks.CORN;
    }

    @Override
    public int getMaxHeight()
    {
        return 3;
    }
}
