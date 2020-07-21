package com.terrorAndBlueMods.weaponDistanceEnchant;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class HandlePlayerTick
{
	@SubscribeEvent
	public void playerUpdate(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;

		if(player.worldObj.isRemote && event.phase == Phase.START)
		{
			ItemStack held = player.getCurrentEquippedItem();

			int reachWeapon = held == null ? 0 : EnchantmentHelper.getEnchantmentLevel(ExtraEnchantsMain.reachingWeapon.effectId, held);
			int reachTool = held == null ? 0 : EnchantmentHelper.getEnchantmentLevel(ExtraEnchantsMain.reachingTool.effectId, held);

			if (reachWeapon > 0 && player.isSwingInProgress && player.swingProgress < 0.125)
			{
				player.swingProgress = 0.125F;

				double maxExtraDistance = Utils.getMaxExtraDistanceForReachLevel(reachWeapon);
				double baseDistance = player.capabilities.isCreativeMode ? 6.0 : 4.5;

				EntityLivingBase target = Utils.getTarget(baseDistance + maxExtraDistance);

				if(!(target instanceof EntityPlayer) || player.canAttackPlayer((EntityPlayer)target))
				{
					if(target != null && player.getDistanceToEntity(target) > baseDistance)
					{
						PacketDispatcher.sendToServer(new PlayerAttackEntityMessage(target));
						return;//if we're attacking something and also have reachTool, lets not do both lol
						//though with the swing progress check, this shouldn't be necessary
					}
				}
			}
			
			if (reachTool > 0 && player.isSwingInProgress && player.swingProgress < 0.125)
			{
				player.swingProgress = 0.125F;

				double maxExtraDistance = Utils.getMaxExtraDistanceForReachLevel(reachTool);
				double baseDistance = 5;//always 5 for mining blocks

				MovingObjectPosition mop = Utils.getMovingObjectPositionFromPlayer(player, baseDistance + maxExtraDistance, false);
				
				if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
				{
					if(mop.hitVec != null && mop.hitVec.distanceTo(Vec3.createVectorHelper(player.posX, player.posY, player.posZ)) > baseDistance)
					{
						PacketDispatcher.sendToServer(new PlayerMineBlockMessage(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, player.worldObj));
					}
				}
			}
		}
	}
}
