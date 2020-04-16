package mod.tieso2001.boneappletea.inventory.container;

import mod.tieso2001.boneappletea.init.ModContainerTypes;
import mod.tieso2001.boneappletea.tileentity.CaskTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CaskContainer extends Container {

    private Block block;
    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    public CaskContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ModContainerTypes.CASK, windowId);
        block = world.getBlockState(pos).getBlock();
        tileEntity = world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);


        addSlot(new CaskInputSlot(((CaskTileEntity) tileEntity).inputSlot, 0, 50, 35));
        addSlot(new OutputSlot(((CaskTileEntity) tileEntity).outputSlot, 0, 112, 35));
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, block);
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

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {

        Slot slot = this.inventorySlots.get(index);
        int slots = this.inventorySlots.size();
        int inputSlots = ((CaskTileEntity) tileEntity).inputSlot.getSlots();
        int outputSlots = ((CaskTileEntity) tileEntity).outputSlot.getSlots();
        int containerSlots = inputSlots + outputSlots;

        if (slot.getHasStack()) {
            if (index < containerSlots) { // Container -> Inventory
                if (!this.mergeItemStack(slot.getStack(), containerSlots, slots, true)) return ItemStack.EMPTY;
                slot.onSlotChanged();
            }
            else { // Inventory -> Container
                if (!((CaskTileEntity) tileEntity).inputSlot.isItemValid(0, slot.getStack().copy())) return ItemStack.EMPTY;
                if (!this.mergeItemStack(slot.getStack(), 0, inputSlots, false)) return ItemStack.EMPTY;
                slot.onSlotChanged();
            }
        }
        return slot.getStack().copy();
    }
}
