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
    public static final PropertyInteger CROPS_AGE = PropertyInteger.create("age", 0, 12);
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};


    public BlockTallCrops()
    {
        super();
        this.setDefaultState(blockState.getBaseState().withProperty(this.getAgeProperty(), 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if (state.getValue(this.getAgeProperty()) < 8) return CROPS_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
        else return CROPS_AABB[7];
    }

    public Block getCropBlock()
    {
        return Blocks.WHEAT;
    }

    public int getHeight(World worldIn, BlockPos pos, IBlockState state)
    {
        int blockHeight = 1;
        for (int i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this.getCropBlock(); i++)
        {
            blockHeight++;
        }
        return blockHeight;
    }

    public int getHeight(net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state)
    {
        int blockHeight = 1;
        for (int i = 1; world.getBlockState(pos.down(i)).getBlock() == this.getCropBlock(); i++)
        {
            blockHeight++;
        }
        return blockHeight;
    }

    public int getMaxHeight()
    {
        return 3;
    }

    public boolean isFullGrown(IBlockState state)
    {
        return this.getAge(state) == 8 || this.getAge(state) == 12;
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
        return this.getAge(state) == 7 || this.getAge(state) == 8 || this.getAge(state) >= 12;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            int i = this.getAge(state);

            if ((i < this.getMaxAge() || i == 9 || i == 10 || i == 11) && this.canGrow(worldIn, pos, state, false))
            {
                float f = 2 * getGrowthChance(this, worldIn, pos);

                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0))
                {
                    growOnce(worldIn, pos, state);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }
        }
    }

    public void destroyCrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (worldIn.getBlockState(pos).getBlock() != this.getCropBlock()) worldIn.setBlockState(pos, state);

        if (this.getHeight(worldIn, pos, state) == this.getMaxHeight())
        {
            if (this.getAge(worldIn.getBlockState(pos)) == 12) this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
            worldIn.destroyBlock(pos, false);

            for (int i = 1; i < this.getMaxHeight(); i++)
            {
                if (this.getHeight(worldIn, pos, worldIn.getBlockState(pos.down(i))) == 1 && this.getAge(worldIn.getBlockState(pos.down(i))) == 8) this.dropBlockAsItem(worldIn, pos.down(i), worldIn.getBlockState(pos.down(i)), 0);
                worldIn.destroyBlock(pos.down(i), false);
            }
        }
        else {
            int up = 0;
            for (int i = 1; worldIn.getBlockState(pos.up(i)).getBlock() == this.getCropBlock(); i++)
            {
                up++;
            }

            if (up > 0)
            {
                for (int i = up; i > 0; i--)
                {
                    if (this.getAge(worldIn.getBlockState(pos.up(i))) == 12) this.dropBlockAsItem(worldIn, pos.up(i), worldIn.getBlockState(pos.up(i)), 0);
                    worldIn.destroyBlock(pos.up(i), false);
                }
            }

            if (this.getHeight(worldIn, pos, state) > 1)
            {
                int down = 0;
                for (int i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this.getCropBlock(); i++)
                {
                    down++;
                }

                worldIn.destroyBlock(pos, false);

                if (down > 0)
                {
                    for (int i = down; i > 0; i--)
                    {
                        if (this.getHeight(worldIn, pos.down(i), worldIn.getBlockState(pos.down(i))) == 1)
                        {
                            if (this.getAge(worldIn.getBlockState(pos.down(i))) == 8) this.dropBlockAsItem(worldIn, pos.down(i), worldIn.getBlockState(pos.down(i)), 0);
                        }

                        worldIn.destroyBlock(pos.down(i), false);
                    }
                }
            }
            else {
                if (this.getAge(worldIn.getBlockState(pos)) == 8) this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
                worldIn.destroyBlock(pos, false);
            }
        }
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
    {
        destroyCrop(worldIn, pos, state);
    }

    @Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
    {

    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state)
    {
        int age = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int ageMax = this.getMaxAge();
        int height = this.getHeight(worldIn, pos, state);
        int heightMax = this.getMaxHeight();

        if (this.isMaxAge(state)) return;
        if (age >= ageMax) age = ageMax;

        if (height < heightMax)
        {
            worldIn.setBlockState(pos, this.withAge(age));
            if (age == ageMax) worldIn.setBlockState(pos.up(), this.getDefaultState());
        }
        else {
            if (age <= 3) {
                worldIn.setBlockState(pos, this.withAge(age));
            }
            else if (age == 4) worldIn.setBlockState(pos, this.withAge(9));
            else if (age == 5) worldIn.setBlockState(pos, this.withAge(10));
            else if (age == 6) worldIn.setBlockState(pos, this.withAge(11));
            else if (age == 7)
            {
                worldIn.setBlockState(pos, this.withAge(12));
                for (int i = 1; i < this.getMaxHeight(); i++) worldIn.setBlockState(pos.down(i), worldIn.getBlockState(pos.down(i)).withProperty(CROPS_AGE, 8));
            }
        }
    }

    public void growOnce(World worldIn, BlockPos pos, IBlockState state)
    {
        int age = this.getAge(state) + 1;
        int ageMax = this.getMaxAge();
        int height = this.getHeight(worldIn, pos, state);
        int heightMax = this.getMaxHeight();

        if (this.isMaxAge(state)) return;
        if (age >= ageMax) age = ageMax;

        if (height < heightMax)
        {
            worldIn.setBlockState(pos, this.withAge(age));
            if (age == ageMax) worldIn.setBlockState(pos.up(), this.getDefaultState());
        }
        else {
            if (age <= 3) {
                worldIn.setBlockState(pos, this.withAge(age));
            }
            else if (age == 4) worldIn.setBlockState(pos, this.withAge(9));
            else if (age == 5) worldIn.setBlockState(pos, this.withAge(10));
            else if (age == 6) worldIn.setBlockState(pos, this.withAge(11));
            else if (age == 7)
            {
                worldIn.setBlockState(pos, this.withAge(12));
                for (int i = 1; i < this.getMaxHeight(); i++) worldIn.setBlockState(pos.down(i), worldIn.getBlockState(pos.down(i)).withProperty(CROPS_AGE, 8));
            }
        }
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());

        boolean light = worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos);
        boolean below = soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) || (this.getHeight(worldIn, pos, state) > 1 && soil.getBlock() == this.getCropBlock());
        if (state.getBlock() == this.getCropBlock() && soil.getBlock() == Blocks.AIR) return false;
        return light && below;
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        int count = 1;
        for (int i = 0; i < count; i++)
        {
            Item item = null;
            if (this.getAge(state) == 12) item = this.getCrop();
            if (this.getHeight(world, pos, state) == 1) item = this.getSeed();

            if (item != Items.AIR && item != null)
            {
                drops.add(new ItemStack(item, 1, this.damageDropped(state)));
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
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.getSeed();
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        if (this.isMaxAge(state)) return false;
        if (this.isFullGrown(state)) return false;
        if (!(worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR)) return false;
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CROPS_AGE});
    }
}
