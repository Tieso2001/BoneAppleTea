package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.tileentity.TileFermenter;
import com.tieso2001.boneappletea.util.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void register() {
        GameRegistry.registerTileEntity(TileFermenter.class, Reference.MOD_ID + ":fermenter");
    }

}
