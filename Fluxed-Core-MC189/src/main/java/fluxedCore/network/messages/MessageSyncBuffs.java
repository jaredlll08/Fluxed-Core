package fluxedCore.network.messages;

import java.util.ArrayList;
import java.util.List;

import fluxedCore.FluxedCore;
import fluxedCore.buffs.BuffEffect;
import fluxedCore.buffs.BuffHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncBuffs implements IMessage, IMessageHandler<MessageSyncBuffs, IMessage> {

	public int entityID;
	public List<BuffEffect> effect;

	public MessageSyncBuffs() {

	}

	public MessageSyncBuffs(EntityLivingBase entity, List<BuffEffect> effect) {
		this.entityID = entity.getEntityId();
		this.effect = effect;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		int effectSize = buf.readInt();
		effect = new ArrayList<BuffEffect>();
		for (int i = 0; i < effectSize; i++) {
			effect.add(BuffEffect.readFromByteBuf(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(effect.size());
		for (BuffEffect buff : effect) {
			buff.writeToByteBuf(buf);
		}
	}

	@Override
	public IMessage onMessage(MessageSyncBuffs message, MessageContext ctx) {
		EntityLivingBase entity = (EntityLivingBase) FluxedCore.proxy.getWorld().getEntityByID(message.entityID);
		for(BuffEffect buff : message.effect){
			BuffHelper.applyToEntity(FluxedCore.proxy.getWorld(), entity, buff);
		}
		return null;
	}

}
