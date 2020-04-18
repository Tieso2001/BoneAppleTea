package net.tieso2001.boneappletea;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MOD_ID)
public class EventHandlers {

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        if (event.getName().equals(new ResourceLocation("minecraft", "blocks/grass"))) {
            // Temporary method which adds a whole new pool to the loottable instead of adding a new entry to the already existing pool
            // This causes barley seeds to still be able to drop when using shears on the grass (which should not be possible!)
            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(BoneAppleTea.MOD_ID, "blocks/grass"))).build());
        }
    }
}
