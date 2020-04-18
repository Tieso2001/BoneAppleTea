package net.tieso2001.boneappletea.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CaskRecipeSerializer<T extends CaskRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final CaskRecipeSerializer.IFactory<T> factory;

    public CaskRecipeSerializer(CaskRecipeSerializer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    @Nonnull
    public T read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        // group
        String groupString = JSONUtils.getString(json, "group", "");

        // ingredient
        JsonElement ingredientJSON = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(ingredientJSON);

        // ingredientFluidAmount
        int ingredientFluidAmount = JSONUtils.getInt(json, "ingredientFluidAmount", 1000);

        // ingredientFluid
        ResourceLocation ingredientFluidRS = ResourceLocation.create(JSONUtils.getString(json, "ingredientFluid", "minecraft:empty"), ':');
        FluidStack ingredientFluidStack = new FluidStack(ForgeRegistries.FLUIDS.getValue(ingredientFluidRS), ingredientFluidAmount);

        // result
        ItemStack resultItemStack;
        if (!json.has("result")) {
            resultItemStack = ItemStack.EMPTY;
        }
        else if (json.get("result").isJsonObject()) {
            resultItemStack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        } else {
            String resultString = JSONUtils.getString(json, "result");
            ResourceLocation resultRS = new ResourceLocation(resultString);
            resultItemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(resultRS));
        }

        // resultFluidAmount
        int resultFluidAmount = JSONUtils.getInt(json, "resultFluidAmount", 1000);

        // resultFluid
        ResourceLocation resultFluidRS = ResourceLocation.create(JSONUtils.getString(json, "resultFluid", "minecraft:empty"), ':');
        FluidStack resultFluidStack = new FluidStack(ForgeRegistries.FLUIDS.getValue(resultFluidRS), resultFluidAmount);

        // caskTime
        int caskTime = JSONUtils.getInt(json, "caskTime", 200);

        return this.factory.create(recipeId, groupString, ingredient, ingredientFluidStack, resultItemStack, resultFluidStack, caskTime);
    }

    @Nullable
    @Override
    public T read(@Nonnull ResourceLocation recipeId, PacketBuffer buffer) {
        // group
        String groupString = buffer.readString(32767);

        // ingredient
        Ingredient ingredient = Ingredient.read(buffer);

        // ingredientFluid
        ResourceLocation ingredientFluidRS = ResourceLocation.create(buffer.readString(32767), ':');
        FluidStack ingredientFluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(ingredientFluidRS), buffer.readVarInt());

        // result
        ItemStack itemstack = buffer.readItemStack();

        // resultFluid
        ResourceLocation resultFluidRS = ResourceLocation.create(buffer.readString(32767), ':');
        FluidStack resultFluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(resultFluidRS), buffer.readVarInt());

        // caskTime
        int caskTime = buffer.readVarInt();

        return this.factory.create(recipeId, groupString, ingredient, ingredientFluid, itemstack, resultFluid, caskTime);
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        // group
        buffer.writeString(recipe.group);

        // ingredient
        recipe.ingredient.write(buffer);

        // ingredientFluidAmount
        buffer.writeInt(recipe.getIngredientFluid().getAmount());

        // ingredientFluid
        buffer.writeString(recipe.ingredientFluid.getFluid().getRegistryName().toString());

        // result
        buffer.writeItemStack(recipe.result);

        // resultFluidAmount
        buffer.writeInt(recipe.getResultFluid().getAmount());

        // resultFluid
        buffer.writeString(recipe.resultFluid.getFluid().getRegistryName().toString());

        // caskTime
        buffer.writeVarInt(recipe.caskTime);
    }

    public interface IFactory<T extends CaskRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient ingredient, FluidStack ingredientFluid, ItemStack result, FluidStack resultFluid, int caskTime);
    }
}
