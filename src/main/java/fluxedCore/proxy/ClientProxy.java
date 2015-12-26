package fluxedCore.proxy;

import fluxedCore.handlers.ClientEventHandler;
import fluxedCore.handlers.GuiEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public World getWorld() {
		return Minecraft.getMinecraft().theWorld;
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public boolean isServer() {
		return false;
	}

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public void registerRenderers() {
	}

	@Override
	public void registerGuis() {
	}

	@Override
	public void registerEvents() {
		FMLCommonHandler.instance().bus().register(new GuiEventHandler());
		MinecraftForge.EVENT_BUS.register(new GuiEventHandler());		
		
		FMLCommonHandler.instance().bus().register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());	
	}
}
