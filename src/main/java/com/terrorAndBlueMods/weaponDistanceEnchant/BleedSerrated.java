package com.terrorAndBlueMods.weaponDistanceEnchant;

import net.minecraft.entity.Entity;

public class BleedSerrated extends Bleed
{
	protected Location location;
	protected Entity target;
	protected int serratedLevel;
	
	protected boolean moved;
	
	public BleedSerrated(float damage, int time, int frequency, Location loc, Entity target, int serratedLevel)
	{
		super(damage, time, frequency);
		this.location = loc;
		this.target = target;
		this.serratedLevel = serratedLevel;
		this.moved = false;
	}
	
	@Override
	public float calculateDamage()
	{
		float damage = super.calculateDamage();
		
		if(damage == 0)
			return damage;
		
		float movedMultiplier = 1F;
		
		if(targetMoved(2F))//target moved more than two blocks from where bleed was inflicted
		{
			movedMultiplier += 0.5F * (serratedLevel+1);//2.0, 2.5, 3.0 based on serrated level
		}
		
		return movedMultiplier * damage;
	}
	
	public boolean targetMoved(float threshold)
	{
		if(moved)
			return true;
		
		if(target == null || target.isDead || location == null)
			return false;
			
		if(location.dim == target.worldObj.provider.dimensionId && location.distance(new Location(target.posX, target.posY, target.posZ, target.worldObj.provider.dimensionId)) < threshold)
			return false;
		
		moved = true;
		return true;
	}
	
	@Override
	public boolean refresh(Bleed other)
	{
		if(!(other instanceof BleedSerrated))
			return false;
		
		BleedSerrated o = (BleedSerrated)other;
		
		if(o.serratedLevel != this.serratedLevel)
			return false;
		if(o.target == null || o.target.isDead)
			return false;
		
		location = o.location;
		target = o.target;
		moved = o.moved;
		return super.refresh(other);
	}
}
