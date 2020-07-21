package com.terrorAndBlueMods.weaponDistanceEnchant;

public class BleedListCombining extends BleedList
{
	@Override
	public boolean add(Bleed bleed)
	{
		for(Bleed bl : this)
		{
			if(bl.getTimeLeft() > 0 && bl.getTimeLeft() <= bleed.getTimeLeft() && bl.getFrequency() == bleed.getFrequency())
			{
				bl.refresh(bleed);
				return true;
			}
		}
		
		return super.add(bleed);
	}
	
	@Override
	public void add(int index, Bleed bleed)
	{
		for(Bleed bl : this)
		{
			if(bl.getTimeLeft() > 0 && bl.getTimeLeft() <= bleed.getTimeLeft() && bl.getFrequency() == bleed.getFrequency())
			{
				bl.refresh(bleed);
				return;
			}
		}
		
		super.add(index, bleed);
	}
}
