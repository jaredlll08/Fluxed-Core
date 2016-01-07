package fluxedCore.network;

import fluxedCore.network.messages.MessageBiome;
import fluxedCore.network.messages.MessageBuffSync;
import fluxedCore.network.messages.MessageBuffUpdate;
import fluxedCore.network.messages.MessageDataSync;
import fluxedCore.network.messages.MessageSyncBuffs;
import fluxedCore.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
	public static int id = 0;

	public static void init() {
		
		
		INSTANCE.registerMessage(MessageBiome.class, MessageBiome.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageBuffSync.class, MessageBuffSync.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageSyncBuffs.class, MessageSyncBuffs.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageBuffUpdate.class, MessageBuffUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageDataSync.class, MessageDataSync.class, id++, Side.CLIENT);
	}

}
