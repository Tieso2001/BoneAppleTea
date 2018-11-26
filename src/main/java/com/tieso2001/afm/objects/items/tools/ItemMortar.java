package com.tieso2001.afm.objects.items.tools;

import com.tieso2001.afm.Main;
import com.tieso2001.afm.init.ItemInit;
import com.tieso2001.afm.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class ItemMortar extends ItemTool implements IHasModel {
    private static String name = "Mortar and Pestle";

    public ItemMortar(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
        super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.afmtab);
        setNoRepair();

        ItemInit.ITEMS.add(this);
    }

    public ItemMortar(ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
        super(materialIn, effectiveBlocksIn);

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Main.afmtab);
        setNoRepair();

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        return EnumActionResult.SUCCESS;
    }
}
