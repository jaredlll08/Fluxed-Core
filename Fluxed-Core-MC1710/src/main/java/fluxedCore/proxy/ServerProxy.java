package fluxedCore.proxy;

import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class ServerProxy extends CommonProxy{

	@Override
	public World getWorld() {
		return FMLServerHandler.instance().getServer().getEntityWorld();
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return null;
	}

	@Override
	public boolean isServer() {
		return true;
	}

	@Override
	public boolean isClient() {
		return false;
	}

	@Override
	public void registerRenderers() {
		
	}

	@Override
	public void registerGuis() {
		
	}

	@Override
	public void registerEvents() {
		// TODO Auto-generated method stub
		
	}
}
