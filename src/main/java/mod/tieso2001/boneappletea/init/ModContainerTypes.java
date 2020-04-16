package mod.tieso2001.boneappletea.init;

import mod.tieso2001.boneappletea.BoneAppleTea;
import mod.tieso2001.boneappletea.inventory.container.CaskContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

import static mod.tieso2001.boneappletea.util.ModUtil.Null;

@ObjectHolder(BoneAppleTea.MOD_ID)
public class ModContainerTypes {

    public static final ContainerType<CaskContainer> CASK = Null();
}
