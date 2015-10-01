package fluxedCore.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fluxedCore.buffs.BuffEffect;
import fluxedCore.buffs.BuffHelper;

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
		EntityLivingBase entity = (EntityLivingBase) FMLClientHandler.instance().getClient().theWorld.getEntityByID(message.entityID);
		BuffHelper.applyToEntity(FMLClientHandler.instance().getClient().theWorld, entity, message.effect);
		return null;
	}

}
