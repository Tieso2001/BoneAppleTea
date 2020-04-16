package mod.tieso2001.boneappletea;

import mod.tieso2001.boneappletea.block.BarleyBlock;
import mod.tieso2001.boneappletea.block.CaskBlock;
import mod.tieso2001.boneappletea.init.ModBlocks;
import mod.tieso2001.boneappletea.init.ModItemGroups;
import mod.tieso2001.boneappletea.inventory.container.CaskContainer;
import mod.tieso2001.boneappletea.recipe.CaskRecipe;
import mod.tieso2001.boneappletea.recipe.CaskRecipeSerializer;
import mod.tieso2001.boneappletea.tileentity.CaskTileEntity;
import mod.tieso2001.boneappletea.world.feature.BarleyFeature;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                setup(new BarleyBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP)), "barley"),

                setup(new CaskBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE).harvestLevel(0)), "oak_cask"),
                setup(new CaskBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE).harvestLevel(0)), "spruce_cask"),
                setup(new CaskBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE).harvestLevel(0)), "birch_cask"),
                setup(new CaskBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE).harvestLevel(0)), "jungle_cask"),
                setup(new CaskBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE).harvestLevel(0)), "acacia_cask"),
                setup(new CaskBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE).harvestLevel(0)), "dark_oak_cask")
        );
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(


                setup(new Item(new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "barley"),
                setup(new Item(new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "barley_grains"),
                setup(new BlockNamedItem(ModBlocks.BARLEY, new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "barley_seeds"),
                setup(new Item(new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "wheat_grains"),

                setup(new BlockItem(ModBlocks.OAK_CASK, new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "oak_cask"),
                setup(new BlockItem(ModBlocks.SPRUCE_CASK, new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "spruce_cask"),
                setup(new BlockItem(ModBlocks.BIRCH_CASK, new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "birch_cask"),
                setup(new BlockItem(ModBlocks.JUNGLE_CASK, new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "jungle_cask"),
                setup(new BlockItem(ModBlocks.ACACIA_CASK, new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "acacia_cask"),
                setup(new BlockItem(ModBlocks.DARK_OAK_CASK, new Item.Properties().group(ModItemGroups.BONE_APPLE_TEA_GROUP)), "dark_oak_cask")
        );
    }

    @SubscribeEvent
    public static void onRegisterTileEntityTypes(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(
                setup(TileEntityType.Builder.create(CaskTileEntity::new, ModBlocks.OAK_CASK, ModBlocks.SPRUCE_CASK, ModBlocks.BIRCH_CASK, ModBlocks.JUNGLE_CASK, ModBlocks.ACACIA_CASK, ModBlocks.DARK_OAK_CASK).build(null), "cask")
        );
    }

    @SubscribeEvent
    public static void onRegisterContainerTypes(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                IForgeContainerType.create(((windowId, inv, data) -> {
                    BlockPos pos = data.readBlockPos();
                    return new CaskContainer(windowId, BoneAppleTea.proxy.getClientWorld(), pos, inv, BoneAppleTea.proxy.getClientPlayer());
                })).setRegistryName("cask")
        );
    }

    @SubscribeEvent
    public static void onRegisterRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().registerAll(
                new CaskRecipeSerializer<>(CaskRecipe::new).setRegistryName(BoneAppleTea.MOD_ID, "cask")
        );
    }

    @SubscribeEvent
    public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(
                new BarleyFeature(NoFeatureConfig::deserialize).setRegistryName("barley")
        );
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(BoneAppleTea.MOD_ID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }
}
