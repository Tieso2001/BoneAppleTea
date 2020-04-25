package net.tieso2001.boneappletea;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.tieso2001.boneappletea.init.ModItemGroups;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

    private static ArrayList<Block> noBlockItems = new ArrayList<>();

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        noBlockItems.add(ModBlocks.BARLEY.get());

        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> !noBlockItems.contains(block))
                .forEach(block -> event.getRegistry().register(new BlockItem(block, new Item.Properties()
                                .group(ModItemGroups.BONE_APPLE_TEA_GROUP))
                                .setRegistryName(block.getRegistryName())));
    }
}
