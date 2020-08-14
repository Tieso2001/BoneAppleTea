package net.tieso2001.boneappletea.init;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tieso2001.boneappletea.BoneAppleTea;

public class ModFluids {

    public static final ResourceLocation APPLE_JUICE_STILL_RESOURCE = new ResourceLocation(BoneAppleTea.MOD_ID, "fluid/apple_juice_still");
    public static final ResourceLocation APPLE_JUICE_FLOWING_RESOURCE = new ResourceLocation(BoneAppleTea.MOD_ID, "fluid/apple_juice_flow");

    public static final ResourceLocation CIDER_STILL_RESOURCE = new ResourceLocation(BoneAppleTea.MOD_ID, "fluid/cider_still");
    public static final ResourceLocation CIDER_FLOWING_RESOURCE = new ResourceLocation(BoneAppleTea.MOD_ID, "fluid/cider_flow");

    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, BoneAppleTea.MOD_ID);

    public static final RegistryObject<FlowingFluid> APPLE_JUICE = FLUIDS.register("apple_juice", () ->
            new ForgeFlowingFluid.Source(ModFluids.APPLE_JUICE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> APPLE_JUICE_FLOWING = FLUIDS.register("apple_juice_flowing", () ->
            new ForgeFlowingFluid.Flowing(ModFluids.APPLE_JUICE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties APPLE_JUICE_PROPERTIES = new ForgeFlowingFluid.Properties(() -> ModFluids.APPLE_JUICE.get(), () -> ModFluids.APPLE_JUICE_FLOWING.get(),
            FluidAttributes.builder(APPLE_JUICE_STILL_RESOURCE, APPLE_JUICE_FLOWING_RESOURCE)).bucket(() -> ModItems.APPLE_JUICE_BUCKET.get());

    public static final RegistryObject<FlowingFluid> CIDER = FLUIDS.register("cider", () ->
            new ForgeFlowingFluid.Source(ModFluids.CIDER_PROPERTIES));

    public static final RegistryObject<FlowingFluid> CIDER_FLOWING = FLUIDS.register("cider_flowing", () ->
            new ForgeFlowingFluid.Flowing(ModFluids.CIDER_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CIDER_PROPERTIES = new ForgeFlowingFluid.Properties(() -> ModFluids.CIDER.get(), () -> ModFluids.CIDER_FLOWING.get(),
            FluidAttributes.builder(CIDER_STILL_RESOURCE, CIDER_FLOWING_RESOURCE)).bucket(() -> ModItems.CIDER_BUCKET.get());
}
