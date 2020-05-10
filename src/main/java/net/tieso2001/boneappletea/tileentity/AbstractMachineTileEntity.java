package net.tieso2001.boneappletea.tileentity;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractMachineTileEntity<Recipe extends IRecipe<?>> extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private final String displayName;
    public final ItemStackHandler inventory;
    protected final LazyOptional<ItemStackHandler> inventoryCapability;

    public short maxProcessTime = -1;
    public short processTimeLeft = -1;

    public AbstractMachineTileEntity(TileEntityType<?> tileEntityType, String displayName, int inventorySize) {
        super(tileEntityType);
        this.displayName = displayName;
        this.inventory = createItemStackHandler(inventorySize);
        this.inventoryCapability = LazyOptional.of(() -> this.inventory);
    }

    private ItemStackHandler createItemStackHandler(int size) {
        return new ItemStackHandler(size) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return isItemValidForSlot(slot, stack);
            }

            @Override
            public int getSlotLimit(int slot) {
                return getLimitForSlot(slot);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                AbstractMachineTileEntity.this.markDirty();
            }
        };
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }

    public int getLimitForSlot(int slot) {
        return 64;
    }

    public abstract Recipe getRecipe();

    public abstract short getMaxProcessTime();

    public abstract boolean canProcess(Recipe recipe);

    public abstract void finishProcess(Recipe recipe);

    @Override
    public void tick() {

        if (world == null || world.isRemote) {
            return;
        }

        Recipe recipe = getRecipe();
        if (this.canProcess(recipe)) {
            if (processTimeLeft == -1 || maxProcessTime == -1) {
                maxProcessTime = this.getMaxProcessTime();
                processTimeLeft = maxProcessTime;
            } else {
                processTimeLeft--;
                if (processTimeLeft == 0) {
                    this.finishProcess(recipe);
                    maxProcessTime = -1;
                    processTimeLeft = -1;
                }
            }
        } else {
            maxProcessTime = -1;
            processTimeLeft = -1;
        }
        this.markDirty();
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("inventory"));
        this.maxProcessTime = compound.getShort("max_process_time");
        this.processTimeLeft = compound.getShort("process_time_left");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inventory", this.inventory.serializeNBT());
        compound.putShort("max_process_time", this.maxProcessTime);
        compound.putShort("process_time_left", this.processTimeLeft);
        return super.write(compound);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(this.displayName);
    }
}
