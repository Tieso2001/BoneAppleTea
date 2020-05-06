package net.tieso2001.boneappletea.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.tieso2001.boneappletea.init.ModContainerTypes;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;

public class FruitPressContainer extends Container {

    public FruitPressTileEntity tileEntity;
    private IItemHandler playerInventory;

    public FruitPressContainer(final int windowId, PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory.player.world, data.readBlockPos(), playerInventory, playerInventory.player);
    }

    public FruitPressContainer(final int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ModContainerTypes.FRUIT_PRESS.get(), windowId);
        tileEntity = (FruitPressTileEntity) world.getTileEntity(pos);
        this.playerInventory = new InvWrapper(playerInventory);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            addSlot(new SlotItemHandler(handler, FruitPressTileEntity.INPUT_SLOT, 44, 35));
            addSlot(new SlotItemHandler(handler, FruitPressTileEntity.FLUID_SLOT, 139, 35));
        });
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), player, ModBlocks.OAK_FRUIT_PRESS.get()) ||
                isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), player, ModBlocks.SPRUCE_FRUIT_PRESS.get()) ||
                isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), player, ModBlocks.BIRCH_FRUIT_PRESS.get()) ||
                isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), player, ModBlocks.JUNGLE_FRUIT_PRESS.get()) ||
                isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), player, ModBlocks.ACACIA_FRUIT_PRESS.get()) ||
                isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), player, ModBlocks.DARK_OAK_FRUIT_PRESS.get());
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
