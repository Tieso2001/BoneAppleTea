package com.tieso2001.afm.init;

import com.tieso2001.afm.objects.blocks.BlockCornPlant;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block CORN_PLANT = new BlockCornPlant("corn_plant");
}
