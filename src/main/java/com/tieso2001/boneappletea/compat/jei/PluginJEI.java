package com.tieso2001.boneappletea.compat.jei;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.recipe.RecipeStockPot;
import com.tieso2001.boneappletea.recipe.RecipeStockPotRegistry;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class PluginJEI implements IModPlugin
{
    public static final String STOCK_POT_ID = BoneAppleTea.MODID + ".stock_pot";

    @Override
    public void register(IModRegistry registry)
    {
        registerStockPotHandler(registry);
    }

    private void registerStockPotHandler(@Nonnull IModRegistry registry)
    {
        registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.STOCK_POT)), STOCK_POT_ID);
        List<JEIRecipeStockPot> stockpotRecipes = new ArrayList<>();
        for (RecipeStockPot recipe : RecipeStockPotRegistry.getRecipeMap().values())
        {
            stockpotRecipes.add(new JEIRecipeStockPot(recipe));
        }
        registry.addRecipes(stockpotRecipes, STOCK_POT_ID);
        registry.handleRecipes(JEIRecipeStockPot.class, JEIRecipeWrapperStockPot::new, STOCK_POT_ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new JEIRecipeCategoryStockPot(guiHelper));
    }
}
