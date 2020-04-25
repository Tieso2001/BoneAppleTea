package net.tieso2001.boneappletea.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.tieso2001.boneappletea.recipe.FruitPressRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FruitPressTileEntity extends TileEntity implements ITickableTileEntity {

    public ItemStackHandler slot;
    private final LazyOptional<IItemHandler> itemHolder = LazyOptional.of(() -> slot);

    private ItemStackHandler createItemStackHandler() {
        return new ItemStackHandler() {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return super.isItemValid(slot, stack);
            }
        };
    }

    public FluidTank tank;
    private final LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> tank);

    private RecipeWrapper recipeWrapper;

    public FruitPressTileEntity() {
        super(ModTileEntityTypes.FRUIT_PRESS.get());
        slot = createItemStackHandler();
        tank = new FluidTank(8000);
        recipeWrapper = new RecipeWrapper(slot);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("slot", slot.serializeNBT());
        tank.writeToNBT(compound);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        slot.deserializeNBT(compound.getCompound("slot"));
        tank.readFromNBT(compound);
    }

    @Override
    public void tick() {
        FruitPressRecipe recipe = world.getRecipeManager().getRecipe(FruitPressRecipe.fruit_press, recipeWrapper, world).orElse(null);

        if (canProcess(recipe)) {
            slot.extractItem(0, 1, false);
            FluidStack fluidstack = new FluidStack(recipe.getFluid().getFluid(), tank.getFluidAmount() + recipe.getFluid().getAmount());
            tank.setFluid(fluidstack);
            markDirty();
        }
    }

    private boolean canProcess(FruitPressRecipe recipe) {
        if (recipe == null) return false;

        FluidStack recipeFluid = recipe.getFluid().copy();
        boolean empty = tank.isEmpty() && tank.getCapacity() >= recipeFluid.getAmount();
        boolean fluid = tank.getFluid().isFluidEqual(recipeFluid) && ((tank.getFluidAmount() + recipeFluid.getAmount()) <= tank.getCapacity());
        return empty || fluid;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return itemHolder.cast();
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return fluidHolder.cast();
        return super.getCapability(cap, side);
    }
}
