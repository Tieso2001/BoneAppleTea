package net.tieso2001.boneappletea.world.feature;

import com.mojang.datafixers.Dynamic;
import net.tieso2001.boneappletea.block.BarleyBlock;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Function;

public class BarleyFeature extends Feature<NoFeatureConfig> {

    public BarleyFeature(final Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        int i = 0;
        pos = pos.down();

        for (int j = 0; j < 60; ++j) {

            BlockPos blockpos = pos.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));

            if (worldIn.getBlockState(blockpos).getBlock() == Blocks.GRASS_BLOCK && worldIn.isAirBlock(blockpos.up())) {

                if (worldIn.getFluidState(blockpos.west()).isTagged(FluidTags.WATER) || worldIn.getFluidState(blockpos.east()).isTagged(FluidTags.WATER) || worldIn.getFluidState(blockpos.north()).isTagged(FluidTags.WATER) || worldIn.getFluidState(blockpos.south()).isTagged(FluidTags.WATER)
                || worldIn.getBlockState(blockpos.west()).getBlock() == Blocks.FARMLAND || worldIn.getBlockState(blockpos.east()).getBlock() == Blocks.FARMLAND || worldIn.getBlockState(blockpos.north()).getBlock() == Blocks.FARMLAND || worldIn.getBlockState(blockpos.south()).getBlock() == Blocks.FARMLAND) {

                    worldIn.setBlockState(blockpos, Blocks.FARMLAND.getDefaultState().with(FarmlandBlock.MOISTURE, 7), Constants.BlockFlags.DEFAULT);
                    worldIn.setBlockState(blockpos.up(), ModBlocks.BARLEY.getDefaultState().with(BarleyBlock.AGE, rand.nextInt(8)), Constants.BlockFlags.DEFAULT);
                    i++;
                }
            }
        }
        return i > 0;
    }
}
