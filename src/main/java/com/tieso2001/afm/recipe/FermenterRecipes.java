package com.tieso2001.afm.recipe;

import com.google.common.collect.Maps;
import com.tieso2001.afm.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

public class FermenterRecipes {

    private final Map<ItemStack, ItemStack> inputItemList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<FluidStack, FluidStack> fluidList = Maps.<FluidStack, FluidStack>newHashMap();


    public void register() {
        addFermentRecipe(ModItems.YEAST, ModItems.POTATO_MUSH, FluidRegistry.WATER, 1000, FluidRegistry.LAVA, 1000);
    }

    public void addFermentRecipe(Item inputItemOne, Item inputItemTwo, Fluid inputFluid, int inputFluidAmount, Fluid outputFluid, int outputFluidAmount) {
        this.inputItemList.put(new ItemStack(inputItemOne), new ItemStack(inputItemTwo));
        this.fluidList.put(new FluidStack(inputFluid, inputFluidAmount), new FluidStack(outputFluid, outputFluidAmount));
    }

    public static void addFluidEmptyRecipe() {
    }

}
