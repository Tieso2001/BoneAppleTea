package com.tieso2001.boneappletea.gui.handler;

import com.tieso2001.boneappletea.recipe.FermenterRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFermenterInput extends SlotItemHandler {

    public SlotFermenterInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) { return FermenterRecipes.getInstance().isItemInputValid(stack); }

}
