package com.tieso2001.afm.recipe;

import com.tieso2001.afm.init.ModItems;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FermenterRecipes {

    public static void register() {
        addFermentRecipe(ModItems.YEAST, ModItems.POTATO_MUSH, FluidRegistry.WATER, 1000, FluidRegistry.LAVA, 1000);
    }

    public static void addFermentRecipe(Item inputItemOne, Item inputItemTwo, Fluid inputFluid, int inputFluidAmount, Fluid outputFluid, int outputFluidAmount) {
    }

    public static void addFluidEmptyRecipe() {
    }

}
