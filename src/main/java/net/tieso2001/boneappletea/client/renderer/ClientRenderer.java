package net.tieso2001.boneappletea.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.tieso2001.boneappletea.init.ModBlocks;

public class ClientRenderer {

    public static void registerBlockRenderLayer() {
        Block[] crops = {
                ModBlocks.BARLEY
        };

        for (Block block : crops) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }
    }
}
