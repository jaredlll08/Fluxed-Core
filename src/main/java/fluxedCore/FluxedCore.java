package fluxedCore;

import fluxedCore.buffs.BuffHandler;
import fluxedCore.config.ConfigHandler;
import fluxedCore.network.PacketHandler;
import fluxedCore.proxy.IProxy;
import fluxedCore.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
