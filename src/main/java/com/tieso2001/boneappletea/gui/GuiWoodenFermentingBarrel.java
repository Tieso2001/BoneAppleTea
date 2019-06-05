package com.tieso2001.boneappletea.gui;

import com.tieso2001.boneappletea.BoneAppleTea;
import com.tieso2001.boneappletea.container.ContainerWoodenFermentingBarrel;
import com.tieso2001.boneappletea.tile.TileWoodenFermentingBarrel;
import com.tieso2001.boneappletea.util.GuiUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiWoodenFermentingBarrel extends GuiContainer
{
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private Rectangle inputFluidTankGUI = new Rectangle(53, 15, 16, 55);
    private Rectangle outputFluidTankGUI = new Rectangle(107, 15, 16, 55);

    private TileWoodenFermentingBarrel tileEntity;

    private static final ResourceLocation background = new ResourceLocation(BoneAppleTea.MODID, "textures/gui/wooden_fermenting_barrel.png");

    public GuiWoodenFermentingBarrel(TileWoodenFermentingBarrel tileEntity, ContainerWoodenFermentingBarrel container)
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

        GuiUtil.drawFluidTank(this, tileEntity.getFluidTank(0), inputFluidTankGUI, guiLeft, guiTop);
        GuiUtil.drawFluidTankOverlay(this, guiLeft + inputFluidTankGUI.x, guiTop + inputFluidTankGUI.y);

        GuiUtil.drawFluidTank(this, tileEntity.getFluidTank(1), outputFluidTankGUI, guiLeft, guiTop);
        GuiUtil.drawFluidTankOverlay(this, guiLeft + outputFluidTankGUI.x, guiTop + outputFluidTankGUI.y);
    }

    protected void renderFluidToolTip(int x, int y)
    {
        Rectangle inputFluidTank = new Rectangle(guiLeft + inputFluidTankGUI.x, guiTop + inputFluidTankGUI.y, 16, 55);
        Rectangle outputFluidTank = new Rectangle(guiLeft + outputFluidTankGUI.x, guiTop + outputFluidTankGUI.y, 16, 55);

        if (inputFluidTank.contains(x, y) && tileEntity.getFluidTank(0).getFluid() != null)
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
        else if (outputFluidTank.contains(x, y) && tileEntity.getFluidTank(1).getFluid() != null)
        {
            if (tileEntity.getFluidTank(1).getFluid().amount > 0)
            {
                FontRenderer font = this.mc.fontRenderer;
                List<String> toolTip = new ArrayList<>();
                toolTip.add(tileEntity.getFluidTank(1).getFluid().getLocalizedName());
                toolTip.add(GuiUtil.numberToString(tileEntity.getFluidTank(1).getFluid().amount) + " / " + GuiUtil.numberToString(tileEntity.getFluidTank(1).getCapacity()) + " mB");
                this.drawHoveringText(toolTip, x, y, (font == null ? fontRenderer : font));
            }
        }
    }
}
