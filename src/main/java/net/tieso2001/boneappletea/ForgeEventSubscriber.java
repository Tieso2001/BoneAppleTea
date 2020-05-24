package net.tieso2001.boneappletea;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.tieso2001.boneappletea.init.ModBlocks;
import net.tieso2001.boneappletea.util.TextUtil;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = BoneAppleTea.MOD_ID)
public final class ForgeEventSubscriber {

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        if (event.getName().equals(new ResourceLocation("minecraft", "blocks/grass"))) {
            event.getTable().addPool(LootPool.builder()
                    .addEntry(TableLootEntry.builder(new ResourceLocation(BoneAppleTea.MOD_ID, "blocks/grass")))
                    .build());
        }
    }

    @SubscribeEvent
    public static void itemTooltip(ItemTooltipEvent event) {

        ArrayList<Item> casks = new ArrayList<>();
        casks.add(ModBlocks.OAK_CASK.get().asItem());
        casks.add(ModBlocks.SPRUCE_CASK.get().asItem());
        casks.add(ModBlocks.BIRCH_CASK.get().asItem());
        casks.add(ModBlocks.JUNGLE_CASK.get().asItem());
        casks.add(ModBlocks.ACACIA_CASK.get().asItem());
        casks.add(ModBlocks.DARK_OAK_CASK.get().asItem());

        ItemStack stack = event.getItemStack();
        if (casks.contains(stack.getItem())) {
            if (stack.hasTag()) {
                CompoundNBT tag = stack.getTag();
                if (!tag.isEmpty()) {
                    ListNBT list = ((CompoundNBT) tag.get("BlockEntityTag")).getList("tanks", 10);
                    if (!list.isEmpty()) {
                        INBT nbt = list.get(0);
                        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT((CompoundNBT) nbt);
                        event.getToolTip().add(new StringTextComponent(TextFormatting.GRAY + TextUtil.fluidTankContentString(fluidStack, 8000)));
                    }
                }
            }
        }
    }
}
