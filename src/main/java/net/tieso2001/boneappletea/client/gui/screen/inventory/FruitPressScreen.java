package net.tieso2001.boneappletea.client.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.inventory.container.FruitPressContainer;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;
import net.tieso2001.boneappletea.util.RenderUtil;
import net.tieso2001.boneappletea.util.TextUtil;

import java.awt.*;

public class FruitPressScreen extends ContainerScreen<FruitPressContainer> {

    private FruitPressTileEntity tileEntity = this.container.tileEntity;

    public static ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BoneAppleTea.MOD_ID, "textures/gui/container/fruit_press.png");
    private Rectangle GUI_ARROW = new Rectangle(176, 0, 32, 16);
    private Rectangle GUI_TANK = new Rectangle(117, 19, 16, 48);

    public FruitPressScreen(FruitPressContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

    @Override
    public void blit(int x, int y, int u, int v, int width, int height) {
        super.blit(x, y, u, v, width, height);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        if (isPointInRegion(GUI_TANK.x, GUI_TANK.y, GUI_TANK.width, GUI_TANK.height, mouseX, mouseY)) {
            renderTooltip(TextUtil.fluidTankContentList(tileEntity.getTank(0)), mouseX, mouseY);
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), (float)(this.xSize / 2 - this.font.getStringWidth(this.title.getFormattedText()) / 2), 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        // background
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        // arrow
        this.blit(this.guiLeft + 72, this.guiTop + 35, GUI_ARROW.x, GUI_ARROW.y, this.getProgressArrowWidth(), GUI_ARROW.height);

        // fluid
        RenderUtil.renderGuiTank(tileEntity.getFluidInTank(0), tileEntity.getTankCapacity(0), this.guiLeft + GUI_TANK.x, this.guiTop + GUI_TANK.y, GUI_TANK.width, GUI_TANK.height);
    }

    private int getProgressArrowWidth() {
        short maxProcessTime = tileEntity.maxProcessTime;
        short processTimeLeft = tileEntity.processTimeLeft;
        if (processTimeLeft <= 0 || maxProcessTime <= 0) {
            return 0;
        }
        return (maxProcessTime - processTimeLeft) * GUI_ARROW.width / maxProcessTime;
    }
}
