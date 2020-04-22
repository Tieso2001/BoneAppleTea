package net.tieso2001.boneappletea.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;

import javax.annotation.Nullable;

public class FruitPressBlock extends Block {

    public FruitPressBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.FRUIT_PRESS.create();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
            FruitPressTileEntity tileEntity = (FruitPressTileEntity) worldIn.getTileEntity(pos);
            String fluidName = tileEntity.tank.getFluid().getDisplayName().getString();
            String fluidAmount = Integer.toString(tileEntity.tank.getFluidAmount());
            player.sendMessage(new StringTextComponent(fluidAmount + " mB " + fluidName));
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

}
