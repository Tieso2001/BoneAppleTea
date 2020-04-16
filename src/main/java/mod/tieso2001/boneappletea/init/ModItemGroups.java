package mod.tieso2001.boneappletea.init;

import mod.tieso2001.boneappletea.BoneAppleTea;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ModItemGroups {

    public static final ItemGroup BONE_APPLE_TEA_GROUP = new ModItemGroup(BoneAppleTea.MOD_ID, () -> new ItemStack(ModItems.BARLEY));

    public static final class ModItemGroup extends ItemGroup {

        @Nonnull
        private final Supplier<ItemStack> iconSupplier;

        public ModItemGroup(@Nonnull final String name, @Nonnull final Supplier<ItemStack> iconSupplier) {
            super(name);
            this.iconSupplier = iconSupplier;
        }

        @Override
        @Nonnull
        public ItemStack createIcon() {
            return iconSupplier.get();
        }
    }
}
