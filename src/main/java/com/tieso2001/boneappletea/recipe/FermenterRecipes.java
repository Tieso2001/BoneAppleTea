package com.tieso2001.boneappletea.recipe;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class FermenterRecipes {

    public static Map<ItemStack, FluidStack[]> RECIPES = new HashMap<>();

    public static void register() {
        addFermentRecipe(ModItems.YEAST, 1, FluidRegistry.WATER, 1000, FluidRegistry.LAVA, 1000);
    }

    public static void addFermentRecipe(Item inputItem, int inputItemAmount, Fluid inputFluid, int inputFluidAmount, Fluid outputFluid, int outputFluidAmount) {
        FluidStack[] fluids = new FluidStack[2];
        fluids[0] = new FluidStack(inputFluid, inputFluidAmount);
        fluids[1] = new FluidStack(outputFluid, outputFluidAmount);
        ItemStack itemStack = new ItemStack(inputItem, inputItemAmount);
        RECIPES.put(itemStack, fluids);
    }

}
