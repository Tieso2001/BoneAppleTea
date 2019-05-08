package com.tieso2001.boneappletea.gui;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.container.ContainerStockPot;
import com.tieso2001.boneappletea.tile.TileStockPot;
import com.tieso2001.boneappletea.util.GuiUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiStockPot extends GuiContainer
{
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private Rectangle fluidTankGUI = new Rectangle(80, 14, 16, 55);

    private TileStockPot tileEntity;

    private static final ResourceLocation background = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/stock_pot.png");

    public GuiStockPot(TileStockPot tileEntity, ContainerStockPot container)
    {
        super(container);
        this.xSize = WIDTH;
        this.ySize = HEIGHT;
        this.tileEntity = tileEntity;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        this.renderFluidToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GuiUtil.drawBackground(this, background, guiLeft, guiTop, xSize, ySize);
        GuiUtil.drawFluidTank(this, tileEntity.getFluidTank(), fluidTankGUI, guiLeft, guiTop);
        GuiUtil.drawFluidTankOverlay(this, background, xSize, guiLeft, guiTop, fluidTankGUI);
    }

    protected void renderFluidToolTip(int x, int y)
    {
        Rectangle fluidTank = new Rectangle(guiLeft + fluidTankGUI.x, guiTop + fluidTankGUI.y, 16, 55);
        if (fluidTank.contains(x, y) && tileEntity.getFluidTank().getFluid() != null)
        {
            if (tileEntity.getFluidTank().getFluid().amount > 0)
            {
                FontRenderer font = this.mc.fontRenderer;
                List<String> toolTip = new ArrayList<>();
                toolTip.add(tileEntity.getFluidTank().getFluid().getLocalizedName());
                toolTip.add(tileEntity.getFluidTank().getFluid().amount + " mb");
                this.drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
            }
        }
    }
}
