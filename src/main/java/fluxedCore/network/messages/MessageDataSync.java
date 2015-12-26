package fluxedCore.network.messages;

import fluxedCore.FluxedCore;
import fluxedCore.entity.EntityData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDataSync implements IMessage, IMessageHandler<MessageDataSync, IMessage> {
	public MessageDataSync() {
	}

	private int entityID;
	private NBTTagCompound data;

	public MessageDataSync(EntityLivingBase target, NBTTagCompound tag) {
		this.entityID = target.getEntityId();
		this.data = tag;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		ByteBufUtils.writeTag(buf, data);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
		this.data = ByteBufUtils.readTag(buf);
	}

	@Override
	public IMessage onMessage(MessageDataSync message, MessageContext ctx) {
		World world = FluxedCore.proxy.getWorld();
		EntityLivingBase entity = (EntityLivingBase) world.getEntityByID(message.entityID);
		if (entity != null) {
			EntityData data = EntityData.getInstance(entity);
			data.loadNBTData(message.data);
		}
		return null;
	}
}
