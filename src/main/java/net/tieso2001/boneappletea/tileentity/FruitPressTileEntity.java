package net.tieso2001.boneappletea.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.tieso2001.boneappletea.inventory.container.FruitPressContainer;
import net.tieso2001.boneappletea.recipe.FruitPressingRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FruitPressTileEntity extends AbstractFluidMachineTileEntity<FruitPressingRecipe> {

    public static final int INPUT_SLOT = 0;
    public static final int FLUID_SLOT = 1;
    public static final int OUTPUT_TANK = 0;

    private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, FLUID_SLOT, FLUID_SLOT + 1));

    public FruitPressTileEntity() {
        super(ModTileEntityTypes.FRUIT_PRESS.get(), "Fruit Press", 2, 1, 1000);
    }

    private boolean isInputSlotItem(ItemStack stack) {
        return FruitPressingRecipe.serializer.ingredientList.contains(stack.getItem());
    }

    private boolean isFluidSlotItem(ItemStack stack) {
        ResourceLocation tag = new ResourceLocation(BoneAppleTea.MOD_ID, "fruit_press_fluid_slot_items");
        return ItemTags.getCollection().getOrCreate(tag).contains(stack.getItem());
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        switch (slot) {
            case INPUT_SLOT:
                return isInputSlotItem(stack);
            case FLUID_SLOT:
                return isFluidSlotItem(stack);
            default:
                return false;
        }
    }

    @Override
    public int getLimitForSlot(int slot) {
        if (slot == FLUID_SLOT) {
            return 1;
        }
        return super.getLimitForSlot(slot);
    }

    @Override
    protected int getInputTanks() {
        return 0;
    }

    @Override
    protected int getOutputTanks() {
        return 1;
    }

    @Override
    public FruitPressingRecipe getRecipe() {
        Inventory recipeInventory = new Inventory(this.inventory.getStackInSlot(INPUT_SLOT));
        return world == null ? null : world.getRecipeManager().getRecipe(FruitPressingRecipe.recipeType, recipeInventory, world).orElse(null);
    }

    @Override
    public short getMaxProcessTime() {
        return (short) getRecipe().getProcessTime();
    }

    @Override
    public boolean canProcess(FruitPressingRecipe recipe) {
        if (recipe == null) {
            return false;
        }
        return getTank(OUTPUT_TANK).fill(recipe.getResult().copy(), FluidAction.SIMULATE) > 0;
    }

    @Override
    public void finishProcess(FruitPressingRecipe recipe) {
        inventory.extractItem(INPUT_SLOT, recipe.getIngredientCount(), false);
        getTank(OUTPUT_TANK).fill(recipe.getResult().copy(), FluidAction.EXECUTE);
        world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_HIT, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.25F + 0.6F);
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) {
            return;
        }
        FluidActionResult testTransfer = FluidUtil.tryFillContainer(inventory.getStackInSlot(FLUID_SLOT), getTank(OUTPUT_TANK), Integer.MAX_VALUE, null, false);
        if (testTransfer.isSuccess()) {
            FluidActionResult realTransfer = FluidUtil.tryFillContainer(inventory.getStackInSlot(FLUID_SLOT), getTank(OUTPUT_TANK), Integer.MAX_VALUE, null, true);
            inventory.setStackInSlot(FLUID_SLOT, realTransfer.getResult().copy());
        }
        super.tick();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                if (!isFluidSlotItem(this.inventory.getStackInSlot(FLUID_SLOT))) {
                    return inventoryCapabilityDown.cast();
                }
                return super.getCapability(cap, side);
            }
            return this.inventoryCapability.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return tankCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FruitPressContainer(id, world, pos, playerInventory, playerEntity);
    }
}
