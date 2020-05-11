package net.tieso2001.boneappletea.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.inventory.container.CaskContainer;
import net.tieso2001.boneappletea.inventory.container.FruitPressContainer;

public final class ModContainerTypes {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, BoneAppleTea.MOD_ID);

    public static final RegistryObject<ContainerType<CaskContainer>> CASK = CONTAINER_TYPES.register("cask", () ->
            IForgeContainerType.create(CaskContainer::new));

    public static final RegistryObject<ContainerType<FruitPressContainer>> FRUIT_PRESS = CONTAINER_TYPES.register("fruit_press", () ->
            IForgeContainerType.create(FruitPressContainer::new));
}
