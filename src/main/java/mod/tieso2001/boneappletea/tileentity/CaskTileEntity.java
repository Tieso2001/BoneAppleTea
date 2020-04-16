package mod.tieso2001.boneappletea.tileentity;

import mod.tieso2001.boneappletea.init.ModTileEntityTypes;
import mod.tieso2001.boneappletea.inventory.container.CaskContainer;
import mod.tieso2001.boneappletea.inventory.container.OutputFluidTank;
import mod.tieso2001.boneappletea.recipe.CaskRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CaskTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public ItemStackHandler inputSlot;
    public ItemStackHandler outputSlot;
    private final LazyOptional<IItemHandler> inputSlotHolder = LazyOptional.of(() -> inputSlot);
    private final LazyOptional<IItemHandler> outputSlotHolder = LazyOptional.of(() -> outputSlot);
    private final LazyOptional<IItemHandler> combinedHolder = LazyOptional.of(() -> new CombinedInvWrapper(inputSlot, outputSlot));

    public FluidTank inputTank;
    public FluidTank outputTank;
    private final LazyOptional<IFluidHandler> inputFluidHolder = LazyOptional.of(() -> inputTank);
    private final LazyOptional<IFluidHandler> outputFluidHolder = LazyOptional.of(() -> outputTank);

    private RecipeWrapper recipeWrapper;

    public CaskTileEntity() {
        super(ModTileEntityTypes.CASK);
        inputSlot = new ItemStackHandler();
        outputSlot = new ItemStackHandler();
        inputTank = new FluidTank(1000);
        outputTank = new OutputFluidTank(1000);
        recipeWrapper = new RecipeWrapper(inputSlot);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inputSlot", inputSlot.serializeNBT());
        compound.put("outputSlot", outputSlot.serializeNBT());
        inputTank.writeToNBT(compound);
        outputTank.writeToNBT(compound);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        inputSlot.deserializeNBT(compound.getCompound("inputSlot"));
        outputSlot.deserializeNBT(compound.getCompound("outputSlot"));
        inputTank.readFromNBT(compound);
        outputTank.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) return outputSlotHolder.cast();
            return inputSlotHolder.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) return outputFluidHolder.cast();
            return inputFluidHolder.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void tick() {
        CaskRecipe recipe = world.getRecipeManager().getRecipe(CaskRecipe.cask, recipeWrapper, world).orElse(null);

        if (recipe != null && canProcess(recipe)) {
            inputSlot.extractItem(0,1, false);
            inputTank.drain(recipe.getIngredientFluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            ItemStack itemstack = new ItemStack(recipe.getRecipeOutput().getItem(), outputSlot.getStackInSlot(0).getCount() + recipe.getRecipeOutput().getCount());
            outputSlot.setStackInSlot(0, itemstack);
            FluidStack fluidstack = new FluidStack(recipe.getResultFluid().getFluid(), outputTank.getFluidAmount() + recipe.getResultFluid().getAmount());
            outputTank.setFluid(fluidstack);
            markDirty();
        }
    }

    private boolean canProcess(CaskRecipe recipe) {
        boolean itemInput = !inputSlot.extractItem(0,1, true).isEmpty() || recipe.getIngredients().isEmpty();
        boolean fluidInput = inputTank.getFluid().containsFluid(recipe.getIngredientFluid());
        boolean outputItemType = outputSlot.getStackInSlot(0).isEmpty() || outputSlot.getStackInSlot(0).isItemEqual(recipe.getRecipeOutput());
        boolean outputItemAmount = outputSlot.getStackInSlot(0).getCount() + recipe.getRecipeOutput().getCount() <= outputSlot.getSlotLimit(0);
        boolean fluidOutputType = outputTank.isEmpty() || outputTank.getFluid().containsFluid(recipe.getResultFluid().copy());
        boolean fluidOutputAmount = outputTank.getFluidAmount() + recipe.getResultFluid().getAmount() <= outputTank.getCapacity();
        return itemInput && fluidInput && outputItemType && outputItemAmount && fluidOutputType && fluidOutputAmount;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CaskContainer(i, world, pos, playerInventory, playerEntity);
    }
}
