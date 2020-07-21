package com.terrorAndBlueMods.weaponDistanceEnchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentSerrated extends Enchantment
{
	public EnchantmentSerrated(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.weapon);
		this.name = "serrated";
	}
	
	public int getMinEnchantability(int level)
	{
		return 4 + 16 * level;
	}
	
	public int getMaxEnchantability(int level)
	{
		return getMinEnchantability(level) + 10;
	}
	
	public int getMaxLevel()
	{
		return 3;
	}
	
	public boolean canApplyTogether(Enchantment e)
	{
		return !(e instanceof EnchantmentDeathMark);
	}
}