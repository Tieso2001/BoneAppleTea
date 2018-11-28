package com.tieso2001.afm.objects.blocks.machines;

import com.tieso2001.afm.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import scala.tools.nsc.backend.icode.TypeKinds;

public class GuiFermenter extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation background = new ResourceLocation(Reference.MOD_ID, "textures/gui/fermenter.png");

    public GuiFermenter(TileFermenter tileEntity, ContainerFermenter container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}

