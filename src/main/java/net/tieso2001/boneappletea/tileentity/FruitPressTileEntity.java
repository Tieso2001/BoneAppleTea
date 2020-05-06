package net.tieso2001.boneappletea.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.fluid.capability.OutputFluidTank;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.tieso2001.boneappletea.inventory.container.FruitPressContainer;
import net.tieso2001.boneappletea.recipe.FruitPressingRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FruitPressTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int INPUT_SLOT = 0;
    public static final int FLUID_SLOT = 1;

    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
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
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            FruitPressTileEntity.this.markDirty();
        }
    };

    public final FluidTank tank = new OutputFluidTank(1000);

    private final LazyOptional<ItemStackHandler> inventoryCapability = LazyOptional.of(() -> this.inventory);
    private final LazyOptional<IFluidHandler> tankCapability = LazyOptional.of(() -> tank);

    public short processTimeLeft = -1;
    public short maxProcessTime = 200;

    public FruitPressTileEntity() {
        super(ModTileEntityTypes.FRUIT_PRESS.get());
    }

    private boolean isInputSlotItem(ItemStack stack) {
        return FruitPressingRecipe.serializer.ingredientList.contains(stack.getItem());
    }

    private boolean isFluidSlotItem(ItemStack stack) {
        ResourceLocation tag = new ResourceLocation(BoneAppleTea.MOD_ID, "fruit_press_fluid_slot_items");
        return ItemTags.getCollection().getOrCreate(tag).contains(stack.getItem());
    }

    private FruitPressingRecipe getRecipe(final ItemStack input) {
        return getRecipe(new Inventory(input));
    }

    private FruitPressingRecipe getRecipe(final IInventory inventory) {
        return world == null ? null : world.getRecipeManager().getRecipe(FruitPressingRecipe.recipeType, inventory, world).orElse(null);
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) {
            return;
        }

        FluidActionResult testTransfer = FluidUtil.tryFillContainer(inventory.getStackInSlot(1), tank, Integer.MAX_VALUE, null, false);
        if (testTransfer.isSuccess()) {
            FluidActionResult realTransfer = FluidUtil.tryFillContainer(inventory.getStackInSlot(1), tank, Integer.MAX_VALUE, null, true);
            inventory.setStackInSlot(1, realTransfer.getResult().copy());
        }

        FruitPressingRecipe recipe = getRecipe(inventory.getStackInSlot(0));
        if (this.canProcess(recipe)) {
            if (processTimeLeft == -1) {
                processTimeLeft = maxProcessTime;
            } else {
                processTimeLeft--;
                if (processTimeLeft == 0) {
                    inventory.extractItem(0, recipe.getIngredientCount(), false);
                    OutputFluidTank fluidTank = (OutputFluidTank) this.tank;
                    fluidTank.fillInternal(recipe.getResult().copy(), IFluidHandler.FluidAction.EXECUTE);
                    world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_HIT, SoundCategory.BLOCKS, 2.0F, world.rand.nextFloat() * 0.25F + 0.6F);
                    processTimeLeft = -1;
                }
            }
        } else {
            processTimeLeft = -1;
        }
        this.markDirty();
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
    }

    private boolean canProcess(FruitPressingRecipe recipe) {
        if (recipe == null) {
            return false;
        }
        OutputFluidTank fluidTank = (OutputFluidTank) this.tank;
        return fluidTank.fillInternal(recipe.getResult().copy(), IFluidHandler.FluidAction.SIMULATE) > 0;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("inventory"));
        this.tank.readFromNBT(compound);
        this.maxProcessTime = compound.getShort("max_process_time");
        this.processTimeLeft = compound.getShort("process_time_left");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("inventory", this.inventory.serializeNBT());
        tank.writeToNBT(compound);
        compound.putShort("max_process_time", this.maxProcessTime);
        compound.putShort("process_time_left", this.processTimeLeft);
        return compound;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    // TODO: empty bucket should not be extracted

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return inventoryCapability.cast();
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return tankCapability.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Fruit Press");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FruitPressContainer(id, world, pos, playerInventory, playerEntity);
    }
}
