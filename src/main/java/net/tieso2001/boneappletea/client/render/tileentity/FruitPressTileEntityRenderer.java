package net.tieso2001.boneappletea.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;
import net.tieso2001.boneappletea.tileentity.FruitPressTileEntity;
import net.tieso2001.boneappletea.util.RenderUtil;

public class FruitPressTileEntityRenderer extends TileEntityRenderer<FruitPressTileEntity> {

    public FruitPressTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float r, float g, float b, float a, float u, float v) {
        renderer.pos(stack.getLast().getMatrix(), x, y, z)
                .color(r, g, b, a)
                .tex(u, v)
                .lightmap(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    @Override
    public void render(FruitPressTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        Direction FACING = tileEntityIn.getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);

        // RENDER FOR FLUIDSTACK
        FluidStack fluid = tileEntityIn.getFluidInTank(0);

        if (!fluid.isEmpty()) {

            TextureAtlasSprite sprite = RenderUtil.getFluidTexture(fluid);

            int heightMultiplier = 0;
            if (fluid.getAmount() == 1000) heightMultiplier = 3;
            if (fluid.getAmount() > 500 && fluid.getAmount() < 1000) heightMultiplier = 2;
            if (fluid.getAmount() > 250 && fluid.getAmount() <= 500) heightMultiplier = 1;

            int color = fluid.getFluid().getAttributes().getColor(fluid);
            float r = (color >> 16 & 0xFF) / 255.0F;
            float g = (color >> 8 & 0xFF) / 255.0F;
            float b = (color & 0xFF) / 255.0F;
            float a = (color >> 24 & 0xFF) / 255.0F;

            float uMin = sprite.getMinU();
            float uMax = sprite.getMaxU();
            float vMin = sprite.getMinV();
            float vMax = sprite.getMaxV();

            IVertexBuilder builder = bufferIn.getBuffer(RenderType.getTranslucentNoCrumbling());

            matrixStackIn.push();

            float y = 0.4375F + 0.0625F * heightMultiplier;

            // TOP
            add(builder, matrixStackIn, 0.125F, y, 0.875F, r, g, b, a, uMin, vMax);
            add(builder, matrixStackIn, 0.875F, y, 0.875F, r, g, b, a, uMax, vMax);
            add(builder, matrixStackIn, 0.875F, y, 0.125F, r, g, b, a, uMax, vMin);
            add(builder, matrixStackIn, 0.125F, y, 0.125F, r, g, b, a, uMin, vMin);

            matrixStackIn.pop();
        }

        // TODO: 1+ items, 32+ items, 64 items
        // RENDER FOR ITEMSTACK
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0).copy().getStack();

        if (!stack.isEmpty()) {

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            IBakedModel iBakedModel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);

            for (int i = 0; i < stack.getCount() && i < 3; i++) {

                matrixStackIn.push();

                matrixStackIn.translate(0.5F, 0.38671875F + 0.0390625 * i, 0.5F);
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90));
                matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(120 * i));
                matrixStackIn.scale(0.625F, 0.625F, 0.625F);

                if (FACING == Direction.NORTH) matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(180));
                if (FACING == Direction.SOUTH) matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(0));
                if (FACING == Direction.WEST) matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(90));
                if (FACING == Direction.EAST) matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(270));

                itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, iBakedModel);

                matrixStackIn.pop();
            }
        }
    }
}
