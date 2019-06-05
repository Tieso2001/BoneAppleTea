package com.tieso2001.boneappletea.compat.jei;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.compat.jei.stock_pot.JEIRecipeCategoryStockPot;
import com.tieso2001.boneappletea.compat.jei.stock_pot.JEIRecipeStockPot;
import com.tieso2001.boneappletea.compat.jei.stock_pot.JEIRecipeWrapperStockPot;
import com.tieso2001.boneappletea.compat.jei.fermenting.JEIRecipeCategoryFermenting;
import com.tieso2001.boneappletea.compat.jei.fermenting.JEIRecipeFermenting;
import com.tieso2001.boneappletea.compat.jei.fermenting.JEIRecipeWrapperFermenting;
import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.recipe.RecipeStockPot;
import com.tieso2001.boneappletea.recipe.RecipeStockPotRegistry;
import com.tieso2001.boneappletea.recipe.RecipeFermenting;
import com.tieso2001.boneappletea.recipe.RecipeFermentingRegistry;
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
    public static final String STOCK_POT_ID = BoneAppleTea.MODID + ".jei.stock_pot";
    public static final String FERMENTING_ID = BoneAppleTea.MODID + ".jei.fermenting";

    @Override
    public void register(IModRegistry registry)
    {
        registerStockPotHandler(registry);
        registerFermentingHandler(registry);
    }

    private void registerStockPotHandler(@Nonnull IModRegistry registry)
    {
        registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.STOCK_POT)), STOCK_POT_ID);
        List<JEIRecipeStockPot> recipes = new ArrayList<>();
        for (RecipeStockPot recipe : RecipeStockPotRegistry.getRecipeMap().values())
        {
            recipes.add(new JEIRecipeStockPot(recipe));
        }
        registry.addRecipes(recipes, STOCK_POT_ID);
        registry.handleRecipes(JEIRecipeStockPot.class, JEIRecipeWrapperStockPot::new, STOCK_POT_ID);
    }

    private void registerFermentingHandler(@Nonnull IModRegistry registry)
    {
        registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.WOODEN_BARREL)), FERMENTING_ID);
        List<JEIRecipeFermenting> recipes = new ArrayList<>();
        for (RecipeFermenting recipe : RecipeFermentingRegistry.getRecipeMap().values())
        {
            recipes.add(new JEIRecipeFermenting(recipe));
        }
        registry.addRecipes(recipes, FERMENTING_ID);
        registry.handleRecipes(JEIRecipeFermenting.class, JEIRecipeWrapperFermenting::new, FERMENTING_ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new JEIRecipeCategoryStockPot(guiHelper));
        registry.addRecipeCategories(new JEIRecipeCategoryFermenting(guiHelper));
    }
}
