package com.tieso2001.boneappletea.gui.handler;

import com.tieso2001.boneappletea.recipe.FermenterRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;


public class SlotFermenterBottle extends SlotItemHandler {

    public SlotFermenterBottle(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) { return FermenterRecipes.getInstance().isItemBottleValid(stack); }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) { return 1; }

}
