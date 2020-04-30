package net.tieso2001.boneappletea.init;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.recipe.CaskRecipe;
import net.tieso2001.boneappletea.recipe.CaskRecipeSerializer;
import net.tieso2001.boneappletea.recipe.FruitPressingRecipe;

public final class ModRecipeSerializers {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, BoneAppleTea.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<?>> CASK = RECIPE_SERIALIZERS.register("cask", () ->
            new CaskRecipeSerializer<>(CaskRecipe::new));

    public static final RegistryObject<IRecipeSerializer<?>> FRUIT_PRESSING = RECIPE_SERIALIZERS.register("fruit_pressing", () ->
            FruitPressingRecipe.serializer);
}
