package com.tieso2001.boneappletea.recipe;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{
    public static void initSmelting()
    {
        GameRegistry.addSmelting(ModItems.CORN, new ItemStack(ModItems.CORN_ON_THE_COB), 0.35F);
        GameRegistry.addSmelting(ModItems.CORN_KERNELS, new ItemStack(ModItems.POPCORN), 0.35F);
    }
}
