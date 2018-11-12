package com.tieso2001.afm.objects.blocks;

import com.tieso2001.afm.init.BlockInit;
import com.tieso2001.afm.init.ItemInit;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCornPlant extends BlockCrops {

    private static final AxisAlignedBB[] corn =
            new AxisAlignedBB[]{
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
                    new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)
            };

    public BlockCornPlant(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);

        BlockInit.BLOCKS.add(this);
    }

    /*
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            if (this.isMaxAge(state)) {
                worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemInit.CORN, 1)));
                worldIn.setBlockState(pos, this.withAge(0));
                return true;
            }
        }
        return false;
    }
    */

    @Override
    protected Item getSeed() {
        return ItemInit.CORN_KERNELS;
    }

    @Override
    protected Item getCrop() {
        return ItemInit.CORN;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return corn[((Integer) state.getValue(this.getAgeProperty())).intValue()];
    }

}