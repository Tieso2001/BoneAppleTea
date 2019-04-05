package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.item.ItemCornKernels;
import com.tieso2001.boneappletea.item.ItemFoodItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(BoneAppleTea.MODID)
public class ModItems
{
    public static Item CORN = new ItemFoodItem("corn",1, false);
    public static Item CORN_KERNELS = new ItemCornKernels("corn_kernels",1, false);
    public static Item CORN_ON_THE_COB = new ItemFoodItem("corn_on_the_cob",4, false);
    public static Item POPCORN = new ItemFoodItem("popcorn",2, false);
}
