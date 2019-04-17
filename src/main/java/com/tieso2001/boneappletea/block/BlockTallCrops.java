package com.tieso2001.boneappletea.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockTallCrops extends BlockCrops implements IGrowable
{
    public static final PropertyInteger CROPS_AGE = PropertyInteger.create("age", 0, 13);
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};


    public BlockTallCrops()
    {
        super();
        this.setDefaultState(blockState.getBaseState().withProperty(this.getAgeProperty(), 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if (this.getAge(state) == 0 || this.getAge(state) == 5 || this.getAge(state) == 10) return CROPS_AABB[0];
        if (this.getAge(state) == 1 || this.getAge(state) == 6 || this.getAge(state) == 11) return CROPS_AABB[2];
        if (this.getAge(state) == 2 || this.getAge(state) == 7 || this.getAge(state) == 12) return CROPS_AABB[5];
        if (this.getAge(state) == 3 || this.getAge(state) == 4 || this.getAge(state) == 8 || this.getAge(state) == 9 || this.getAge(state) == 13) return CROPS_AABB[7];
        return CROPS_AABB[7];
    }

    public Block getCropBlock()
    {
        return Blocks.WHEAT;
    }

    public int getHeight(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.getAge(state) <= 4) return 1;
        if (this.getAge(state) >= 10) return this.getMaxHeight();

        int blockHeight = 2;
        for (int i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this.getCropBlock(); i++)
        {
            if (this.getAge(worldIn.getBlockState(pos.down(i))) > 4) blockHeight++;
        }
        return blockHeight;
    }

    public int getMaxHeight()
    {
        return 3;
    }

    public boolean isFullGrown(IBlockState state)
    {
        return this.getAge(state) == 4 || this.getAge(state) == 9 || this.getAge(state) == 13;
    }

    @Override
    public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), age);
    }

    @Override
    protected PropertyInteger getAgeProperty()
    {
        return CROPS_AGE;
    }

    @Override
    public boolean isMaxAge(IBlockState state)
    {
        return this.getAge(state) == 3 || this.getAge(state) == 8 || this.getAge(state) == 13;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        this.checkAndDropBlock(worldIn, pos, state);

        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            if (!this.isFullGrown(state) && !this.isMaxAge(state))
            {
                float f = getGrowthChance(this, worldIn, pos);

                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0))
                {
                    grow(worldIn, pos, state, 1);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    @Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.canBlockStay(worldIn, pos, state))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            breakBlock(worldIn, pos, state);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        for (int i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this.getCropBlock(); i++)
        {
            this.dropBlockAsItem(worldIn, pos.down(i), worldIn.getBlockState(pos.down(i)), 0);
            worldIn.setBlockState(pos.down(i), Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state)
    {
        int ageOld = this.getAge(state);
        int ageNew = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int height = this.getHeight(worldIn, pos, state);
        int heightMax = this.getMaxHeight();

        if (ageOld <= 4) // Base
        {
            if (ageNew >= 3)
            {
                ageNew = 3;
                if (height < heightMax - 1) worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(CROPS_AGE, 5), 2);
                else worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(CROPS_AGE, 10), 2);
            }
            worldIn.setBlockState(pos, this.withAge(ageNew), 2);
        }

        if (ageOld >= 5 && ageOld <= 9) // Middle
        {
            if (ageNew >= 8)
            {
                ageNew = 8;
                if (height < heightMax - 1) worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(CROPS_AGE, 5), 2);
                else worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(CROPS_AGE, 10), 2);
            }
            worldIn.setBlockState(pos, this.withAge(ageNew), 2);
        }

        if (ageOld >= 10 && ageOld <= 13) // Top
        {
            if (ageNew >= 13)
            {
                ageNew = 13;
                for (int i = 1; i < heightMax; i++)
                {
                    worldIn.setBlockState(pos.down(i), worldIn.getBlockState(pos.down(i)).withProperty(CROPS_AGE, (this.getAge(worldIn.getBlockState(pos.down(i))) + 1)), 2);
                }
            }
            worldIn.setBlockState(pos, this.withAge(ageNew), 2);
        }
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state, int amount)
    {
        int ageOld = this.getAge(state);
        int ageNew = this.getAge(state) + amount;
        int height = this.getHeight(worldIn, pos, state);
        int heightMax = this.getMaxHeight();

        if (ageOld <= 4) // Base
        {
            if (ageNew >= 3)
            {
                ageNew = 3;
                if (height < heightMax - 1) worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(CROPS_AGE, 5), 2);
                else worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(CROPS_AGE, 10), 2);
            }
            worldIn.setBlockState(pos, this.withAge(ageNew), 2);
        }

        if (ageOld >= 5 && ageOld <= 9) // Middle
        {
            if (ageNew >= 8)
            {
                ageNew = 8;
                if (height < heightMax - 1) worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(CROPS_AGE, 5), 2);
                else worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(CROPS_AGE, 10), 2);
            }
            worldIn.setBlockState(pos, this.withAge(ageNew), 2);
        }

        if (ageOld >= 10 && ageOld <= 13) // Top
        {
            if (ageNew >= 13)
            {
                ageNew = 13;
                for (int i = 1; i < heightMax; i++)
                {
                    worldIn.setBlockState(pos.down(i), worldIn.getBlockState(pos.down(i)).withProperty(CROPS_AGE, (this.getAge(worldIn.getBlockState(pos.down(i))) + 1)), 2);
                }
            }
            worldIn.setBlockState(pos, this.withAge(ageNew), 2);
        }
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        boolean one = worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos);
        boolean two = worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(worldIn.getBlockState(pos.down()), worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) || worldIn.getBlockState(pos.down()).getBlock() == this.getCropBlock();
        return one && two;
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand0 = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand0);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand0, fortune);
            if (item != Items.AIR)
            {
                drops.add(new ItemStack(item, 1, this.damageDropped(state)));
            }
        }

        Random rand1 = world instanceof World ? ((World)world).rand : new Random();

        if (this.getHeight((World)world, pos, state) == 1 && this.isFullGrown(state))
        {
            int k = 3 + fortune;

            for (int i = 0; i < 3 + fortune; ++i)
            {
                if (rand1.nextInt(2 * this.getMaxAge()) <= this.getMaxAge())
                {
                    drops.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            List<ItemStack> drops = getDrops(worldIn, pos, state, fortune); // use the old method until it gets removed, for backward compatibility
            chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());

            for (ItemStack drop : drops)
            {
                if (worldIn.rand.nextFloat() <= chance)
                {
                    spawnAsEntity(worldIn, pos, drop);
                }
            }
        }

        if (false && !worldIn.isRemote) // Forge: NOP all this.
        {
            if (this.getHeight(worldIn, pos, state) == 1 && this.isFullGrown(state))
            {
                int j = 3 + fortune;

                for (int k = 0; k < j; ++k)
                {
                    if (worldIn.rand.nextInt(2 * this.getMaxAge()) <= this.getMaxAge())
                    {
                        spawnAsEntity(worldIn, pos, new ItemStack(this.getSeed()));
                    }
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (this.getAge(state) == 4) return this.getCrop();
        if (this.getAge(state) < 4) return this.getSeed();
        return null;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        if (this.isFullGrown(state)) return false;
        if (this.isMaxAge(state)) return false;
        if (worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR && this.getAge(state) < 10) return false;
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CROPS_AGE});
    }
}
