package fluxedCore.buffs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import fluxedCore.network.PacketHandler;
import fluxedCore.network.messages.MessageBuffSync;
import fluxedCore.network.messages.MessageBuffUpdate;

public class BuffHandler {

	public BuffHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void handleBuffEntity(LivingUpdateEvent e) {
		updatebuffs(e.entityLiving);
	}

	public void updatebuffs(EntityLivingBase entity) {
		if (!entity.worldObj.isRemote) {
			for (int i = 0; i < BuffHelper.getEntityEffects(entity).size(); i++) {
				BuffEffect buff = BuffHelper.getEntityEffects(entity).get(i);
				if(buff !=null && buff.getBuff()!=null){
					if (buff.getBuff().canUpdate())
						buff.getBuff().onBuffTick(entity.worldObj, entity, buff.getDuration(), buff.getPower());
					buff.setDuration(buff.getDuration() - 1);
					if (buff.getDuration() <= 0) {
						BuffHelper.getEntityEffects(entity).remove(i);
					}
					BuffHelper.updateBuff(entity.worldObj, entity, buff);
					PacketHandler.INSTANCE.sendToAllAround(new MessageBuffUpdate(entity, buff), new TargetPoint(entity.worldObj.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 128D));
				}
			}
		}
	}

	@SubscribeEvent
	public void login(PlayerEvent.PlayerLoggedInEvent e) {
		if (!e.player.worldObj.isRemote) {
			for (BuffEffect buff : BuffHelper.getEntityEffects(e.player)) {
				PacketHandler.INSTANCE.sendToAllAround(new MessageBuffSync(e.player, buff), new TargetPoint(e.player.worldObj.provider.dimensionId, e.player.posX, e.player.posY, e.player.posZ, 128D));
			}
		}
	}

}
