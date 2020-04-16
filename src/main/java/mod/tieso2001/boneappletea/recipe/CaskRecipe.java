package mod.tieso2001.boneappletea.recipe;

import mod.tieso2001.boneappletea.init.ModBlocks;
import mod.tieso2001.boneappletea.init.ModRecipeSerializers;
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

public class CaskRecipe implements IRecipe<RecipeWrapper> {

    public static final IRecipeType<CaskRecipe> cask = IRecipeType.register("cask");

    private final IRecipeType<?> type;
    private final ResourceLocation id;
    final String group;
    final Ingredient ingredient;
    final FluidStack ingredientFluid;
    final ItemStack result;
    final FluidStack resultFluid;
    final int caskTime;

    public CaskRecipe(ResourceLocation resourceLocation, String group, Ingredient ingredient, FluidStack ingredientFluid, ItemStack result, FluidStack resultFluid, int caskTime) {
        type = cask;
        id = resourceLocation;
        this.group = group;
        this.ingredient = ingredient;
        this.ingredientFluid = ingredientFluid;
        this.result = result;
        this.resultFluid = resultFluid;
        this.caskTime = caskTime;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CASK;
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
        return new ItemStack(ModBlocks.OAK_CASK);
    }

    public FluidStack getIngredientFluid() {
        return ingredientFluid;
    }

    public FluidStack getResultFluid() {
        return resultFluid;
    }

    public int getCaskTime() {
        return caskTime;
    }
}
