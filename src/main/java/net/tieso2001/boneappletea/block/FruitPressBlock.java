package net.tieso2001.boneappletea.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
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

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    // LOWER HALF
    private static final VoxelShape NW_CORNER = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 4.0D, 3.0D);
    private static final VoxelShape NE_CORNER = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 4.0D, 3.0D);
    private static final VoxelShape SW_CORNER = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 4.0D, 16.0D);
    private static final VoxelShape SE_CORNER = Block.makeCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 4.0D, 16.0D);
    private static final VoxelShape CORNERS = VoxelShapes.or(NW_CORNER, NE_CORNER, SW_CORNER, SE_CORNER);

    private static final VoxelShape FLUID_CONTAINER_BOX = Block.makeCuboidShape(0.0D, 4.0D, 0.0D, 16.0D, 11.0D, 16.0D);
    private static final VoxelShape FLUID_CONTAINER_HOLE = Block.makeCuboidShape(2.0D, 6.0D, 2.0D, 14.0D, 11.0D, 14.0D);
    private static final VoxelShape FLUID_CONTAINER = VoxelShapes.combineAndSimplify(FLUID_CONTAINER_BOX, FLUID_CONTAINER_HOLE, IBooleanFunction.ONLY_FIRST);

    private static final VoxelShape LOWER_NS_W_FENCE = Block.makeCuboidShape(0.0D, 11.0D, 7.0D, 2.0D, 16.0D, 9.0D);
    private static final VoxelShape LOWER_NS_E_FENCE = Block.makeCuboidShape(14.0D, 11.0D, 7.0D, 16.0D, 16.0D, 9.0D);
    private static final VoxelShape LOWER_WE_W_FENCE = Block.makeCuboidShape(7.0D, 11.0D, 14.0D, 9.0D, 16.0D, 16.0D);
    private static final VoxelShape LOWER_WE_E_FENCE = Block.makeCuboidShape(7.0D, 11.0D, 0.0D, 9.0D, 16.0D, 2.0D);

    private static final VoxelShape PISTON_HEAD_POWERED = Block.makeCuboidShape(3.0D, 10.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    private static final VoxelShape LOWER_PISTON_EXTENSION = Block.makeCuboidShape(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D);

    // UPPER HALF
    private static final VoxelShape UPPER_NS_W_POLE = Block.makeCuboidShape(0.0D, 0.0D, 7.0D, 2.0D, 13.0D, 9.0D);
    private static final VoxelShape BOTTOM_NS_W_CONNECTOR = Block.makeCuboidShape(2.0D, 7.0D, 7.0D, 3.0D, 8.0D, 9.0D);
    private static final VoxelShape TOP_NS_W_CONNECTOR = Block.makeCuboidShape(2.0D, 9.0D, 7.0D, 3.0D, 10.0D, 9.0D);
    private static final VoxelShape UPPER_NS_W_FENCE = VoxelShapes.or(UPPER_NS_W_POLE, BOTTOM_NS_W_CONNECTOR, TOP_NS_W_CONNECTOR);

    private static final VoxelShape UPPER_WE_W_POLE = Block.makeCuboidShape(7.0D, 0.0D, 14.0D, 9.0D, 13.0D, 16.0D);
    private static final VoxelShape BOTTOM_WE_W_CONNECTOR = Block.makeCuboidShape(7.0D, 7.0D, 13.0D, 9.0D, 8.0D, 14.0D);
    private static final VoxelShape TOP_WE_W_CONNECTOR = Block.makeCuboidShape(7.0D, 9.0D, 13.0D, 9.0D, 10.0D, 14.0D);
    private static final VoxelShape UPPER_WE_W_FENCE = VoxelShapes.or(UPPER_WE_W_POLE, BOTTOM_WE_W_CONNECTOR, TOP_WE_W_CONNECTOR);


    private static final VoxelShape UPPER_NS_E_POLE = Block.makeCuboidShape(14.0D, 0.0D, 7.0D, 16.0D, 13.0D, 9.0D);
    private static final VoxelShape BOTTOM_NS_E_CONNECTOR = Block.makeCuboidShape(13.0D, 7.0D, 7.0D, 14.0D, 8.0D, 9.0D);
    private static final VoxelShape TOP_NS_E_CONNECTOR = Block.makeCuboidShape(13.0D, 9.0D, 7.0D, 14.0D, 10.0D, 9.0D);
    private static final VoxelShape UPPER_NS_E_FENCE = VoxelShapes.or(UPPER_NS_E_POLE, BOTTOM_NS_E_CONNECTOR, TOP_NS_E_CONNECTOR);

    private static final VoxelShape UPPER_WE_E_POLE = Block.makeCuboidShape(7.0D, 0.0D, 0.0D, 9.0D, 13.0D, 2.0D);
    private static final VoxelShape BOTTOM_WE_E_CONNECTOR = Block.makeCuboidShape(7.0D, 7.0D, 2.0D, 9.0D, 8.0D, 3.0D);
    private static final VoxelShape TOP_WE_E_CONNECTOR = Block.makeCuboidShape(7.0D, 9.0D, 2.0D, 9.0D, 10.0D, 3.0D);
    private static final VoxelShape UPPER_WE_E_FENCE = VoxelShapes.or(UPPER_WE_E_POLE, BOTTOM_WE_E_CONNECTOR, TOP_WE_E_CONNECTOR);


    private static final VoxelShape PISTON_BASE = Block.makeCuboidShape(3.0D, 4.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    private static final VoxelShape UPPER_PISTON_HEAD = Block.makeCuboidShape(3.0D, 2.0D, 3.0D, 13.0D, 4.0D, 13.0D);
    private static final VoxelShape UPPER_PISTON_EXTENSION = Block.makeCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 4.0D, 9.0D);

    // FULL SHAPE
    private static final VoxelShape LOWER_SHAPE = VoxelShapes.or(CORNERS, FLUID_CONTAINER);
    private static final VoxelShape LOWER_SHAPE_UNPOWERED_NS = VoxelShapes.or(LOWER_SHAPE, LOWER_NS_W_FENCE, LOWER_NS_E_FENCE);
    private static final VoxelShape LOWER_SHAPE_POWERED_NS = VoxelShapes.or(LOWER_SHAPE_UNPOWERED_NS, PISTON_HEAD_POWERED, LOWER_PISTON_EXTENSION);
    private static final VoxelShape LOWER_SHAPE_UNPOWERED_WE = VoxelShapes.or(LOWER_SHAPE, LOWER_WE_W_FENCE, LOWER_WE_E_FENCE);
    private static final VoxelShape LOWER_SHAPE_POWERED_WE = VoxelShapes.or(LOWER_SHAPE_UNPOWERED_WE, PISTON_HEAD_POWERED, LOWER_PISTON_EXTENSION);

    private static final VoxelShape UPPER_SHAPE_UNPOWERED_NS = VoxelShapes.or(PISTON_BASE, UPPER_PISTON_HEAD, UPPER_NS_W_FENCE, UPPER_NS_E_FENCE);
    private static final VoxelShape UPPER_SHAPE_UNPOWERED_WE = VoxelShapes.or(PISTON_BASE, UPPER_PISTON_HEAD, UPPER_WE_W_FENCE, UPPER_WE_E_FENCE);
    private static final VoxelShape UPPER_SHAPE_POWERED_NS = VoxelShapes.or(PISTON_BASE, UPPER_PISTON_EXTENSION, UPPER_NS_W_FENCE, UPPER_NS_E_FENCE);
    private static final VoxelShape UPPER_SHAPE_POWERED_WE = VoxelShapes.or(PISTON_BASE, UPPER_PISTON_EXTENSION, UPPER_WE_W_FENCE, UPPER_WE_E_FENCE);

    public FruitPressBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER).with(POWERED, Boolean.FALSE));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            if (state.get(POWERED)) {
                if (state.get(FACING) == Direction.WEST || state.get(FACING) == Direction.EAST) return UPPER_SHAPE_POWERED_WE;
                return UPPER_SHAPE_POWERED_NS;
            }
            if (state.get(FACING) == Direction.WEST || state.get(FACING) == Direction.EAST) return UPPER_SHAPE_UNPOWERED_WE;
            return UPPER_SHAPE_UNPOWERED_NS;
        }
        if (state.get(POWERED)) {
            if (state.get(FACING) == Direction.WEST || state.get(FACING) == Direction.EAST) return LOWER_SHAPE_POWERED_WE;
            return LOWER_SHAPE_POWERED_NS;
        }
        if (state.get(FACING) == Direction.WEST || state.get(FACING) == Direction.EAST) return LOWER_SHAPE_UNPOWERED_WE;
        return LOWER_SHAPE_UNPOWERED_NS;
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

        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND && state.get(HALF) == DoubleBlockHalf.LOWER && !state.get(POWERED) && !player.isCrouching()) {

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
        if (state.get(HALF) == DoubleBlockHalf.UPPER) return ActionResultType.PASS;
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
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.with(FACING, direction.rotate(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(HALF).add(POWERED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        BlockPos pos = context.getPos();

        if (pos.getY() < 255 && context.getWorld().getBlockState(pos.up()).isReplaceable(context)) {
            World world = context.getWorld();
            boolean powered = world.isBlockPowered(pos.up());
            return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HALF, DoubleBlockHalf.LOWER).with(POWERED, powered);
        }
        return null;
    }
}
