package fluxedCore.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fluxedCore.FluxedCore;
import fluxedCore.buffs.BuffEffect;
import fluxedCore.buffs.BuffHelper;

public class MessageBuffUpdate implements IMessage, IMessageHandler<MessageBuffUpdate, IMessage> {

	public int entityID;
	public BuffEffect effect;

	public MessageBuffUpdate() {

	}

	public MessageBuffUpdate(EntityLivingBase entity, BuffEffect effect) {
		this.entityID = entity.getEntityId();
		this.effect = effect;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		effect = BuffEffect.readFromByteBuf(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		effect.writeToByteBuf(buf);
	}

	@Override
	public IMessage onMessage(MessageBuffUpdate message, MessageContext ctx) {
		EntityLivingBase entity = (EntityLivingBase) FluxedCore.proxy.getWorld().getEntityByID(message.entityID);
		BuffHelper.updateBuff(FluxedCore.proxy.getWorld(), entity, message.effect);
		return null;
	}

}
