package net.tieso2001.boneappletea.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.tieso2001.boneappletea.init.ModBlocks;

public class ClientRenderer {

    public static void registerBlockRenderLayer() {
        Block[] cutout_blocks = {
                ModBlocks.BARLEY,
                ModBlocks.FRUIT_PRESS,
                ModBlocks.OAK_CASK,
                ModBlocks.BIRCH_CASK,
                ModBlocks.SPRUCE_CASK,
                ModBlocks.JUNGLE_CASK,
                ModBlocks.ACACIA_CASK,
                ModBlocks.DARK_OAK_CASK
        };

        for (Block block : cutout_blocks) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }
    }
}
