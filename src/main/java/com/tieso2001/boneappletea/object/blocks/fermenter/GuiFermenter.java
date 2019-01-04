package com.tieso2001.boneappletea.object.blocks.fermenter;

import com.tieso2001.boneappletea.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class GuiFermenter extends GuiContainer {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;

    private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/fermenter.png");
    private TileFermenter fermenter;

    public GuiFermenter(TileFermenter tileEntity, ContainerFermenter container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        fermenter = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Input Tank
        int inputTankAmount = fermenter.getInputTankAmount();
        drawString(mc.fontRenderer, "Amount: " + inputTankAmount, guiLeft + 5, guiTop - 10, 0xffffff);

        int heightInput = (int) (((double) inputTankAmount / fermenter.MAX_TANK_CONTENTS) * 57);
//        drawRect(guiLeft + 45, guiTop + (70 - heightInput), guiLeft + 60, guiTop + 70, 0x24b33f);
//        this.drawGradientRect(guiLeft + 45, guiTop + (70 - heightInput), guiLeft + 60, guiTop + 70, 0x24b33f, 0x24b33f);


        // Output Tank
        int outputTankAmount = fermenter.getOutputTankAmount();
        drawString(mc.fontRenderer, "Amount: " + outputTankAmount, guiLeft + 110, guiTop - 10, 0xffffff);

        int heightOuput = (int) (((double) outputTankAmount / fermenter.MAX_TANK_CONTENTS) * 57);
//        drawRect(guiLeft + 45, guiTop + (70 - heightOuput), guiLeft + 60, guiTop + 70, 0x24b33f);
//        this.drawGradientRect(guiLeft + 45, guiTop + (70 - heightOuput), guiLeft + 60, guiTop + 70, 0x24b33f, 0x24b33f);


        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }

}