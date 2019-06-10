package com.tieso2001.boneappletea.block.fluids;

import com.tieso2001.boneappletea.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidBeer extends BlockFluidClassic
{
    public BlockFluidBeer()
    {
        super(ModFluids.BEER, Material.WATER);
    }
}
