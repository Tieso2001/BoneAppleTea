package com.tieso2001.afm.object.blocks.fermenter;

import com.tieso2001.afm.util.FluidStackRenderer;
import com.tieso2001.afm.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GuiFermenter extends GuiContainer {

    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

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

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        FluidStackRenderer.renderFluidStack(new FluidStack(FluidRegistry.WATER, 1000), guiLeft + 64, guiTop + 12);
        renderHoveredToolTip(mouseX, mouseY);
    }

}

