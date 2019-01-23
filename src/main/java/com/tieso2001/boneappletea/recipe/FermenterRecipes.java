package com.tieso2001.boneappletea.recipe;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class FermenterRecipes {

    private static final FermenterRecipes INSTANCE = new FermenterRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> fermentingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();

    public static FermenterRecipes getInstance() {
        return INSTANCE;
    }

    private FermenterRecipes() {
        addFermentingRecipe(new ItemStack(ModItems.BARLEY), new ItemStack(Items.GLASS_BOTTLE), new ItemStack(ModItems.BEER_BUCKET));
    }


    public void addFermentingRecipe(ItemStack inputItem, ItemStack inputBottle, ItemStack outputBottle) {
        if(getFermentingResult(inputItem, inputBottle) != ItemStack.EMPTY) return;
        this.fermentingList.put(inputItem, inputBottle, outputBottle);
    }

    public ItemStack getFermentingResult(ItemStack inputItem, ItemStack inputBottle) {
        for(Map.Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.fermentingList.columnMap().entrySet()) {
            if(this.compareItemStacks(inputItem, (ItemStack)entry.getKey())) {
                for(Map.Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
                    if(this.compareItemStacks(inputBottle, (ItemStack)ent.getKey())) {
                        return (ItemStack)ent.getValue();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

}
