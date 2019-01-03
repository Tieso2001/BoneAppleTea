package com.tieso2001.boneappletea.handler;

import com.tieso2001.boneappletea.init.ModBlocks;
import com.tieso2001.boneappletea.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class RenderHandler {

    public static void registerCustomMeshesAndStates() {

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BEER), new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "beer"), "fluid");
            }
        });

        ModelLoader.setCustomStateMapper(ModBlocks.BEER, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "beer"), "fluid");
            }
        });

    }

}
