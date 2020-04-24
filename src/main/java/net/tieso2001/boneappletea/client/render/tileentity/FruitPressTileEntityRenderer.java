package net.tieso2001.boneappletea.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;

public class FruitPressTileEntityRenderer extends TileEntityRenderer<FruitPressTileEntity> {

    public FruitPressTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    /*
    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float r, float g, float b, float a, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(r, g, b, a)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }
    */

    @Override
    public void render(FruitPressTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        /*
        FluidStack fluid = tileEntityIn.tank.getFluid();
        ResourceLocation stillFluid = fluid.getFluid().getAttributes().getStillTexture(fluid);
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(stillFluid);

        int color = fluid.getFluid().getAttributes().getColor(fluid);
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        float a = (color >> 24 & 0xFF) / 255.0F;

        float uMin = sprite.getMinU();
        float uMax = sprite.getMaxU();
        float vMin = sprite.getMinV();
        float vMax = sprite.getMaxV();

        matrixStackIn.push();
        //matrixStackIn.translate(0.125F, 0.375F, 0.125F);
        //matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.scale(0.75F, 0.25F, 0.75F);

        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getTranslucent());

        add(builder, matrixStackIn, 0.125F, 0.375F, 0.125F, r, g, b, a, uMin, vMin);
        add(builder, matrixStackIn, 0.875F, 0.375F, 0.125F, r, g, b, a, uMax, vMin);
        add(builder, matrixStackIn, 0.875F, 0.375F, 0.875F, r, g, b, a, uMax, vMax);
        add(builder, matrixStackIn, 0.125F, 0.375F, 0.875F, r, g, b, a, uMin, vMax);

        matrixStackIn.pop();
        */
    }
}
