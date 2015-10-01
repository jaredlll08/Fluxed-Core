package fluxedCore.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

	@Override
	public World getClientWorld() {
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
}
