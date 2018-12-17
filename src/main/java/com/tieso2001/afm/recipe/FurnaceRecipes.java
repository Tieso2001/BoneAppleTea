package com.tieso2001.afm.recipe;

import com.tieso2001.afm.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnaceRecipes {

    public static void register() {
        addFurnaceRecipe(ModItems.CORN_KERNELS, ModItems.POPCORN);
        addFurnaceRecipe(ModItems.CORN, ModItems.CORN_ON_THE_COB);
    }

    public static void addFurnaceRecipe(Item inputItem, Item outputItem) {
        GameRegistry.addSmelting(new ItemStack(inputItem), new ItemStack(outputItem), 0.35F);
    }

}