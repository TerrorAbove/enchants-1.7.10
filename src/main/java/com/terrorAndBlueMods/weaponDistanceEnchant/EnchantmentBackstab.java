package com.terrorAndBlueMods.weaponDistanceEnchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentBackstab extends Enchantment
{
	public EnchantmentBackstab(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.weapon);
		this.name = "backstab";
	}
	
	public int getMinEnchantability(int level)
	{
		return 6 * level;
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
		return !(e instanceof EnchantmentReachingWeapon);
	}
}