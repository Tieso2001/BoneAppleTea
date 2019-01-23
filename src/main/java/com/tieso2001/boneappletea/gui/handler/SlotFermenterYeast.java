package com.tieso2001.boneappletea.gui.handler;

import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFermenterYeast extends SlotItemHandler {

    public SlotFermenterYeast(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == ModItems.YEAST;
    }

}
