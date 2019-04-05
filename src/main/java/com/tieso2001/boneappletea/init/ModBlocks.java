package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.block.BlockCorn;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(BoneAppleTea.MODID)
public class ModBlocks
{
    public static Block CORN = new BlockCorn("corn");
}
