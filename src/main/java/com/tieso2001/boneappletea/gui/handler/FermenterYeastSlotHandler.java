package com.tieso2001.boneappletea.gui.handler;

import com.tieso2001.boneappletea.recipe.FermenterRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class FermenterYeastSlotHandler extends ItemStackHandler {

    public FermenterYeastSlotHandler(int size)
    {
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) { return FermenterRecipes.getInstance().isItemYeastValid(stack); }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!isItemValid(slot, stack)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }

}