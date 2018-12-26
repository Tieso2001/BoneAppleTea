package com.tieso2001.afm.object.blocks.fermenter;

import com.tieso2001.afm.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiFermenter extends GuiContainer {

    protected Rectangle inputFluidBar = new Rectangle(64, 12, 16, 58);
    protected Rectangle outputFluidBar = new Rectangle(120, 12, 16, 58);

    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

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

        //Draw Fluid Input Tank
        Fluid inputFluid = FluidRegistry.WATER;
        TextureAtlasSprite inputFluidTexture = mc.getTextureMapBlocks().getTextureExtry(inputFluid.getStill().toString());
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        int inputFluidHeight = (fermenter.getInputFluidGuiHeight(inputFluidBar.height));
        drawTexturedModalRect(inputFluidBar.x + guiLeft, inputFluidBar.y + guiTop + (inputFluidBar.height - inputFluidHeight), inputFluidTexture, inputFluidBar.width, inputFluidHeight);

        //Draw Fluid Output Tank
        Fluid outputFluid = FluidRegistry.LAVA;
        TextureAtlasSprite outputFluidTexture = mc.getTextureMapBlocks().getTextureExtry(outputFluid.getStill().toString());
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        int outputFluidHeight = (fermenter.getOutputFluidGuiHeight(outputFluidBar.height));
        drawTexturedModalRect(outputFluidBar.x + guiLeft, outputFluidBar.y + guiTop + (outputFluidBar.height - outputFluidHeight), outputFluidTexture, outputFluidBar.width, outputFluidHeight);

        renderHoveredToolTip(mouseX, mouseY);
    }

}