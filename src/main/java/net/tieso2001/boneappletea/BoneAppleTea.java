package net.tieso2001.boneappletea;

import net.minecraftforge.eventbus.api.IEventBus;
import net.tieso2001.boneappletea.init.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BoneAppleTea.MOD_ID)
public final class BoneAppleTea {

    public static final String MOD_ID = "boneappletea";

    public BoneAppleTea() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
    }
}
