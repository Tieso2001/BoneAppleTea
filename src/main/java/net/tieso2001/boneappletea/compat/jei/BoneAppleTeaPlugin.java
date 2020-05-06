package net.tieso2001.boneappletea.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.client.gui.screen.inventory.FruitPressScreen;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.tieso2001.boneappletea.inventory.container.FruitPressContainer;
import net.tieso2001.boneappletea.recipe.FruitPressingRecipe;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class BoneAppleTeaPlugin implements IModPlugin {

    public static final ResourceLocation PLUGIN_UID = new ResourceLocation(BoneAppleTea.MOD_ID, "plugin/main");
    public static final ResourceLocation FRUIT_PRESSING_UID = new ResourceLocation(BoneAppleTea.MOD_ID, "plugin/fruit_pressing");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FruitPressingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(getRecipesOfType(FruitPressingRecipe.recipeType), FRUIT_PRESSING_UID);
    }

    private static List<IRecipe<?>> getRecipesOfType(IRecipeType<?> recipeType) {
        return Minecraft.getInstance().world.getRecipeManager().getRecipes().stream()
                .filter(recipe -> recipe.getType() == recipeType)
                .collect(Collectors.toList());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FruitPressScreen.class, 70, 33, 36, 20, FRUIT_PRESSING_UID);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(FruitPressContainer.class, FRUIT_PRESSING_UID, 0, 1, 2, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.OAK_FRUIT_PRESS.get()), FRUIT_PRESSING_UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SPRUCE_FRUIT_PRESS.get()), FRUIT_PRESSING_UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.BIRCH_FRUIT_PRESS.get()), FRUIT_PRESSING_UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.JUNGLE_FRUIT_PRESS.get()), FRUIT_PRESSING_UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ACACIA_FRUIT_PRESS.get()), FRUIT_PRESSING_UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DARK_OAK_FRUIT_PRESS.get()), FRUIT_PRESSING_UID);
    }
}
