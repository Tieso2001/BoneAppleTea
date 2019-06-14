package com.tieso2001.boneappletea.util;

import com.tieso2001.boneappletea.BoneAppleTea;
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
    // Draw

    public static void drawRectangle(GuiContainer container, ResourceLocation rectangle, int xPos, int yPos, int xPosTexture, int yPosTexture, int xSize, int ySize)
    {
        bindTexture(container, rectangle);
        container.drawTexturedModalRect(xPos, yPos, xPosTexture, yPosTexture, xSize, ySize);
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

    public static void drawFluidTankOverlay(GuiContainer container, int tankLocationX, int tankLocationY)
    {
        bindTexture(container, new ResourceLocation(BoneAppleTea.MODID, "textures/gui/elements/fluidtank_overlay.png"));
        container.drawTexturedModalRect(tankLocationX, tankLocationY, 0, 0, 16, 55);
    }

    public static void drawFire(GuiContainer container, int fireLocationX, int fireLocationY, float progress)
    {
        bindTexture(container, new ResourceLocation(BoneAppleTea.MODID, "textures/gui/elements/fire.png"));
        container.drawTexturedModalRect(fireLocationX, fireLocationY + 14 - (int) (progress * 14), 0, 14 - (int) (progress * 14), 14, (int) (progress * 14));
    }

    public static void drawArrow0(GuiContainer container, int arrowLocationX, int arrowLocationY, float progress)
    {
        bindTexture(container, new ResourceLocation(BoneAppleTea.MODID, "textures/gui/elements/arrow_0.png"));
        container.drawTexturedModalRect(arrowLocationX, arrowLocationY, 0, 0, (int) (progress * 22), 16);
    }

    // Other

    private static void bindTexture(GuiContainer container, ResourceLocation resource)
    {
        container.mc.getTextureManager().bindTexture(resource);
    }

    private static TextureAtlasSprite getFluidTexture(GuiContainer container, Fluid fluid)
    {
        return container.mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
    }

    public static String getRealTime(int ticks)
    {
        if (ticks < 20) return "0";

        int timeSeconds = ticks / 20;

        int timeMinutes = 0;
        for (int i = timeSeconds; i >= 60; i = i - 60)
        {
            timeMinutes++;
            timeSeconds = timeSeconds - 60;
        }

        int timeHours = 0;
        for (int i = timeMinutes; i >= 60; i = i - 60)
        {
            timeHours++;
            timeMinutes = timeMinutes - 60;
        }

        String string = timeSeconds + "s";

        if (timeHours > 0)
        {
            if (timeSeconds < 10) string = "0" + string;

            if (timeMinutes < 10) string = timeHours + "h 0" + timeMinutes + "m " + string;
            else string = timeHours + "h " + timeMinutes + "m " + string;
        }
        else if (timeMinutes > 0)
        {
            if (timeSeconds < 10) string = timeMinutes + "m 0" + string;
            else string = timeMinutes + "m " + string;
        }

        return string;
    }

    public static String getFluidTankAmount(FluidTank tank)
    {
        String tankCapacity = Integer.toString(tank.getCapacity());
        if (tank.getCapacity() >= 1000)
        {
            StringBuilder builder = new StringBuilder();
            builder.append(tankCapacity).insert(tankCapacity.length() - 3, ",");
            tankCapacity = builder.toString();
        }

        String fluidAmount = Integer.toString(tank.getFluidAmount());
        if (tank.getFluidAmount() >= 1000)
        {
            StringBuilder builder = new StringBuilder();
            builder.append(fluidAmount).insert(fluidAmount.length() - 3, ",");
            fluidAmount = builder.toString();
        }

        return fluidAmount + " / " + tankCapacity + " mB";
    }
}
