package com.terrorAndBlueMods.weaponDistanceEnchant;

import java.util.ArrayList;
import java.util.Iterator;

public class BleedList extends ArrayList<Bleed>
{
	public float getTotalSerratedDamageThisTick()
	{
		float damage = 0;
		
		Iterator<Bleed> iter = this.iterator();
		
		while(iter.hasNext())
		{
			Bleed bleed = iter.next();
			
			if(!(bleed instanceof BleedSerrated))
				continue;
			
			if(bleed != null && bleed.getTimeLeft() > 0)
			{
				damage += bleed.calculateDamage();
			}
			else
			{
				iter.remove();
			}
		}
		
		return damage;
	}
	
	public float getTotalDeathmarkDamageThisTick()
	{
		float damage = 0;
		
		Iterator<Bleed> iter = this.iterator();
		
		while(iter.hasNext())
		{
			Bleed bleed = iter.next();
			
			if(!(bleed instanceof BleedDeathMark))
				continue;
			
			if(bleed != null && bleed.getTimeLeft() > 0)
			{
				damage += bleed.calculateDamage();
			}
			else
			{
				iter.remove();
			}
		}
		
		return damage;
	}
}
