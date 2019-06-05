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
    private Rectangle bubblesGUI = new Rectangle(104, 28, 11, 28);

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
        GuiUtil.drawRectangle(this, background, guiLeft, guiTop,0, 0, xSize, ySize);
        GuiUtil.drawFluidTank(this, tileEntity.getFluidTank(0), fluidTankGUI, guiLeft, guiTop);
        GuiUtil.drawFluidTankOverlay(this, guiLeft + fluidTankGUI.x, guiTop + fluidTankGUI.y);
        renderBubbles();
    }

    protected void renderFluidToolTip(int x, int y)
    {
        Rectangle fluidTank = new Rectangle(guiLeft + fluidTankGUI.x, guiTop + fluidTankGUI.y, 16, 55);
        if (fluidTank.contains(x, y) && tileEntity.getFluidTank(0).getFluid() != null)
        {
            if (tileEntity.getFluidTank(0).getFluid().amount > 0)
            {
                FontRenderer font = this.mc.fontRenderer;
                List<String> toolTip = new ArrayList<>();
                toolTip.add(tileEntity.getFluidTank(0).getFluid().getLocalizedName());
                toolTip.add(GuiUtil.numberToString(tileEntity.getFluidTank(0).getFluid().amount) + " / " + GuiUtil.numberToString(tileEntity.getFluidTank(0).getCapacity()) + " mB");
                this.drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
            }
        }
    }

    protected void renderBubbles()
    {
        int boilTime = this.tileEntity.boilTime;

        if (boilTime > 0)
        {
            int maxBoilTime = this.tileEntity.maxBoilTime;
            int bubblesHeight = (int) (bubblesGUI.height * ((float) (maxBoilTime - boilTime) / maxBoilTime));
            GuiUtil.drawBubbles(this, guiLeft + bubblesGUI.x, guiTop + bubblesGUI.y + (bubblesGUI.height - bubblesHeight), (bubblesGUI.height - bubblesHeight));
            //GuiUtil.drawRectangle(this, background, guiLeft + 103, guiTop + 27 + (29 - bubbleHeight), 176, 29 - bubbleHeight, 12, bubbleHeight);
        }
    }
}
