package com.tieso2001.boneappletea.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGoldenFrikandelRoll extends ItemFood {

    public ItemGoldenFrikandelRoll(String name, int amount, boolean isWolfFood) {
        super(amount, isWolfFood);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60 * 20, 2));
            player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 30 * 20, 4));
            player.heal(player.getMaxHealth());
        }

        super.onFoodEaten(stack, worldIn, player);
    }
}
