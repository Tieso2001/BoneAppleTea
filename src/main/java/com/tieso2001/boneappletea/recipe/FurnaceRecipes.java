package com.tieso2001.boneappletea.recipe;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FurnaceRecipes {

    public static void register() {
        addFurnaceRecipe(ModItems.CORN_KERNELS, ModItems.POPCORN, 0.35F);
        addFurnaceRecipe(ModItems.CORN, ModItems.CORN_ON_THE_COB, 0.35F);
    }

    public static void addFurnaceRecipe(Item inputItem, Item outputItem, float experience) {
        GameRegistry.addSmelting(new ItemStack(inputItem), new ItemStack(outputItem), experience);
    }

}
