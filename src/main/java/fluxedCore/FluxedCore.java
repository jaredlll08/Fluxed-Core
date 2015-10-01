package fluxedCore;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fluxedCore.buffs.BuffHandler;
import fluxedCore.buffs.BuffHelper;
import fluxedCore.buffs.BuffTest;
import fluxedCore.handlers.GuiEventHandler;
import fluxedCore.network.PacketHandler;
import fluxedCore.proxy.IProxy;
import fluxedCore.reference.Reference;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS, name = Reference.MOD_NAME)
public class FluxedCore {

	@SidedProxy(clientSide = "fluxedCore.proxy.ClientProxy", serverSide = "fluxedCore.proxy.ServerProxy")
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		new BuffHandler();
		new fluxedCore.handlers.EventHandler();
		BuffHelper.registerBuff("Test", new BuffTest());
		FMLCommonHandler.instance().bus().register(new GuiEventHandler());
		MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PacketHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
}
