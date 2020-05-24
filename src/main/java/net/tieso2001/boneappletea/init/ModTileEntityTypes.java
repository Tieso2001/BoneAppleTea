package net.tieso2001.boneappletea.init;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.minecraft.tileentity.TileEntityType;
import net.tieso2001.boneappletea.tileentity.BreweryCaskTileEntity;
import net.tieso2001.boneappletea.tileentity.CaskTileEntity;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;

public final class ModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, BoneAppleTea.MOD_ID);

    public static final RegistryObject<TileEntityType<BreweryCaskTileEntity>> BREWERY_CASK = TILE_ENTITY_TYPES.register("brewery_cask", () ->
            TileEntityType.Builder.create(
                    BreweryCaskTileEntity::new,
                    ModBlocks.OAK_BREWERY_CASK.get(),
                    ModBlocks.SPRUCE_BREWERY_CASK.get(),
                    ModBlocks.BIRCH_BREWERY_CASK.get(),
                    ModBlocks.JUNGLE_BREWERY_CASK.get(),
                    ModBlocks.ACACIA_BREWERY_CASK.get(),
                    ModBlocks.DARK_OAK_BREWERY_CASK.get()
            ).build(null));

    public static final RegistryObject<TileEntityType<CaskTileEntity>> CASK = TILE_ENTITY_TYPES.register("cask", () ->
            TileEntityType.Builder.create(
                    CaskTileEntity::new,
                    ModBlocks.OAK_CASK.get(),
                    ModBlocks.SPRUCE_CASK.get(),
                    ModBlocks.BIRCH_CASK.get(),
                    ModBlocks.JUNGLE_CASK.get(),
                    ModBlocks.ACACIA_CASK.get(),
                    ModBlocks.DARK_OAK_CASK.get()
            ).build(null));

    public static final RegistryObject<TileEntityType<FruitPressTileEntity>> FRUIT_PRESS = TILE_ENTITY_TYPES.register("fruit_press", () ->
            TileEntityType.Builder.create(
                    FruitPressTileEntity::new,
                    ModBlocks.OAK_FRUIT_PRESS.get(),
                    ModBlocks.SPRUCE_FRUIT_PRESS.get(),
                    ModBlocks.BIRCH_FRUIT_PRESS.get(),
                    ModBlocks.JUNGLE_FRUIT_PRESS.get(),
                    ModBlocks.ACACIA_FRUIT_PRESS.get(),
                    ModBlocks.DARK_OAK_FRUIT_PRESS.get()
            ).build(null));
}
