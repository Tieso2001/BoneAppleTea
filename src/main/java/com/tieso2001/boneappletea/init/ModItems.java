package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.item.ItemCornSeeds;
import com.tieso2001.boneappletea.item.ItemEdible;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(BoneAppleTea.MODID)
public class ModItems
{
    public static Item CORN = new ItemEdible("corn",1, false);
    public static Item CORN_KERNELS = new ItemCornSeeds("corn_kernels",1, false);
    public static Item POPCORN = new ItemEdible("popcorn",2, false);
    public static Item ROASTED_CORN = new ItemEdible("roasted_corn",4, false);
}
