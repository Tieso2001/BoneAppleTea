package com.tieso2001.boneappletea.recipe;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;

import java.util.Map;

public class FermenterRecipes {

    private static final FermenterRecipes INSTANCE = new FermenterRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> fermentingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();

    public static FermenterRecipes getInstance() {
        return INSTANCE;
    }

    private FermenterRecipes() {
        addFermentingRecipe(new ItemStack(ModItems.BARLEY), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ModItems.BEER_BUCKET));
    }


    public void addFermentingRecipe(ItemStack inputItem, ItemStack inputBottle, ItemStack outputBottle) {
        if(getFermentingResult(inputItem, inputBottle) != ItemStack.EMPTY) return;
        this.fermentingList.put(inputItem, inputBottle, outputBottle);
    }

    public ItemStack getFermentingResult(ItemStack inputItem, ItemStack inputBottle) {
        for(Map.Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.fermentingList.columnMap().entrySet()) {
            if(this.compareItemStacks(inputBottle, (ItemStack)entry.getKey())) {
                for(Map.Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
                    if(this.compareItemStacks(inputItem, (ItemStack)ent.getKey())) {
                        return (ItemStack)ent.getValue();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() == Items.POTIONITEM && stack2.getItem() == Items.POTIONITEM) {
            if(PotionUtils.getPotionFromItem(stack1) == (PotionUtils.getPotionFromItem(stack2))) return true;
            else return false;
        }
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public boolean isItemInputValid(ItemStack item) {
        for(Map.Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.fermentingList.columnMap().entrySet()) {
                for(Map.Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
                    if(this.compareItemStacks(item, (ItemStack)ent.getKey())) {
                        return true;
                    }
                }
            }
        return false;
    }

    public boolean isItemBottleValid(ItemStack item) {
        for(Map.Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.fermentingList.columnMap().entrySet()) {
            if(this.compareItemStacks(item, (ItemStack)entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    public boolean isItemYeastValid(ItemStack item) {
        return item.getItem() == ModItems.YEAST;
    }

}
