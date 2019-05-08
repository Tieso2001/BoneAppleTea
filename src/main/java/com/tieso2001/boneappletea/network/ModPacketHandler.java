package com.tieso2001.boneappletea.network;

import com.tieso2001.boneappletea.BoneAppleTea;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ModPacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BoneAppleTea.MODID);
}
