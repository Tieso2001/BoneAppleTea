package com.tieso2001.boneappletea.tile;

import com.tieso2001.boneappletea.recipe.RecipeStockPot;
import com.tieso2001.boneappletea.recipe.RecipeStockPotRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileStockPot extends TileEntity implements ITickable
{
    public int SLOTS = 2;
    public int boilTime = 0;

    private ItemStackHandler itemStackHandler = new ItemStackHandler(SLOTS)
    {
        @Override
        protected void onContentsChanged(int slot) { markDirty(); }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            for (RecipeStockPot recipe : RecipeStockPotRegistry.getRecipeMap().values())
            {
                if (stack.getItem() == recipe.getInputItemFirst().getItem() || stack.getItem() == recipe.getInputItemSecond().getItem() || stack.isEmpty()) return true;
            }
            return false;
        }
    };

    private FluidTank fluidTank = new FluidTank(1000)
    {
        @Override
        protected void onContentsChanged() { markDirty(); }
    };

    public FluidTank getFluidTank()
    {
        return fluidTank;
    }

    @Override
    public void update()
    {
        FluidStack fluidStack = fluidTank.getFluid();
        ItemStack itemFirst = itemStackHandler.getStackInSlot(0);
        ItemStack itemSecond = itemStackHandler.getStackInSlot(1);

        if (world.getBlockState(pos.down()).getBlock() != Blocks.FIRE)
        {
            boilTime = 0;
            return;
        }

        if (fluidStack == null || (itemFirst.isEmpty() && itemSecond.isEmpty()))
        {
            boilTime = 0;
            return;
        }

        RecipeStockPot recipe = RecipeStockPotRegistry.getRecipe(fluidStack, itemFirst, itemSecond);

        if (recipe == null)
        {
            boilTime = 0;
            return;
        }

        if (fluidStack.amount < recipe.getInputFluid().amount)
        {
            boilTime = 0;
            return;
        }

        if (boilTime == 0) boilTime = recipe.getBoilTime();

        if (boilTime == 1)
        {
            boilTime--;
            fluidTank.setFluid(new FluidStack(recipe.getOutputFluid().copy(), fluidStack.amount));
            if (itemFirst != ItemStack.EMPTY) itemStackHandler.getStackInSlot(0).shrink(1);
            if (itemSecond != ItemStack.EMPTY) itemStackHandler.getStackInSlot(1).shrink(1);
            markDirty();
        }

        if (boilTime > 1) boilTime--;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        if (compound.hasKey("fluids")) fluidTank.readFromNBT(compound.getCompoundTag("fluids"));
        boilTime = compound.getInteger("boilTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        NBTTagCompound fluidTankNBT = new NBTTagCompound();
        fluidTank.writeToNBT(fluidTankNBT);
        compound.setTag("fluids", fluidTankNBT);
        compound.setInteger("boilTime", boilTime);
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank);
        return super.getCapability(capability, facing);
    }
}
