package com.tieso2001.afm.init;

import com.tieso2001.afm.object.blocks.fermenter.TileFermenter;
import com.tieso2001.afm.util.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void register() {
        GameRegistry.registerTileEntity(TileFermenter.class, Reference.MOD_ID + ":fermenter");
    }

}
