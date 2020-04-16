package mod.tieso2001.boneappletea.init;

import mod.tieso2001.boneappletea.BoneAppleTea;
import mod.tieso2001.boneappletea.recipe.CaskRecipe;
import mod.tieso2001.boneappletea.recipe.CaskRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

import static mod.tieso2001.boneappletea.util.ModUtil.Null;

@ObjectHolder(BoneAppleTea.MOD_ID)
public class ModRecipeSerializers {

    public static final CaskRecipeSerializer<CaskRecipe> CASK = Null();
}
