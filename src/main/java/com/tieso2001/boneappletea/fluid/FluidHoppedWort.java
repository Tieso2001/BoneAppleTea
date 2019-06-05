package com.tieso2001.boneappletea.fluid;

import com.tieso2001.boneappletea.BoneAppleTea;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidHoppedWort extends Fluid
{
    public FluidHoppedWort()
    {
        super("hopped_wort", new ResourceLocation(BoneAppleTea.MODID, "blocks/hopped_wort_still"), new ResourceLocation(BoneAppleTea.MODID, "blocks/hopped_wort_flow"));
    }

    @Override
    public Fluid setBlock(Block block)
    {
        return null;
    }
}