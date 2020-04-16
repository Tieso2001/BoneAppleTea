package mod.tieso2001.boneappletea;

import mod.tieso2001.boneappletea.setup.ClientProxy;
import mod.tieso2001.boneappletea.setup.IProxy;
import mod.tieso2001.boneappletea.setup.ServerProxy;
import mod.tieso2001.boneappletea.world.WorldGen;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BoneAppleTea.MOD_ID)
public class BoneAppleTea {

    public static final String MOD_ID = "boneappletea";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public BoneAppleTea() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
        DeferredWorkQueue.runLater(WorldGen::setupWorldGen);
    }
}
