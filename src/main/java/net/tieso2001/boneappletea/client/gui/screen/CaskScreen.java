package net.tieso2001.boneappletea.client.gui.screen;

import net.tieso2001.boneappletea.BoneAppleTea;
import net.tieso2001.boneappletea.inventory.container.CaskContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CaskScreen extends ContainerScreen<CaskContainer> {

    private ResourceLocation GUI = new ResourceLocation(BoneAppleTea.MOD_ID, "textures/gui/cask.png");

    public CaskScreen(CaskContainer container, PlayerInventory inventory, ITextComponent name) {
        super(container, inventory, name);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
    }
}
