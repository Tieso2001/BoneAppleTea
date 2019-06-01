package com.tieso2001.boneappletea.block;

import com.tieso2001.boneappletea.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidSweetWort extends BlockFluidClassic
{
    public BlockFluidSweetWort()
    {
        super(ModFluids.SWEET_WORT, Material.WATER);
    }
}
