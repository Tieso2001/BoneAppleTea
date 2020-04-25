package net.tieso2001.boneappletea.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.tieso2001.boneappletea.init.ModRecipeSerializers;

public class FruitPressRecipe implements IRecipe<RecipeWrapper> {

    public static final IRecipeType<FruitPressRecipe> fruit_press = IRecipeType.register("fruit_press");

    private final IRecipeType<?> type;
    private final ResourceLocation id;
    final Ingredient ingredient;
    final FluidStack fluid;

    public FruitPressRecipe(ResourceLocation resourceLocation, Ingredient ingredient, FluidStack fluid) {
        type = fruit_press;
        id = resourceLocation;
        this.ingredient = ingredient;
        this.fluid = fluid;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
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
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.FRUIT_PRESS.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return type;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.FRUIT_PRESS.get());
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public FluidStack getFluid() {
        return fluid;
    }
}
