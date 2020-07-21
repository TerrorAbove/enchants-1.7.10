package com.terrorAndBlueMods.weaponDistanceEnchant;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PlayerMineBlockMessage implements IMessage
{
	private int x, y, z, face, dimensionID;
	private long timeSent;
	
	public PlayerMineBlockMessage() {} //have to have this
	
	public PlayerMineBlockMessage(int x, int y, int z, int face, World w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.face = face;
		this.dimensionID = w.provider.dimensionId;
		this.timeSent = w.getTotalWorldTime();
	}
	
	@Override
	public void fromBytes(ByteBuf b)
	{
		x = b.readInt();
		y = b.readInt();
		z = b.readInt();
		face = b.readInt();
		dimensionID = b.readInt();
		timeSent = b.readLong();
	}

	@Override
	public void toBytes(ByteBuf b)
	{
		b.writeInt(x);
		b.writeInt(y);
		b.writeInt(z);
		b.writeInt(face);
		b.writeInt(dimensionID);
		b.writeLong(timeSent);
	}

	public static class Handler extends AbstractServerMessageHandler<PlayerMineBlockMessage>
	{
		@Override
		public IMessage handleServerMessage(EntityPlayer player, PlayerMineBlockMessage message, MessageContext ctx)
		{
			try
			{
				if(message.dimensionID != player.worldObj.provider.dimensionId)
					return null;
				
				Block block = player.worldObj.getBlock(message.x, message.y, message.z);

				if(block != null && block.getMaterial().isSolid())
				{
					if(player.worldObj.getTotalWorldTime() - message.timeSent > 50)//2.5 seconds delay
						return null;//happened too long ago
					
					ItemStack held = player.getCurrentEquippedItem();

					int level = held == null ? 0 : EnchantmentHelper.getEnchantmentLevel(ExtraEnchantsMain.reachingTool.effectId, held);
					
					float maxDistance = 5F + (float)Utils.getMaxExtraDistanceForReachLevel(level);
					
					//final distance check
					if(player.getDistance(message.x+0.5, message.y+0.5, message.z+0.5) > maxDistance + 0.5F)
						return null;
					
					//MinecraftForge.EVENT_BUS.post(new PlayerInteractEvent(player, Action.LEFT_CLICK_BLOCK, message.x, message.y, message.z, message.face, player.worldObj));
					//TODO fix, does nothing :D
				}
			}
			catch(Exception exc) {}
			
			return null;
		}
	}
}
