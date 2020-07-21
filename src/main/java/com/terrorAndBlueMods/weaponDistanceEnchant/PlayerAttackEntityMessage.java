package com.terrorAndBlueMods.weaponDistanceEnchant;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PlayerAttackEntityMessage implements IMessage
{
	private int entityID;
	private int dimensionID;
	private long timeSent;
	
	public PlayerAttackEntityMessage() {} //have to have this
	
	public PlayerAttackEntityMessage(Entity entity)
	{
		entityID = entity.getEntityId();
		dimensionID = entity.worldObj.provider.dimensionId;
		timeSent = entity.worldObj.getTotalWorldTime();
	}
	
	@Override
	public void fromBytes(ByteBuf arg0)
	{
		entityID = arg0.readInt();
		dimensionID = arg0.readInt();
		timeSent = arg0.readLong();
	}

	@Override
	public void toBytes(ByteBuf arg0)
	{
		arg0.writeInt(entityID);
		arg0.writeInt(dimensionID);
		arg0.writeLong(timeSent);
	}

	public static class Handler extends AbstractServerMessageHandler<PlayerAttackEntityMessage>
	{
		// the fruits of our labor: we immediately know from the method name that we are handling
		// a message on the server side, and we have our EntityPlayer right there ready for use. Awesome.
		@Override
		public IMessage handleServerMessage(EntityPlayer player, PlayerAttackEntityMessage message, MessageContext ctx)
		{
			try
			{
				if(message.dimensionID != player.worldObj.provider.dimensionId)
					return null;
				
				Entity target = player.worldObj.getEntityByID(message.entityID);

				if(target != null)
				{
					if(target instanceof EntityPlayer && !player.canAttackPlayer((EntityPlayer)target))
						return null;
					
					if(player.worldObj.getTotalWorldTime() - message.timeSent > 50)//2.5 seconds delay
						return null;//happened too long ago
					
					ItemStack weapon = player.getCurrentEquippedItem();

					int level = weapon == null ? 0 : EnchantmentHelper.getEnchantmentLevel(ExtraEnchantsMain.reachingWeapon.effectId, weapon);
					
					float maxDistance = player.capabilities.isCreativeMode ? 6.0F : 4.5F + (float)Utils.getMaxExtraDistanceForReachLevel(level);
					
					if(player.getDistanceToEntity(target) > maxDistance + 0.5F)
						return null;
					
					player.attackTargetEntityWithCurrentItem(target);
				}
			}
			catch(Exception exc) {}
			
			return null;
		}
	}
}
