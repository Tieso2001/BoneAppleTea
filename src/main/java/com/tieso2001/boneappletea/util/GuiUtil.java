package com.tieso2001.boneappletea.util;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiUtil
{
    public static void drawRectangle(GuiContainer container, ResourceLocation rectangle, int xPos, int yPos, int xSize, int ySize)
    {
        bindTexture(container, rectangle);
        container.drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
    }

    public static void drawFluidTank(GuiContainer container, FluidTank fluidTank, Rectangle fluidTankGUI, int guiLeft, int guiTop)
    {
        FluidStack fluidStack = fluidTank.getFluid();
        if (fluidStack == null) fluidStack = new FluidStack(FluidRegistry.WATER, 0);

        float fluidPercentage = (float) fluidStack.amount / (float) fluidTank.getCapacity();
        int fluidHeight = (int) Math.ceil(fluidPercentage * (float) 55);

        TextureAtlasSprite fluidTexture = getFluidTexture(container, fluidStack.getFluid());
        bindTexture(container, TextureMap.LOCATION_BLOCKS_TEXTURE);

        float minU = fluidTexture.getInterpolatedU(0);
        float minV = fluidTexture.getInterpolatedV(0);
        float maxU = fluidTexture.getInterpolatedU(16);
        float maxV = fluidTexture.getInterpolatedV(16);

        for (int i = 0; i < fluidHeight / 16; i++) {
            drawFluidQuad(guiLeft + fluidTankGUI.x, guiTop + fluidTankGUI.y + (i * 16) + (fluidTankGUI.height - fluidHeight), 16, 16, minU, minV, maxU, maxV);
        }
        if (fluidHeight % 16 != 0) {
            drawFluidQuad(guiLeft + fluidTankGUI.x, guiTop + fluidTankGUI.y + fluidHeight - (fluidHeight % 16) + (fluidTankGUI.height - fluidHeight), 16, fluidHeight % 16, minU, minV, maxU, fluidTexture.getInterpolatedV(fluidHeight % 16));
        }
    }

    private static void drawFluidQuad(int x, int y, int width, int height, float minU, float minV, float maxU, float maxV)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)x, (double)(y + height), (double)0).tex(minU, maxV).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), (double)0).tex(maxU, maxV).endVertex();
        buffer.pos((double)(x + width), (double)y, (double)0).tex(maxU, minV).endVertex();
        buffer.pos((double)x, (double)y, (double)0).tex(minU, minV).endVertex();
        tessellator.draw();
    }

    public static void drawFluidTankOverlay(GuiContainer container, ResourceLocation guiTexture, int guiWidth, int guiHeight, int guiLeft, int guiTop, Rectangle fluidTankGUI)
    {
        bindTexture(container, guiTexture);
        container.drawTexturedModalRect(guiLeft + fluidTankGUI.x, guiTop + fluidTankGUI.y, guiWidth, guiHeight, fluidTankGUI.width, fluidTankGUI.height);
    }

    private static TextureAtlasSprite getFluidTexture(GuiContainer container, Fluid fluid)
    {
        return container.mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
    }

    public static String numberToString(int number)
    {
        int thousand;
        for (thousand = 0; number >= 1000; thousand++)
        {
            number = number - 1000;
        }
        if (thousand > 0 && number >= 100) return thousand + "," + number;
        if (thousand > 0 && number >= 10) return thousand + "," + "0" + number;
        if (thousand > 0 && number >= 0) return thousand + "," + "00" + number;
        return "" + number;
    }

    private static void bindTexture(GuiContainer container, ResourceLocation resource)
    {
        container.mc.getTextureManager().bindTexture(resource);
    }
}
