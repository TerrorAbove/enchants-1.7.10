package com.terrorAndBlueMods.weaponDistanceEnchant;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class BleedDeathMark extends Bleed//technically a Bleed, but it won't look like it
{
	private static final float HEALTH_SCALING_FACTOR = 2F;//up to double damage when target is low health
	private static final int DEATH_MARK_TIME = 120;
	
	private EntityLivingBase target;
	private int deathMarkLevel;
	
	public BleedDeathMark(float damage, EntityLivingBase target, int deathMarkLevel)
	{
		super(damage, DEATH_MARK_TIME, DEATH_MARK_TIME);//it will hit once, at the end
		this.target = target;
		this.deathMarkLevel = deathMarkLevel;
	}
	
	@Override
	public boolean refresh(Bleed other)
	{
		if(!(other instanceof BleedDeathMark))
			return false;
		
		BleedDeathMark bdm = (BleedDeathMark)other;
		
		if(bdm.deathMarkLevel != this.deathMarkLevel)
			return false;
		
		return super.refresh(other);
	}
	
	@Override
	public float calculateDamage()
	{
		float damage = 0;
		
		if(timeLeft > 0)
		{
			timeLeft--;
		}

		if(timeLeft <= 0)
			damage = damageRemaining;
		else
			return 0;
		
		damage = getModifiedDamage(damage);
		damageRemaining = 0;
		damageDealt += damage;
		
		return damage;
	}
	
	private float getModifiedDamage(float originalDamage)
	{
		if(target == null || target.isDead)
			return 0;
		
		//healthReduced is the amount of the target's max health we will subtract for calculations.
		//	It will be used to calculate a threshold of maximum damage.
		//	Damage will be at it's maximum when target is below a certain percent of health
		//	e.g., target has 7/20 health, but we will calculate it as 1/14.
		float healthReduced = target.getMaxHealth() * deathMarkLevel * 0.1F;
		float maxHp = target.getMaxHealth() - healthReduced;
		float hp = Math.max(0, target.getHealth() - healthReduced);
		
		float missingHealthRatio = 1F - hp / maxHp;
		
		return originalDamage * missingHealthRatio * HEALTH_SCALING_FACTOR;
	}
	
	/**
	 * 
	 * @return <code>true</code> if this specific death mark bleed is capable of killing the target, if dealt immediately. Note this may not take into account things like other mods' damage reducing effects.
	 */
	public boolean willKillTarget()
	{
		float damage = damageRemaining;
		
		if(target != null && target.isEntityAlive() && !target.isEntityInvulnerable())
		{
			damage = getModifiedDamage(damage);
		}
		else
		{
			return false;
		}
		
		if(target instanceof EntityPlayer && ((EntityPlayer)target).capabilities.isCreativeMode)
			return false;
		
		return damage >= target.getHealth();
	}
}
