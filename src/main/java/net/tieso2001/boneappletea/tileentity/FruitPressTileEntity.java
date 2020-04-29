package net.tieso2001.boneappletea.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.tieso2001.boneappletea.network.ModPackets;
import net.tieso2001.boneappletea.network.packet.setFluidInTankPacket;
import net.tieso2001.boneappletea.network.packet.setStackInSlotPacket;
import net.tieso2001.boneappletea.recipe.FruitPressingRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FruitPressTileEntity extends TileEntity implements ITickableTileEntity {

    private boolean poweredPrevious = true;

    public ItemStackHandler slot;
    private final LazyOptional<IItemHandler> itemHolder = LazyOptional.of(() -> slot);

    private ItemStackHandler createItemStackHandler() {
        return new ItemStackHandler() {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return FruitPressingRecipe.serializer.ingredientList.contains(stack.getItem());
            }

            @Override
            public int getSlotLimit(int slot) {
                return 3;
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                markDirty();
            }
        };
    }

    public FluidTank tank;
    private final LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> tank);

    private RecipeWrapper recipeWrapper;

    public FruitPressTileEntity() {
        super(ModTileEntityTypes.FRUIT_PRESS.get());
        slot = createItemStackHandler();
        tank = new FluidTank(1000);
        recipeWrapper = new RecipeWrapper(slot);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
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

       if (world.isRemote) return;

       FruitPressingRecipe recipe = world.getRecipeManager().getRecipe(FruitPressingRecipe.recipeType, recipeWrapper, world).orElse(null);
       boolean powered = this.getBlockState().get(BlockStateProperties.POWERED);

       if (canProcess(recipe) && !poweredPrevious && powered) {
           slot.extractItem(0, recipe.getIngredientCount(), false);
           FluidStack fluidstack = new FluidStack(recipe.getResult().getFluid(), tank.getFluidAmount() + recipe.getResult().getAmount());
           tank.setFluid(fluidstack);
           world.playSound(null, pos, SoundEvents.BLOCK_HONEY_BLOCK_HIT, SoundCategory.BLOCKS, 2.0F, world.rand.nextFloat() * 0.25F + 0.6F);
           markDirty();
       }

       poweredPrevious = this.getBlockState().get(BlockStateProperties.POWERED);
       if (world.isBlockLoaded(pos)) synchronizeClient();
    }

    private boolean canProcess(FruitPressingRecipe recipe) {
        if (recipe == null) return false;

        FluidStack recipeFluid = recipe.getResult().copy();
        boolean empty = tank.isEmpty() && tank.getCapacity() >= recipeFluid.getAmount();
        boolean fluid = tank.getFluid().isFluidEqual(recipeFluid) && ((tank.getFluidAmount() + recipeFluid.getAmount()) <= tank.getCapacity());
        return empty || fluid;
    }

    private void synchronizeClient() {
        BlockPos pos = this.getPos();
        double r2 = 1024.0D; // radius of 2 chunks (32 blocks)
        Supplier<PacketDistributor.TargetPoint> targetPoint = PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), r2, world.getDimension().getType());

        ItemStack stack = slot.getStackInSlot(0).copy();
        ModPackets.INSTANCE.send(PacketDistributor.NEAR.with(targetPoint), new setStackInSlotPacket(stack, pos));

        FluidStack fluid = tank.getFluid();
        ModPackets.INSTANCE.send(PacketDistributor.NEAR.with(targetPoint), new setFluidInTankPacket(fluid, pos));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return itemHolder.cast();
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return fluidHolder.cast();
        return super.getCapability(cap, side);
    }
}
