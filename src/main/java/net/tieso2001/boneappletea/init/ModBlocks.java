package net.tieso2001.boneappletea.init;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.minecraft.block.Block;
import net.tieso2001.boneappletea.block.BarleyBlock;
import net.tieso2001.boneappletea.block.CaskBlock;
import net.tieso2001.boneappletea.block.FruitPressBlock;

import java.util.function.Supplier;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, BoneAppleTea.MOD_ID);

    public static final RegistryObject<Block> BARLEY = BLOCKS.register("barley", () ->
            new BarleyBlock(Block.Properties
                    .create(Material.PLANTS)
                    .doesNotBlockMovement()
                    .tickRandomly()
                    .hardnessAndResistance(0.0F)
                    .sound(SoundType.CROP)
            ));

    public static final RegistryObject<Block> FRUIT_PRESS = BLOCKS.register("fruit_press", () ->
            new FruitPressBlock(Block.Properties
                    .create(Material.WOOD)
                    .notSolid()
                    .hardnessAndResistance(2.0F)
                    .sound(SoundType.WOOD)
                    .harvestTool(ToolType.AXE)
                    .harvestLevel(0)
            ));

    private static final Supplier<? extends Block> caskSupplier = () ->
            new CaskBlock(Block.Properties
                    .create(Material.WOOD)
                    .notSolid()
                    .hardnessAndResistance(2.0F)
                    .sound(SoundType.WOOD)
                    .harvestTool(ToolType.AXE)
                    .harvestLevel(0)
            );

    public static final RegistryObject<Block> OAK_CASK = BLOCKS.register("oak_cask", caskSupplier);
    public static final RegistryObject<Block> SPRUCE_CASK = BLOCKS.register("spruce_cask", caskSupplier);
    public static final RegistryObject<Block> BIRCH_CASK = BLOCKS.register("birch_cask", caskSupplier);
    public static final RegistryObject<Block> JUNGLE_CASK = BLOCKS.register("jungle_cask", caskSupplier);
    public static final RegistryObject<Block> ACACIA_CASK = BLOCKS.register("acacia_cask", caskSupplier);
    public static final RegistryObject<Block> DARK_OAK_CASK = BLOCKS.register("dark_oak_cask", caskSupplier);
}
