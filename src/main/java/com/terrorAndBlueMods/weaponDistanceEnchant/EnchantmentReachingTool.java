package com.terrorAndBlueMods.weaponDistanceEnchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentReachingTool extends Enchantment
{
	public EnchantmentReachingTool(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.digger);
		this.name = "reachingTool";
	}
	
	public int getMinEnchantability(int level)
	{
		return 2 + 8 * level;
	}
	
	public int getMaxEnchantability(int level)
	{
		return getMinEnchantability(level) + 5;
	}
	
	public int getMaxLevel()
	{
		return 4;
	}
	
	public boolean canApplyTogether(Enchantment e)
	{
		return true;
	}
}
