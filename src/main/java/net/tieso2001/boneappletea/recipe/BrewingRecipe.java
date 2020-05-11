package net.tieso2001.boneappletea.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.tieso2001.boneappletea.util.InventoryUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BrewingRecipe implements IRecipe<IInventory> {

    public static final IRecipeType<BrewingRecipe> recipeType = IRecipeType.register("brewing");
    public static final BrewingRecipe.Serializer serializer = new BrewingRecipe.Serializer();

    private final ResourceLocation recipeId;
    private Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
    private FluidStack ingredientFluid;
    private FluidStack result;
    private int processTime;

    public BrewingRecipe(ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }

    public FluidStack getIngredientFluid() {
        return ingredientFluid;
    }

    public FluidStack getResult() {
        return result;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void consumeIngredients(IInventory inv) {
        ingredients.forEach(((ingredient, count) -> InventoryUtil.consumeItems(inv, ingredient, count)));
    }

    public Map<Ingredient, Integer> getIngredientMap() {
        return ImmutableMap.copyOf(ingredients);
    }


    @Override
    public boolean matches(IInventory inv, World worldIn) {

        for (Ingredient ingredient : ingredients.keySet()) {
            int required = ingredients.get(ingredient);
            int found = InventoryUtil.getTotalCount(inv, ingredient);
            if (found < required) {
                return false;
            }
        }

        for (int i = 0; i < 2; ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                boolean foundMatch = false;
                for (Ingredient ingredient : ingredients.keySet()) {
                    if (ingredient.test(stack)) {
                        foundMatch = true;
                        break;
                    }
                }
                if (!foundMatch) {
                    return false;
                }
            }
        }

        return true;
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
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredientsList = NonNullList.create();
        for (Ingredient ingredient : ingredients.keySet()) {
            if (!ingredientsList.contains(ingredient)) {
                ingredientsList.add(ingredient);
            }
        }
        return ingredientsList;
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

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BrewingRecipe> {

        public List<Item> ingredients = new ArrayList<>();

        @Override
        public BrewingRecipe read(ResourceLocation recipeId, JsonObject json) {

            BrewingRecipe recipe = new BrewingRecipe(recipeId);

            JSONUtils.getJsonArray(json, "ingredients").forEach(element -> {
                Ingredient ingredient = Ingredient.deserialize(element);
                int count = JSONUtils.getInt(element.getAsJsonObject(), "count", 1);
                recipe.ingredients.put(ingredient, count);

                for (ItemStack stack : ingredient.getMatchingStacks()) {
                    if (!ingredients.contains(stack.getItem())) {
                        ingredients.add(stack.getItem());
                    }
                }
            });

            ResourceLocation ingredientFluidResourceLocation = ResourceLocation.create(JSONUtils.getString(json.get("ingredientFluid").getAsJsonObject(), "fluid", "minecraft:empty"), ':');
            int ingredientFluidAmount = JSONUtils.getInt(json.get("ingredientFluid").getAsJsonObject(), "amount", 0);
            recipe.ingredientFluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(ingredientFluidResourceLocation), ingredientFluidAmount);

            ResourceLocation resultResourceLocation = ResourceLocation.create(JSONUtils.getString(json.get("result").getAsJsonObject(), "fluid", "minecraft:empty"), ':');
            int resultAmount = JSONUtils.getInt(json.get("result").getAsJsonObject(), "amount", 0);
            recipe.result = new FluidStack(ForgeRegistries.FLUIDS.getValue(resultResourceLocation), resultAmount);

            recipe.processTime = JSONUtils.getInt(json, "processTime", 200);;

            return recipe;
        }

        @Nullable
        @Override
        public BrewingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            BrewingRecipe recipe = new BrewingRecipe(recipeId);
            int ingredientCount = buffer.readByte();
            for (int i = 0; i < ingredientCount; ++i) {
                Ingredient ingredient = Ingredient.read(buffer);
                int count = buffer.readVarInt();
                recipe.ingredients.put(ingredient, count);
            }
            recipe.ingredientFluid = buffer.readFluidStack();
            recipe.result = buffer.readFluidStack();
            recipe.processTime = buffer.readInt();
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, BrewingRecipe recipe) {
            buffer.writeByte(recipe.ingredients.size());
            recipe.ingredients.forEach((ingredient, count) -> {
                ingredient.write(buffer);
                buffer.writeVarInt(count);
            });
            buffer.writeFluidStack(recipe.getIngredientFluid());
            buffer.writeFluidStack(recipe.getResult());
            buffer.writeInt(recipe.getProcessTime());
        }
    }
}
