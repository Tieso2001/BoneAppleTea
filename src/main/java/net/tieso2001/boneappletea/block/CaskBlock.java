package net.tieso2001.boneappletea.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.tieso2001.boneappletea.tileentity.CaskTileEntity;
import net.tieso2001.boneappletea.util.TextUtil;

import javax.annotation.Nullable;

public class CaskBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public CaskBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public CaskTileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.CASK.get().create();
    }

    @Nullable
    private CaskTileEntity getTileEntity(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        return tileEntity instanceof CaskTileEntity ? (CaskTileEntity) tileEntity : null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        CaskTileEntity tileEntity = getTileEntity(worldIn, pos);

        if (tileEntity != null && !worldIn.isRemote) {
            if (handIn == Hand.MAIN_HAND) {
                if (FluidUtil.interactWithFluidHandler(player, handIn, worldIn, pos, hit.getFace())) {
                    worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                player.sendStatusMessage(new StringTextComponent(TextUtil.fluidTankContentString(tileEntity.getTank(0))), true);
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }
}
