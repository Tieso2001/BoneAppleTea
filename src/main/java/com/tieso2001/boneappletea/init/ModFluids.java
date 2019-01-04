package com.tieso2001.boneappletea.init;

import com.tieso2001.boneappletea.object.fluids.FluidLiquid;
import com.tieso2001.boneappletea.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

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


    public static Fluid getFluidFromStack(FluidStack stack) { return stack == null ? null : stack.getFluid(); }

    public static String getFluidName(FluidStack stack) {
        Fluid fluid = getFluidFromStack(stack);
        return getFluidName(fluid);
    }

    public static String getFluidName(Fluid fluid) { return fluid == null ? "null" : fluid.getName(); }

    public static int getAmount(FluidStack stack) { return stack == null ? 0 : stack.amount; }


    public static boolean isValidWaterStack(FluidStack stack) { return ModFluids.getFluidFromStack(stack) == FluidRegistry.WATER; }

    public static boolean isValidLavaStack(FluidStack stack) { return ModFluids.getFluidFromStack(stack) == FluidRegistry.LAVA; }

    public static boolean isValidBeerStack(FluidStack stack) { return ModFluids.getFluidFromStack(stack) == ModFluids.BEER; }

}
