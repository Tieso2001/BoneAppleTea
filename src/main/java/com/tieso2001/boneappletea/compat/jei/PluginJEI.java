package com.tieso2001.boneappletea.compat.jei;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.compat.jei.boiling.JEIRecipeCategoryBoiling;
import com.tieso2001.boneappletea.compat.jei.boiling.JEIRecipeBoiling;
import com.tieso2001.boneappletea.compat.jei.boiling.JEIRecipeWrapperBoiling;
import com.tieso2001.boneappletea.compat.jei.fermenting.JEIRecipeCategoryFermenting;
import com.tieso2001.boneappletea.compat.jei.fermenting.JEIRecipeFermenting;
import com.tieso2001.boneappletea.compat.jei.fermenting.JEIRecipeWrapperFermenting;
import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.recipe.RecipeBoiling;
import com.tieso2001.boneappletea.recipe.RecipeBoilingRegistry;
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
    public static final String BOILING_ID = BoneAppleTea.MODID + ".jei.boiling";
    public static final String FERMENTING_ID = BoneAppleTea.MODID + ".jei.fermenting";

    @Override
    public void register(IModRegistry registry)
    {
        registerStockPotHandler(registry);
        registerFermentingHandler(registry);
    }

    private void registerStockPotHandler(@Nonnull IModRegistry registry)
    {
        registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.STOCK_POT)), BOILING_ID);
        List<JEIRecipeBoiling> recipes = new ArrayList<>();
        for (RecipeBoiling recipe : RecipeBoilingRegistry.getRecipeMap().values())
        {
            recipes.add(new JEIRecipeBoiling(recipe));
        }
        registry.addRecipes(recipes, BOILING_ID);
        registry.handleRecipes(JEIRecipeBoiling.class, JEIRecipeWrapperBoiling::new, BOILING_ID);
    }

    private void registerFermentingHandler(@Nonnull IModRegistry registry)
    {
        registry.addRecipeCatalyst(new ItemStack(Item.getItemFromBlock(ModBlocks.WOODEN_FERMENTING_BARREL)), FERMENTING_ID);
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
        registry.addRecipeCategories(new JEIRecipeCategoryBoiling(guiHelper));
        registry.addRecipeCategories(new JEIRecipeCategoryFermenting(guiHelper));
    }
}
