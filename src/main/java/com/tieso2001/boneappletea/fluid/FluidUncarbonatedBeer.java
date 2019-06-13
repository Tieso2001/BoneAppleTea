package com.tieso2001.boneappletea.fluid;

import com.tieso2001.boneappletea.BoneAppleTea;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidUncarbonatedBeer extends Fluid
{
    public FluidUncarbonatedBeer()
    {
        super("uncarbonated_beer", new ResourceLocation(BoneAppleTea.MODID, "blocks/uncarbonated_beer_still"), new ResourceLocation(BoneAppleTea.MODID, "blocks/uncarbonated_beer_flow"));
    }

    @Override
    public Fluid setBlock(Block block)
    {
        return null;
    }
}
