package fluxedCore.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IProxy {

	public void preInit();

	public void init();

	public void postInit();

	public World getWorld();

	public EntityPlayer getClientPlayer();

	public boolean isServer();

	public boolean isClient();

	public void registerRenderers();

	public void registerGuis();
	
	public void registerEvents();
}
