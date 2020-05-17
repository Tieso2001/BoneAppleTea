package net.tieso2001.boneappletea.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.tieso2001.boneappletea.tileentity.BreweryCaskTileEntity;

import javax.annotation.Nullable;

public class BreweryCaskBlock extends Block {

    public BreweryCaskBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BreweryCaskTileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.BREWERY_CASK.get().create();
    }

    @Nullable
    private BreweryCaskTileEntity getTileEntity(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        return tileEntity instanceof BreweryCaskTileEntity ? (BreweryCaskTileEntity) tileEntity : null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        BreweryCaskTileEntity tileEntity = getTileEntity(worldIn, pos);

        if (tileEntity != null && !worldIn.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) player, tileEntity, pos);
        }
        return ActionResultType.CONSUME;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

        BreweryCaskTileEntity tileEntity = getTileEntity(worldIn, pos);

        if (tileEntity != null) {
            final ItemStackHandler inventory = tileEntity.inventory;
            for (int slot = 0; slot < inventory.getSlots(); ++slot) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(slot));
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
}
