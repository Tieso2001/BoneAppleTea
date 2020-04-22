package net.tieso2001.boneappletea.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FruitPressRecipeSerializer<T extends FruitPressRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final FruitPressRecipeSerializer.IFactory<T> factory;

    public FruitPressRecipeSerializer(FruitPressRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    @Nonnull
    public T read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        // ingredient
        JsonElement ingredientJSON = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(ingredientJSON);

        // fluidAmount
        int fluidAmount = JSONUtils.getInt(json, "fluidAmount", 1000);

        // fluid
        ResourceLocation fluidResourceLocation = ResourceLocation.create(JSONUtils.getString(json, "fluid", "minecraft:empty"), ':');
        FluidStack fluidstack = new FluidStack(ForgeRegistries.FLUIDS.getValue(fluidResourceLocation), fluidAmount);

        return this.factory.create(recipeId, ingredient, fluidstack);
    }

    @Nullable
    @Override
    public T read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
        // ingredient
        Ingredient ingredient = Ingredient.read(buffer);

        @Nullable
        // fluid
        ResourceLocation fluidResourceLocation = ResourceLocation.create(buffer.readString(32767), ':');
        FluidStack fluidstack = new FluidStack(ForgeRegistries.FLUIDS.getValue(fluidResourceLocation), buffer.readVarInt());

        return this.factory.create(recipeId, ingredient, fluidstack);
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        // ingredient
        recipe.ingredient.write(buffer);

        // fluidAmount
        buffer.writeInt(recipe.getFluid().getAmount());

        // fluid
        buffer.writeString(recipe.getFluid().getFluid().getRegistryName().toString());
    }

    public interface IFactory<T extends FruitPressRecipe> {
        T create(ResourceLocation resourceLocation, Ingredient ingredient, FluidStack fluidstack);
    }
}
