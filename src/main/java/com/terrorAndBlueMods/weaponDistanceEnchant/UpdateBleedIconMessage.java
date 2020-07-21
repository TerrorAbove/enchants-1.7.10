package com.terrorAndBlueMods.weaponDistanceEnchant;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class UpdateBleedIconMessage implements IMessage
{
	private Bleed[] bleeds;
	
	public UpdateBleedIconMessage(Bleed... bleeds)
	{
		this.bleeds = bleeds;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		int numBleeds = buf.readInt();
		bleeds = new Bleed[numBleeds];
		
		for(int i = 0; i < bleeds.length; i++)
		{
			bleeds[i] = Bleed.fromBytes(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(bleeds.length);
		
		for(int i = 0; i < bleeds.length; i++)
		{
			bleeds[i].toBytes(buf);
		}
	}
	
	public UpdateBleedIconMessage()
	{
		bleeds = new Bleed[] {};
	}
	
	public static class Handler extends AbstractClientMessageHandler<UpdateBleedIconMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, UpdateBleedIconMessage message, MessageContext ctx)
		{
			//TODO graphics
			return null;
		}
		
	}
}
