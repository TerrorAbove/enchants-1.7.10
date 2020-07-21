package com.terrorAndBlueMods.weaponDistanceEnchant;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Utils
{
	public static EntityLivingBase getTarget(double distance)
	{
	    Minecraft mc = Minecraft.getMinecraft();

	    Entity pointedEntity;
	    double d0 = distance;
	    MovingObjectPosition omo = mc.renderViewEntity.rayTrace(d0, 1.0F);
	    double d1 = d0;
	    Vec3 vec3 = mc.renderViewEntity.getPosition(1.0F);
	    Vec3 vec31 = mc.renderViewEntity.getLook(1.0F);
	    Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
	    pointedEntity = null;
	    Vec3 vec33 = null;
	    float f1 = 1.0F;
	    List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
	    double d2 = d1;

	    for (int i = 0; i < list.size(); ++i)
	    {
	        Entity entity = (Entity)list.get(i);

	        if (entity.canBeCollidedWith())
	        {
	            float f2 = entity.getCollisionBorderSize();
	            AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
	            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

	            if (axisalignedbb.isVecInside(vec3))
	            {
	                if (0.0D < d2 || d2 == 0.0D)
	                {
	                    pointedEntity = entity;
	                    vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
	                    d2 = 0.0D;
	                }
	            }
	            else if (movingobjectposition != null)
	            {
	                double d3 = vec3.distanceTo(movingobjectposition.hitVec);

	                if (d3 < d2 || d2 == 0.0D)
	                {
	                    if (entity == mc.renderViewEntity.ridingEntity && !entity.canRiderInteract())
	                    {
	                        if (d2 == 0.0D)
	                        {
	                            pointedEntity = entity;
	                            vec33 = movingobjectposition.hitVec;
	                        }
	                    }
	                    else
	                    {
	                        pointedEntity = entity;
	                        vec33 = movingobjectposition.hitVec;
	                        d2 = d3;
	                    }
	                }
	            }
	        }
	    }
	    if (pointedEntity != null && (d2 < d1 || omo == null))
	    {
	        omo = new MovingObjectPosition(pointedEntity, vec33);

	        if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
	        {
	            mc.pointedEntity = pointedEntity;
	        }
	    }
	    if (omo != null)
	    {
	        if (omo.typeOfHit == MovingObjectType.ENTITY)
	        {
	            if(omo.entityHit instanceof EntityLivingBase)
	            {
	                return (EntityLivingBase)omo.entityHit;
	            }
	        }
	    }
	    return null;
	}
	
	public static double getMaxExtraDistanceForReachLevel(int level)//TODO relocate?
	{
		if(level <= 0)
			return 0;
		
		return 1.5 + level;
	}
	
	public static MovingObjectPosition getMovingObjectPositionFromPlayer(EntityPlayer player, double maxDistance, boolean liquids)
    {
        float var4 = 1.0F;
        float var5 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * var4;
        float var6 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * var4;
        double var7 = player.prevPosX + (player.posX - player.prevPosX) * (double)var4;
        double var9 = player.prevPosY + (player.posY - player.prevPosY) * (double)var4 + 1.62D - (double)player.yOffset;
        double var11 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)var4;
        Vec3 var13 = Vec3.createVectorHelper(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
        float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
        float var16 = -MathHelper.cos(-var5 * 0.017453292F);
        float var17 = MathHelper.sin(-var5 * 0.017453292F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        Vec3 var23 = var13.addVector((double)var18 * maxDistance, (double)var17 * maxDistance, (double)var20 * maxDistance);
        return player.worldObj.func_147447_a(var13, var23, liquids, !liquids, false);
    }
	
	public static void applyHitWithArmorPen(EntityLivingBase target, DamageSource source, float damage, float armorPenFactor, boolean ignoreHurtResistTimer)
	{
		//assumes damage > 0
		armorPenFactor = Math.max(0, Math.min(1, armorPenFactor));
		float regularHit = 1F - armorPenFactor;
		int prevHurtResTime = target.hurtResistantTime;
		if(ignoreHurtResistTimer)
			target.hurtResistantTime = 0;
		if(regularHit > 0)
			target.attackEntityFrom(source, damage * regularHit);
		if(ignoreHurtResistTimer)
			target.hurtResistantTime = 0;
		if(armorPenFactor > 0)
			target.attackEntityFrom(source.setDamageBypassesArmor(), damage * armorPenFactor);
		if(ignoreHurtResistTimer)
			target.hurtResistantTime = prevHurtResTime;
	}
}
