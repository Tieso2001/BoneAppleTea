package net.tieso2001.boneappletea.inventory.container;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class CaskInputSlot extends SlotItemHandler {

    public CaskInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return super.isItemValid(stack); // Check for recipe input ingredient should be added
    }
}
