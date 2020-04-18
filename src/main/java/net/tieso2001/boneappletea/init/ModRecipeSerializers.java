package net.tieso2001.boneappletea.init;

import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.recipe.CaskRecipe;
import net.tieso2001.boneappletea.recipe.CaskRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;
import net.tieso2001.boneappletea.util.ModUtil;

@ObjectHolder(BoneAppleTea.MOD_ID)
public class ModRecipeSerializers {

    public static final CaskRecipeSerializer<CaskRecipe> CASK = ModUtil.Null();
}
