package com.tieso2001.boneappletea.gui.handler;

import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFermenterBottle extends SlotItemHandler {

    public SlotFermenterBottle(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stack) {
        return PotionUtils.getPotionFromItem(stack) == PotionTypes.WATER;
    }

}
