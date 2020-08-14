package net.tieso2001.boneappletea.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

public class DrinkBucketItem extends BucketItem {

    public DrinkBucketItem(java.util.function.Supplier<? extends Fluid> supplier, Item.Properties builder) {
        super(supplier, builder);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        worldIn.playSound(null, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), this.getDrinkSound(), SoundCategory.NEUTRAL, 1.0F, 1.0F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.4F);
        FoodStats foodStats = ((PlayerEntity) entityLiving).getFoodStats();
        if (getFood() != null) {
            foodStats.setFoodLevel(foodStats.getFoodLevel() + getFood().getHealing());
            foodStats.setFoodSaturationLevel(foodStats.getSaturationLevel() + getFood().getSaturation());
            for(Pair<EffectInstance, Float> pair : getFood().getEffects()) {
                if (!worldIn.isRemote && pair.getLeft() != null && worldIn.rand.nextFloat() < pair.getRight()) {
                    entityLiving.addPotionEffect(new EffectInstance(pair.getLeft()));
                }
            }
        }
        if (!((PlayerEntity)entityLiving).abilities.isCreativeMode) {
            stack.shrink(1);
        }

        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @javax.annotation.Nullable net.minecraft.nbt.CompoundNBT nbt) {
        return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
    }
}
