package com.tieso2001.afm.proxy;

import com.tieso2001.afm.init.BlockInit;
import com.tieso2001.afm.objects.blocks.TileFermenter;
import com.tieso2001.afm.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(BlockInit.FERMENTER);
        GameRegistry.registerTileEntity(TileFermenter.class, Reference.MOD_ID + "_fermenter");
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }

}
