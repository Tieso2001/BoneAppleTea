package net.tieso2001.boneappletea.setup;

import net.tieso2001.boneappletea.client.gui.screen.CaskScreen;
import net.tieso2001.boneappletea.init.ModContainerTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    @Override
    public void init() {
        ScreenManager.registerFactory(ModContainerTypes.CASK, CaskScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
