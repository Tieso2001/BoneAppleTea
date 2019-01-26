package com.tieso2001.boneappletea.gui;

import com.tieso2001.boneappletea.tileentity.TileFermenter;
import com.tieso2001.boneappletea.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiFermenter extends GuiContainer {

    protected Rectangle guiBackground = new Rectangle(guiLeft, guiTop, 176, 166);

    private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/fermenter.png");
    private static final int[] BUBBLELENGTHS = new int[] {29, 24, 20, 16, 11, 6, 0};
    private InventoryPlayer playerInventory;
    private TileFermenter tileEntity;

    public GuiFermenter(InventoryPlayer playerInventory, TileFermenter tileEntity) {
        super(new ContainerFermenter(playerInventory, tileEntity));
        this.playerInventory = playerInventory;
        this.tileEntity = tileEntity;
        this.xSize = guiBackground.width;
        this.ySize = guiBackground.height;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString("Fermenter", this.xSize / 2 - this.fontRenderer.getStringWidth("Fermenter") / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(background);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int i1 = this.tileEntity.getField(0);

        if (i1 > 0) {
            int j1 = (int)(28.0F * (1.0F - (float)i1 / this.tileEntity.fermentingTime));

            if (j1 > 0) {
                this.drawTexturedModalRect(i + 97, j + 16, 176, 0, 9, j1);
            }

            j1 = BUBBLELENGTHS[i1 / 2 % 7];

            if (j1 > 0) {
                this.drawTexturedModalRect(i + 63, j + 14 + 29 - j1, 185, 29 - j1, 12, j1);
            }
        }
    }

}