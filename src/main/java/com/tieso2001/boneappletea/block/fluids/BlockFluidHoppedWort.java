package com.tieso2001.boneappletea.block.fluids;

import com.tieso2001.boneappletea.init.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidHoppedWort extends BlockFluidClassic
{
    public BlockFluidHoppedWort()
    {
        super(ModFluids.HOPPED_WORT, Material.WATER);
    }
}
