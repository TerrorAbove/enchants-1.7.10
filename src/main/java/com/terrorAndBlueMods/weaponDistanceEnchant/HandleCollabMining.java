package com.terrorAndBlueMods.weaponDistanceEnchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class HandleCollabMining
{
	@SubscribeEvent
	public void handleBreakSpeed(BreakSpeed event)
	{
		if(event.entityPlayer != null)
		{
			ItemStack held = event.entityPlayer.getHeldItem();
			
			if(held != null)
			{
				int level = EnchantmentHelper.getEnchantmentLevel(ExtraEnchantsMain.collabMining.effectId, held);
				
				if(level > 0)
				{
					final double x = event.entityPlayer.posX;
					final double y = event.entityPlayer.posY;
					final double z = event.entityPlayer.posZ;
					
					double h_radius = 16;
					double v_radius = 8;
					
					List<Entity> list = event.entityPlayer.worldObj.getEntitiesWithinAABBExcludingEntity(event.entityPlayer,
							AxisAlignedBB.getBoundingBox(x - h_radius, y - v_radius, z - h_radius, x + h_radius, y + v_radius, z + h_radius));
					
					int count = 0;
					
					for(Entity ent : list)
					{
						if(ent instanceof EntityPlayer)
						{
							EntityPlayer pl = (EntityPlayer)ent;
							
							if(pl.getDistanceToEntity(event.entityPlayer) <= h_radius)
							{
								ItemStack held2 = pl.getHeldItem();
								
								if(held2 != null)
								{
									int level2 = EnchantmentHelper.getEnchantmentLevel(ExtraEnchantsMain.collabMining.effectId, held2);
									
									if(level2 > 0)
										count++;
								}
							}
						}
					}
					
					event.newSpeed *= 1 + ((0.2 + (0.1 * level)) * Math.min(5, count));
				}
			}
			
			
			/*if(event.entityPlayer.worldObj.isRemote)
			{
				float speed = event.entityPlayer.getEntityData().getFloat(ExtraEnchantsMain.MODID+"_collabMiningSpeed");
				long time = event.entityPlayer.getEntityData().getLong(ExtraEnchantsMain.MODID+"_collabMiningTime");
				
				if(speed > 0 && System.currentTimeMillis() - time <= 30000)
				{
					event.newSpeed = speed;//TODO fix sync issue, and only works for 1 person
				}
			}
			else
			{
				ItemStack held = event.entityPlayer.getCurrentEquippedItem();
				
				if(held != null)
				{
					if(EnchantmentHelper.getEnchantmentLevel(ExtraEnchantsMain.collabMining.effectId, held) > 0)
					{
						COLLAB_OWNERS.put(event.entityPlayer, System.currentTimeMillis());
					}
				}
				
				for(EntityPlayer pl : COLLAB_OWNERS.keySet())
				{
					if(pl != null && pl != event.entityPlayer && !pl.isDead && pl.worldObj != null && pl.worldObj.provider.dimensionId == event.entityPlayer.worldObj.provider.dimensionId)
					{
						double xzdist = Math.sqrt(Math.pow(pl.posX - event.entityPlayer.posX, 2) + Math.pow(pl.posZ - event.entityPlayer.posZ, 2));
						
						//checks if we are close enough AND the owner has attempted to mine something within 30 seconds
						if(xzdist <= 64 && Math.abs(pl.posY - event.entityPlayer.posY) <= 16 && System.currentTimeMillis() - COLLAB_OWNERS.getOrDefault(pl, 0L) <= 30000)
						{
							event.newSpeed *= (2 - xzdist/80);//at max distance of 64, speed is only increased by 20%.
							event.entityPlayer.getEntityData().setFloat(ExtraEnchantsMain.MODID+"_collabMiningSpeed", event.newSpeed);
							event.entityPlayer.getEntityData().setLong(ExtraEnchantsMain.MODID+"_collabMiningTime", COLLAB_OWNERS.getOrDefault(pl, 0L));
							break;//stop here so that a player can only benefit from a single enchantment
						}
					}
				}
			}*/
		}
	}
	
	/*private static class MiningPlayer
	{
		private EntityPlayer player;
		private long started;
		
		public MiningPlayer(EntityPlayer player, long started)
		{
			this.player = player;
			this.started = started;
		}
		
		public long timeElapsed()
		{
			return System.currentTimeMillis() - started;
		}
		
		@Override
		public boolean equals(Object o)
		{
			if(!(o instanceof MiningPlayer))
				return false;
			
			EntityPlayer other = ((MiningPlayer)o).player;
			
			if(player == null && other == null)
				return true;
			
			if(player == null || other == null)
				return false;
			
			return player.isEntityEqual(other);
		}
		
		public int getCollabLevel()
		{
			if(player == null || player.getHeldItem() == null)
				return 0;
			
			return EnchantmentHelper.getEnchantmentLevel(ExtraEnchantsMain.collabMining.effectId, player.getHeldItem());
		}
	}*/
}
