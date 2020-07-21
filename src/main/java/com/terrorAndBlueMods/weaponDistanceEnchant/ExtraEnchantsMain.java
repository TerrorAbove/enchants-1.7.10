package com.terrorAndBlueMods.weaponDistanceEnchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ExtraEnchantsMain.MODID,
name = ExtraEnchantsMain.NAME,
version = ExtraEnchantsMain.VERSION)
public class ExtraEnchantsMain
{
	public static final String MODID = "TerrorEnchants";
	public static final String NAME = "Terror's Extra Enchantments";
	public static final String VERSION = "1.0";
	
	public static final Enchantment reachingTool = new EnchantmentReachingTool(82, 4);
	public static final Enchantment reachingWeapon = new EnchantmentReachingWeapon(83, 4);
	public static final Enchantment collabMining = new EnchantmentCollabMining(84, 6);
	public static final Enchantment serrated = new EnchantmentSerrated(85, 5);
	public static final Enchantment deathMark = new EnchantmentDeathMark(86, 5);
	public static final Enchantment backstab = new EnchantmentBackstab(87, 4);
	public static final Enchantment chaotic = new EnchantmentChaotic(88, 1);

	@SidedProxy(clientSide="com.terrorAndBlueMods.weaponDistanceEnchant.ClientProxy", serverSide="com.terrorAndBlueMods.weaponDistanceEnchant.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		PacketDispatcher.registerPackets();

		event.getModMetadata().authorList.add("Terror_Above");
		event.getModMetadata().authorList.add("Bluesnake198");
		event.getModMetadata().description = "Terror's Extra Enchantments is a simple mod which adds a few new enchantments to the game, like \"Reaching\" and \"Collaborative\".";
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Enchantment.addToBookList(reachingTool);
		Enchantment.addToBookList(reachingWeapon);
		Enchantment.addToBookList(collabMining);
		Enchantment.addToBookList(serrated);
		Enchantment.addToBookList(deathMark);
		Enchantment.addToBookList(backstab);
		Enchantment.addToBookList(chaotic);

		MinecraftForge.EVENT_BUS.register(new HandleLivingEvent());
		MinecraftForge.EVENT_BUS.register(new HandleCollabMining());
		FMLCommonHandler.instance().bus().register(new HandlePlayerTick());
	}
}
