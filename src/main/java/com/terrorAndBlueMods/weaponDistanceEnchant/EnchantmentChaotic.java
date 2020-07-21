package com.terrorAndBlueMods.weaponDistanceEnchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentChaotic extends Enchantment
{
	public EnchantmentChaotic(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.weapon);
		this.name = "chaotic";
	}
	
	public int getMinEnchantability(int level)
	{
		return 4;
	}
	
	public int getMaxEnchantability(int level)
	{
		return 10;
	}
	
	public int getMaxLevel()
	{
		return 1;
	}
	
	public boolean canApplyTogether(Enchantment e)
	{
		return true;
	}
}