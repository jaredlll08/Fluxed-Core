package fluxedCore;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fluxedCore.buffs.BuffHandler;
import fluxedCore.config.ConfigHandler;
import fluxedCore.items.FBItems;
import fluxedCore.network.PacketHandler;
import fluxedCore.proxy.IProxy;
import fluxedCore.reference.Reference;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, name = Reference.MOD_NAME)
public class FluxedCore {

	@SidedProxy(clientSide = "fluxedCore.proxy.ClientProxy", serverSide = "fluxedCore.proxy.ServerProxy")
	public static IProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		new BuffHandler();
		new fluxedCore.handlers.EventHandler();
		ConfigHandler.preInit(event.getSuggestedConfigurationFile());
		PacketHandler.init();
		proxy.registerEvents();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		FBItems.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
