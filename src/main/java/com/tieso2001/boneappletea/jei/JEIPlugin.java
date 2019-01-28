package com.tieso2001.boneappletea.jei;

import com.tieso2001.boneappletea.gui.ContainerFermenter;
import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.tileentity.TileFermenter;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collections;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static final String FERMENTER_ID = "BoneAppleTea.Fermenter";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registerFermenterHandling(registry);
    }

    private void registerFermenterHandling(@Nonnull IModRegistry registry) {
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.FERMENTER), FERMENTER_ID);
        registry.addRecipes(Collections.singletonList(new FermenterRecipe()), FERMENTER_ID);
        registry.handleRecipes(FermenterRecipe.class, recipe -> new FermenterRecipeWrapper(), FERMENTER_ID);

        transferRegistry.addRecipeTransferHandler(ContainerFermenter.class, FERMENTER_ID,
                0, TileFermenter.SLOTS, TileFermenter.SLOTS, 36);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = helpers.getGuiHelper();

        registry.addRecipeCategories(new FermenterRecipeCategory(guiHelper));
    }

}