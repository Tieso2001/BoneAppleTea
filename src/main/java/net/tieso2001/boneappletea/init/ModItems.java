package net.tieso2001.boneappletea.init;

import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.item.DrinkBucketItem;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, BoneAppleTea.MOD_ID);

    public static final RegistryObject<Item> BARLEY = ITEMS.register("barley", () ->
            new Item(new Item.Properties()
                    .group(ModItemGroups.BONE_APPLE_TEA_GROUP)
            ));

    public static final RegistryObject<Item> BARLEY_GRAINS = ITEMS.register("barley_grains", () ->
            new Item(new Item.Properties()
                    .group(ModItemGroups.BONE_APPLE_TEA_GROUP)
            ));

    public static final RegistryObject<Item> BARLEY_SEEDS = ITEMS.register("barley_seeds", () ->
            new BlockNamedItem(ModBlocks.BARLEY.get(), new Item.Properties()
                    .group(ModItemGroups.BONE_APPLE_TEA_GROUP)
            ));

    public static final RegistryObject<Item> WHEAT_GRAINS = ITEMS.register("wheat_grains", () ->
            new Item(new Item.Properties()
                    .group(ModItemGroups.BONE_APPLE_TEA_GROUP)
            ));

    public static final RegistryObject<Item> APPLE_JUICE_BUCKET = ITEMS.register("apple_juice_bucket", () ->
            new DrinkBucketItem(() -> ModFluids.APPLE_JUICE.get(), new Item.Properties()
                    .group(ModItemGroups.BONE_APPLE_TEA_GROUP)
                    .food(new Food.Builder().hunger(0).saturation(1.0F).build())
                    .maxStackSize(1)
                    .containerItem(Items.BUCKET)));

    public static final RegistryObject<Item> CIDER_BUCKET = ITEMS.register("cider_bucket", () ->
            new DrinkBucketItem(() -> ModFluids.CIDER.get(), new Item.Properties()
                    .group(ModItemGroups.BONE_APPLE_TEA_GROUP)
                    .food(new Food.Builder().hunger(0).saturation(1.0F).effect(new EffectInstance(Effects.SPEED, 600, 0), 1.0F).build())
                    .maxStackSize(1)
                    .containerItem(Items.BUCKET)));
}
