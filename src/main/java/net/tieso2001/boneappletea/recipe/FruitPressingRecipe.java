package net.tieso2001.boneappletea.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class FruitPressingRecipe implements IRecipe<IInventory> {

    public static final IRecipeType<FruitPressingRecipe> recipeType = IRecipeType.register("fruit_pressing");
    public static final Serializer serializer = new Serializer();

    private final ResourceLocation recipeId;
    private Ingredient ingredient;
    private int ingredientCount;
    private FluidStack result;
    private int processTime;

    public FruitPressingRecipe(ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getIngredientCount() {
        return ingredientCount;
    }

    public FluidStack getResult() {
        return result;
    }

    public int getProcessTime() {
        return processTime;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        ItemStack stack = inv.getStackInSlot(0);
        int count = stack.getCount();
        return ingredient.test(stack) && count >= ingredientCount;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public IRecipeType<?> getType() {
        return recipeType;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FruitPressingRecipe> {

        public static ArrayList<Item> ingredientList = new ArrayList<>();

        @Override
        public FruitPressingRecipe read(ResourceLocation recipeId, JsonObject json) {

            FruitPressingRecipe recipe = new FruitPressingRecipe(recipeId);

            recipe.ingredient = Ingredient.deserialize(json.get("ingredient"));
            recipe.ingredientCount = JSONUtils.getInt(json.get("ingredient").getAsJsonObject(), "count", 1);

            for (ItemStack stack : recipe.ingredient.getMatchingStacks()) {
                if (!ingredientList.contains(stack.getItem())) ingredientList.add(stack.getItem());
            }

            ResourceLocation fluidResourceLocation = ResourceLocation.create(JSONUtils.getString(json.get("result").getAsJsonObject(), "fluid", "minecraft:empty"), ':');
            int fluidAmount = JSONUtils.getInt(json.get("result").getAsJsonObject(), "amount", 0);
            recipe.result = new FluidStack(ForgeRegistries.FLUIDS.getValue(fluidResourceLocation), fluidAmount);

            recipe.processTime = JSONUtils.getInt(json, "processTime", 200);;

            return recipe;
        }

        @Nullable
        @Override
        public FruitPressingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            FruitPressingRecipe recipe = new FruitPressingRecipe(recipeId);
            recipe.ingredient = Ingredient.read(buffer);
            recipe.ingredientCount = buffer.readByte();
            recipe.result = buffer.readFluidStack();
            recipe.processTime = buffer.readInt();
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, FruitPressingRecipe recipe) {
            recipe.ingredient.write(buffer);
            buffer.writeByte(recipe.getIngredientCount());
            buffer.writeFluidStack(recipe.getResult());
            buffer.writeInt(recipe.getProcessTime());
        }
    }
}
