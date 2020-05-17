package net.tieso2001.boneappletea.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.tieso2001.boneappletea.inventory.container.BreweryCaskContainer;
import net.tieso2001.boneappletea.recipe.BrewingRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BreweryCaskTileEntity extends AbstractFluidMachineTileEntity<BrewingRecipe> {

    public static final int INPUT_1_SLOT = 0;
    public static final int INPUT_2_SLOT = 1;
    public static final int INPUT_TANK_INPUT_SLOT = 2;
    public static final int INPUT_TANK_OUTPUT_SLOT = 3;
    public static final int OUTPUT_TANK_OUTPUT_SLOT = 4;
    public static final int INPUT_TANK = 0;
    public static final int OUTPUT_TANK = 1;

    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_TANK_OUTPUT_SLOT, OUTPUT_TANK_OUTPUT_SLOT + 1));

    public BreweryCaskTileEntity() {
        super(ModTileEntityTypes.BREWERY_CASK.get(), "Brewery Cask", 5, 2, 4000);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        switch (slot) {
            case INPUT_1_SLOT:
            case INPUT_2_SLOT:
                return BrewingRecipe.serializer.ingredients.contains(stack.getItem());
            case INPUT_TANK_INPUT_SLOT:
            case OUTPUT_TANK_OUTPUT_SLOT:
            case INPUT_TANK_OUTPUT_SLOT:
                return FluidUtil.getFluidHandler(stack).isPresent();
            default:
                return false;
        }
    }

    @Override
    public int getLimitForSlot(int slot) {
        switch (slot) {
            case INPUT_TANK_INPUT_SLOT:
            case INPUT_TANK_OUTPUT_SLOT:
            case OUTPUT_TANK_OUTPUT_SLOT:
                return 1;
            default:
                return super.getLimitForSlot(slot);
        }
    }

    @Override
    protected int getInputTanks() {
        return 1;
    }

    @Override
    protected int getOutputTanks() {
        return 1;
    }

    @Override
    public BrewingRecipe getRecipe() {
        Inventory recipeInventory = new Inventory(this.inventory.getStackInSlot(INPUT_1_SLOT), this.inventory.getStackInSlot(INPUT_2_SLOT));
        return world == null ? null : world.getRecipeManager().getRecipe(BrewingRecipe.recipeType, recipeInventory, world).orElse(null);
    }

    @Override
    public short getMaxProcessTime() {
        return (short) getRecipe().getProcessTime();
    }

    @Override
    public boolean canProcess(BrewingRecipe recipe) {
        if (recipe == null) {
            return false;
        }
        boolean inputTank = !getTank(INPUT_TANK).drain(recipe.getIngredientFluid().copy(), FluidAction.SIMULATE).isEmpty();
        boolean outputTank = getTank(OUTPUT_TANK).fill(recipe.getResult().copy(), FluidAction.SIMULATE) > 0;
        return inputTank && outputTank;
    }

    @Override
    public void finishProcess(BrewingRecipe recipe) {
        Inventory recipeInventory = new Inventory(this.inventory.getStackInSlot(INPUT_1_SLOT), this.inventory.getStackInSlot(INPUT_2_SLOT));
        recipe.consumeIngredients(recipeInventory);
        getTank(INPUT_TANK).drain(recipe.getIngredientFluid().copy(), FluidAction.EXECUTE);
        getTank(OUTPUT_TANK).fill(recipe.getResult().copy(), FluidAction.EXECUTE);
    }

    @Override
    public void tick() {

        if (world == null || world.isRemote) {
            return;
        }

        FluidActionResult fillInputTank = FluidUtil.tryEmptyContainer(inventory.getStackInSlot(INPUT_TANK_INPUT_SLOT), getTank(INPUT_TANK), Integer.MAX_VALUE, null, true);
        if (fillInputTank.isSuccess()) {
            inventory.setStackInSlot(INPUT_TANK_INPUT_SLOT, fillInputTank.getResult().copy());
        }

        FluidActionResult testEmptyInputTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(INPUT_TANK_OUTPUT_SLOT), getTank(INPUT_TANK), Integer.MAX_VALUE, null, false);
        if (testEmptyInputTank.isSuccess()) {
            FluidActionResult realEmptyInputTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(INPUT_TANK_OUTPUT_SLOT), getTank(INPUT_TANK), Integer.MAX_VALUE, null, true);
            inventory.setStackInSlot(INPUT_TANK_OUTPUT_SLOT, realEmptyInputTank.getResult().copy());
        }

        FluidActionResult testEmptyOutputTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(OUTPUT_TANK_OUTPUT_SLOT), getTank(OUTPUT_TANK), Integer.MAX_VALUE, null, false);
        if (testEmptyOutputTank.isSuccess()) {
            FluidActionResult realEmptyOutputTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(OUTPUT_TANK_OUTPUT_SLOT), getTank(OUTPUT_TANK), Integer.MAX_VALUE, null, true);
            inventory.setStackInSlot(OUTPUT_TANK_OUTPUT_SLOT, realEmptyOutputTank.getResult().copy());
        }

        super.tick();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                return inventoryCapabilityDown.cast();
            }
            return inventoryCapability.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return tankCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BreweryCaskContainer(id, world, pos, playerInventory, playerEntity);
    }
}
