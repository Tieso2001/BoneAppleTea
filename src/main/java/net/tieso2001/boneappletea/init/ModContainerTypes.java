package net.tieso2001.boneappletea.init;

import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.inventory.container.CaskContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;
import net.tieso2001.boneappletea.util.ModUtil;

@ObjectHolder(BoneAppleTea.MOD_ID)
public class ModContainerTypes {

    public static final ContainerType<CaskContainer> CASK = ModUtil.Null();
}
