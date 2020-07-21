package com.terrorAndBlueMods.weaponDistanceEnchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentReachingWeapon extends Enchantment
{
	public EnchantmentReachingWeapon(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.weapon);
		this.name = "reachingWeapon";
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
