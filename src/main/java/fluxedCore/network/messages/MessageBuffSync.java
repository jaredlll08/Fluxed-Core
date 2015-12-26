package fluxedCore.network.messages;

import fluxedCore.FluxedCore;
import fluxedCore.buffs.BuffEffect;
import fluxedCore.buffs.BuffHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBuffSync implements IMessage, IMessageHandler<MessageBuffSync, IMessage> {

	public int entityID;
	public BuffEffect effect;

	public MessageBuffSync() {

	}

	public MessageBuffSync(EntityLivingBase entity, BuffEffect effect) {
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
	public IMessage onMessage(MessageBuffSync message, MessageContext ctx) {
		EntityLivingBase entity = (EntityLivingBase) FluxedCore.proxy.getWorld().getEntityByID(message.entityID);
		BuffHelper.applyToEntity(FluxedCore.proxy.getWorld(), entity, message.effect);
		return null;
	}

}
