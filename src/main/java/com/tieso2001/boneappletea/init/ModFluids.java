package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.object.FluidLiquid;
import com.tieso2001.boneappletea.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {

    public static Fluid BEER;

    public static void init() {
        BEER = new FluidLiquid("beer", new ResourceLocation(Reference.MOD_ID, "blocks/beer_still"), new ResourceLocation(Reference.MOD_ID, "blocks/beer_flow"));
    }

    public static void register() {
        registerFluid(BEER, false);
    }

    public static void registerFluid(Fluid fluid, boolean addBucket) {
        FluidRegistry.registerFluid(fluid);
        if (addBucket) { FluidRegistry.addBucketForFluid(fluid); }
    }

}
