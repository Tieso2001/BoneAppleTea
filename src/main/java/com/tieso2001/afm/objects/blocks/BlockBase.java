package com.tieso2001.afm.objects.blocks;

import com.tieso2001.afm.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

    public BlockBase(String name, Material material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

}
