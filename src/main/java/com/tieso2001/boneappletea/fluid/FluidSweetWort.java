package com.tieso2001.boneappletea.fluid;

import com.tieso2001.boneappletea.BoneAppleTea;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidSweetWort extends Fluid
{
    public FluidSweetWort()
    {
        super("sweet_wort", new ResourceLocation(BoneAppleTea.MODID, "blocks/sweet_wort_still"), new ResourceLocation(BoneAppleTea.MODID, "blocks/sweet_wort_flow"));
    }

    @Override
    public Fluid setBlock(Block block)
    {
        return null;
    }
}
