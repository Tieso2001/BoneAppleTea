package net.tieso2001.boneappletea.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

public class CaskBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public CaskBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
    }

    /*
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BreweryCaskTileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.CASK.get().create();
    }

    @Nullable
    private CaskTileEntity getTileEntity(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        return tileEntity instanceof CaskTileEntity ? (CaskTileEntity) tileEntity : null;
    }
    */

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
