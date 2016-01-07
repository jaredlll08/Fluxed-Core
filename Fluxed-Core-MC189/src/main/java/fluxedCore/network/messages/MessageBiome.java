package fluxedCore.network.messages;

import fluxedCore.FluxedCore;
import fluxedCore.util.GeneralUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBiome implements IMessage, IMessageHandler<MessageBiome, IMessage> {
	private BlockPos pos;
	private int biome;

	public MessageBiome() {
	}

	public MessageBiome(BlockPos pos, int biome) {
		this.pos =pos;
		this.biome = biome;
	}

	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(pos.getX());
		buffer.writeInt(pos.getY());
		buffer.writeInt(pos.getZ());
		
		buffer.writeShort(this.biome);
	}

	public void fromBytes(ByteBuf buffer) {
		this.pos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
		this.biome = buffer.readShort();
	}

	public IMessage onMessage(MessageBiome message, MessageContext ctx) {
		GeneralUtils.setBiomeAt(FluxedCore.proxy.getWorld(), message.pos, BiomeGenBase.getBiome(message.biome));
		return null;
	}
}
