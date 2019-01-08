package com.tieso2001.boneappletea.object.blocks.fermenter;

import com.tieso2001.boneappletea.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFermenter extends GuiContainer {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;

    private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/fermenter.png");
    private TileFermenter tileEntity;

    public GuiFermenter(InventoryPlayer player, TileFermenter tileEntity) {
        super(new ContainerFermenter(player, tileEntity));

        this.xSize = WIDTH;
        this.ySize = HEIGHT;

        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(background);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        if(TileFermenter.isBurning(tileEntity))
        {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(this.guiLeft + 81, this.guiTop + 36 + 12 - k, 176, 13 - k, 14, k + 1);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Input Tank
        int inputTankAmount = tileEntity.getField(3);
        this.drawString(mc.fontRenderer, "Amount: " + inputTankAmount, guiLeft + 5, guiTop - 10, 0xffffff);

        int heightInput = (int) (((double) inputTankAmount / tileEntity.MAX_TANK_CONTENTS) * 57);
//        drawRect(guiLeft + 45, guiTop + (70 - heightInput), guiLeft + 60, guiTop + 70, 0x24b33f);
//        this.drawGradientRect(guiLeft + 45, guiTop + (70 - heightInput), guiLeft + 60, guiTop + 70, 0x24b33f, 0x24b33f);


        // Output Tank
        int outputTankAmount = tileEntity.getField(4);
        this.drawString(mc.fontRenderer, "Amount: " + outputTankAmount, guiLeft + 110, guiTop - 10, 0xffffff);

        int heightOuput = (int) (((double) outputTankAmount / tileEntity.MAX_TANK_CONTENTS) * 57);
//        drawRect(guiLeft + 45, guiTop + (70 - heightOuput), guiLeft + 60, guiTop + 70, 0x24b33f);
//        this.drawGradientRect(guiLeft + 45, guiTop + (70 - heightOuput), guiLeft + 60, guiTop + 70, 0x24b33f, 0x24b33f);


        this.mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

    private int getBurnLeftScaled(int pixels) {
        int i = this.tileEntity.getField(1);
        if (i == 0) { i = 200; }
        return this.tileEntity.getField(0) * pixels / i;
    }

}