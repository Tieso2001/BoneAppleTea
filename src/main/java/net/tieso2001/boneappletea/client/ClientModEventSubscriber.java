package net.tieso2001.boneappletea.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.init.ModBlocks;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber {

    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
        Block[] cutout_blocks = {
                ModBlocks.BARLEY.get(),
                ModBlocks.FRUIT_PRESS.get(),
                ModBlocks.OAK_CASK.get(),
                ModBlocks.SPRUCE_CASK.get(),
                ModBlocks.BIRCH_CASK.get(),
                ModBlocks.JUNGLE_CASK.get(),
                ModBlocks.ACACIA_CASK.get(),
                ModBlocks.DARK_OAK_CASK.get()
        };

        for (Block block : cutout_blocks) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }
    }
}
