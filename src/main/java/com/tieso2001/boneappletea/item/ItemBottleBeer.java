package com.tieso2001.boneappletea.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBottleBeer extends Item
{
    public ItemBottleBeer()
    {
        this.setMaxStackSize(1);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote)
        {
            int random = worldIn.rand.nextInt(13);
            if (random == 0) entityLiving.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 600, 0));
            if (random == 1) entityLiving.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 600, 0));
            if (random == 2) entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE, 600, 0));
            if (random == 3) entityLiving.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 600, 0));
            if (random == 4) entityLiving.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 600, 0));
            if (random == 5) entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 600, 0));
            if (random == 6) entityLiving.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 600, 0));
            if (random == 7) entityLiving.addPotionEffect(new PotionEffect(MobEffects.POISON, 600, 0));
            if (random == 8) entityLiving.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 0));
            if (random == 9) entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 0));
            if (random == 10) entityLiving.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 0));
            if (random == 11) entityLiving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, 0));
            if (random == 12)  entityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 600, 0));
        }
        if (entityLiving instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(entityplayermp, stack);
            entityplayermp.addStat(StatList.getObjectUseStats(this));
        }
        if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }
        return stack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : stack;
    }
}
