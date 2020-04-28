package net.tieso2001.boneappletea.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;

import javax.annotation.Nullable;

public class FruitPressBlock extends Block {

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public FruitPressBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(POWERED, Boolean.FALSE));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) return null;
        return ModTileEntityTypes.FRUIT_PRESS.get().create();
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {

        DoubleBlockHalf half = state.get(HALF);
        BlockPos blockpos = half == DoubleBlockHalf.LOWER ? pos.up() : pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);

        if (blockstate.getBlock() == this && blockstate.get(HALF) != half) {

            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND && state.get(HALF) == DoubleBlockHalf.LOWER && !player.isCrouching()) {

            FruitPressTileEntity tileEntity = (FruitPressTileEntity) worldIn.getTileEntity(pos);
            ItemStack heldStack = player.getHeldItem(handIn).copy();
            ItemStack stack = ItemHandlerHelper.copyStackWithSize(heldStack, 1);

            // fluid
            IFluidHandler fluidHandler = FluidUtil.getFluidHandler(worldIn, pos, hit.getFace()).map(handler -> handler).orElse(null);
            IItemHandler playerInventory = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(inventory -> inventory).orElse(null);
            FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(player.getHeldItem(handIn), fluidHandler, playerInventory, Integer.MAX_VALUE, player, true);

            if (fluidActionResult.isSuccess()) {
                player.setHeldItem(handIn, fluidActionResult.getResult().copy());
                worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResultType.CONSUME;
            }

            // insert
            ItemStack insertion = tileEntity.slot.insertItem(0, stack.copy(), true).copy();
            if (!insertion.isItemEqual(stack)) {

                if (!heldStack.isEmpty()) {
                    tileEntity.slot.insertItem(0, stack.copy(), false);
                    if (!player.isCreative()) player.setHeldItem(handIn, ItemHandlerHelper.copyStackWithSize(heldStack, heldStack.getCount() - 1));
                    worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
                    return ActionResultType.CONSUME;
                }
            }

            // extract
            ItemStack extraction = tileEntity.slot.extractItem(0, 1, true).copy();
            if (!extraction.isEmpty()) {

                if (heldStack.isEmpty()) {

                    tileEntity.slot.extractItem(0, 1, false);
                    ItemHandlerHelper.giveItemToPlayer(player, extraction.copy());
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {

        boolean powered = worldIn.isBlockPowered(pos);

        if (powered != state.get(POWERED) && state.get(HALF) == DoubleBlockHalf.UPPER) {

            if (powered) {
                worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
            }
            else {
                worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.15F + 0.6F);
            }

            worldIn.setBlockState(pos, state.with(POWERED, powered), 3);
            worldIn.setBlockState(pos.down(), worldIn.getBlockState(pos.down()).with(POWERED, powered), 3);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return super.isValidPosition(state, worldIn, pos);
        } else {
            return blockstate.getBlock() == this;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF).add(POWERED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        BlockPos pos = context.getPos();

        if (pos.getY() < 255 && context.getWorld().getBlockState(pos.up()).isReplaceable(context)) {
            World world = context.getWorld();
            boolean powered = world.isBlockPowered(pos.up());
            return this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(POWERED, powered);
        }
        return null;
    }
}
