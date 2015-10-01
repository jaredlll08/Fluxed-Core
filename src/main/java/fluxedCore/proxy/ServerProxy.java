package fluxedCore.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class ServerProxy extends CommonProxy{

	@Override
	public World getClientWorld() {
		return null;
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
}
