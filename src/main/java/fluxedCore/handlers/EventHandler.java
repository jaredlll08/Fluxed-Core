package fluxedCore.handlers;

import fluxedCore.entity.EntityData;
import fluxedCore.network.PacketHandler;
import fluxedCore.network.messages.MessageDataSync;
import fluxedCore.reference.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class EventHandler {
	public EventHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityLivingBase) {
			event.entity.registerExtendedProperties(Reference.MOD_ID, new EntityData());
		}
	}

	@SubscribeEvent
	public void onPlayerStartTracking(PlayerEvent.StartTracking event)
	{
		if (event.target instanceof EntityLivingBase)
		{
			syncDataFor((EntityLivingBase) event.target, (EntityPlayerMP) event.entityPlayer);
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		syncDataFor(event.player, (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void onPlayerChangeDimension(PlayerChangedDimensionEvent event)
	{
		syncDataFor(event.player, (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void onPlayerSpawn(PlayerRespawnEvent event)
	{
		syncDataFor(event.player, (EntityPlayerMP) event.player);
	}

	public static void syncDataFor(EntityLivingBase entity, EntityPlayerMP to)
	{
		EntityData data = EntityData.getInstance(entity);
		if (data != null && data.getBuffs().tagCount() > 0)
		{
			NBTTagCompound tag = new NBTTagCompound();

			data.saveNBTData(tag);
			
			PacketHandler.INSTANCE.sendTo(new MessageDataSync(entity, tag), to);
		}
	}
}
