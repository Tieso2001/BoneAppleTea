package net.tieso2001.boneappletea.init;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.recipe.BrewingRecipe;
import net.tieso2001.boneappletea.recipe.FruitPressingRecipe;

public final class ModRecipeSerializers {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, BoneAppleTea.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<?>> BREWING = RECIPE_SERIALIZERS.register("brewing", () ->
            BrewingRecipe.serializer);

    public static final RegistryObject<IRecipeSerializer<?>> FRUIT_PRESSING = RECIPE_SERIALIZERS.register("fruit_pressing", () ->
            FruitPressingRecipe.serializer);
}
