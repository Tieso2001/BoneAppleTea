package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.item.ItemFoodItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(BoneAppleTea.MODID)
public class ModItems
{
    public static Item CORN = new ItemFoodItem("corn",1, false);
}
