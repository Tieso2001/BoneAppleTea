package net.tieso2001.boneappletea.client;

import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.client.gui.screen.inventory.CaskScreen;
import net.tieso2001.boneappletea.client.gui.screen.inventory.FruitPressScreen;
import net.tieso2001.boneappletea.client.render.tileentity.FruitPressTileEntityRenderer;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.tieso2001.boneappletea.init.ModContainerTypes;
import net.tieso2001.boneappletea.init.ModTileEntityTypes;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber {

    @SubscribeEvent
    public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
        Block[] cutout_blocks = {
                ModBlocks.BARLEY.get(),
                ModBlocks.OAK_CASK.get(),
                ModBlocks.SPRUCE_CASK.get(),
                ModBlocks.BIRCH_CASK.get(),
                ModBlocks.JUNGLE_CASK.get(),
                ModBlocks.ACACIA_CASK.get(),
                ModBlocks.DARK_OAK_CASK.get(),
                ModBlocks.OAK_FRUIT_PRESS.get(),
                ModBlocks.SPRUCE_FRUIT_PRESS.get(),
                ModBlocks.BIRCH_FRUIT_PRESS.get(),
                ModBlocks.JUNGLE_FRUIT_PRESS.get(),
                ModBlocks.ACACIA_FRUIT_PRESS.get(),
                ModBlocks.DARK_OAK_FRUIT_PRESS.get()
        };

        for (Block block : cutout_blocks) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }

        ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.FRUIT_PRESS.get(), FruitPressTileEntityRenderer::new);

        DeferredWorkQueue.runLater(() -> {
            ScreenManager.registerFactory(ModContainerTypes.CASK.get(), CaskScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.FRUIT_PRESS.get(), FruitPressScreen::new);
        });
    }
}
