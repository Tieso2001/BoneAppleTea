package com.tieso2001.boneappletea.block;

import com.tieso2001.boneappletea.block.state.StateCornHalf;
import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.init.ModItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCorn extends BlockCrops
{
    public static final PropertyEnum<StateCornHalf> HALF = PropertyEnum.<StateCornHalf>create("half", StateCornHalf.class);

    public BlockCorn(String name)
    {
        setRegistryName(name);
        setTranslationKey(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0).withProperty(HALF, StateCornHalf.LOWER));
    }

    @Override
    protected Item getSeed()
    {
        return ModItems.CORN_KERNELS;
    }

    @Override
    protected Item getCrop()
    {
        return ModItems.CORN;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {AGE, HALF});
    }

    protected StateCornHalf getHalf(IBlockState state)
    {
        return state.getValue(HALF);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        this.checkAndDropBlock(worldIn, pos, state);

        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
        {
            int i = this.getAge(state);

            if (i < this.getMaxAge())
            {
                float f = getGrowthChance(this, worldIn, pos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0))
                {
                    if (this.getHalf(state) == StateCornHalf.LOWER && this.getAge(state) < 6)
                    {
                        worldIn.setBlockState(pos, this.withAge(i + 1).withProperty(HALF, this.getHalf(state)), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                    }
                    else if (this.getHalf(state) == StateCornHalf.LOWER && this.getAge(state) >= 6 && worldIn.getBlockState(pos.up()) == Blocks.AIR.getDefaultState())
                    {
                        worldIn.setBlockState(pos, this.withAge(i + 1).withProperty(HALF, this.getHalf(state)), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));

                        worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, StateCornHalf.UPPER), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos.up(), this.getDefaultState().withProperty(HALF, StateCornHalf.UPPER), worldIn.getBlockState(pos.up()));
                    }
                    else if (this.getHalf(state) == StateCornHalf.UPPER)
                    {
                        worldIn.setBlockState(pos, this.withAge(i + 1).withProperty(HALF, this.getHalf(state)), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                    }
                }
            }
        }
        // Destroy Block
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state)
    {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();

        if (this.getHalf(state) == StateCornHalf.LOWER && i < j)
        {
            worldIn.setBlockState(pos, this.withAge(i).withProperty(HALF, this.getHalf(state)), 2);
        }
        else if (this.getHalf(state) == StateCornHalf.LOWER && i >= j && worldIn.getBlockState(pos.up()) == Blocks.AIR.getDefaultState())
        {
            i = j;
            worldIn.setBlockState(pos, this.withAge(i).withProperty(HALF, this.getHalf(state)), 2);
            worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, StateCornHalf.UPPER), 2);
        }
        else if (this.getHalf(state) == StateCornHalf.UPPER)
        {
            if (i > j) i = j;
            worldIn.setBlockState(pos, this.withAge(i).withProperty(HALF, this.getHalf(state)), 2);
        }
    }

    @Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == Blocks.FARMLAND || state.getBlock() == ModBlocks.CORN;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState soil = worldIn.getBlockState(pos.down());
        boolean checkLight = (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos));
        boolean checkSoil = (soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) || (this.getHalf(state) == StateCornHalf.UPPER));
        return checkLight && checkSoil;
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState stateUp = worldIn.getBlockState(pos.up());
        IBlockState stateDown = worldIn.getBlockState(pos.down());
        if (worldIn.getBlockState(pos.up()).getBlock() == ModBlocks.CORN.getDefaultState().getBlock())
        {
            if (this.getHalf(stateUp) == StateCornHalf.UPPER)
            {
                worldIn.destroyBlock(pos.up(), true);
            }
        }
        else if (worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.CORN.getDefaultState().getBlock())
        {
            if (this.getHalf(stateDown) == StateCornHalf.LOWER)
            {
                worldIn.destroyBlock(pos.down(), true);
            }
        }
    }

    public boolean isFullGrown(IBlockState state)
    {
        return (this.getHalf(state) == StateCornHalf.UPPER && this.getAge(state) == this.getMaxAge());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.isFullGrown(state) ? this.getCrop() : this.getSeed();
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++)
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR)
            {
                drops.add(new ItemStack(item, 1, this.damageDropped(state)));
            }
        }

        int age = getAge(state);
        Random rand1 = world instanceof World ? ((World)world).rand : new Random();

        if (age >= getMaxAge())
        {
            int k = 3 + fortune;

            for (int i = 0; i < k; ++i)
            {
                if (rand1.nextInt(5 * getMaxAge()) <= age)
                {
                    drops.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
    }
}
