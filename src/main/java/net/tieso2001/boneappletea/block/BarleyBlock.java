package net.tieso2001.boneappletea.block;

import net.tieso2001.boneappletea.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;

public class BarleyBlock extends CropsBlock {

    public BarleyBlock(Block.Properties properties) {
       super(properties);
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return ModItems.BARLEY_SEEDS.get();
    }
}
