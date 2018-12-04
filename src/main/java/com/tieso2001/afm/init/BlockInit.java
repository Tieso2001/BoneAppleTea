package com.tieso2001.afm.init;

import com.tieso2001.afm.objects.blocks.crops.BlockBarley;
import com.tieso2001.afm.objects.blocks.crops.BlockCornPlant;
import com.tieso2001.afm.objects.blocks.machines.BlockFermenter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block BARLEY = new BlockBarley("barley");
    public static final Block CORN_PLANT = new BlockCornPlant("corn_plant");
    public static final Block FERMENTER = new BlockFermenter("fermenter", Material.IRON);
}
