package fluxedCore.util;

import fluxedCore.network.PacketHandler;
import fluxedCore.network.messages.MessageBiome;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GeneralUtils
{

	public static boolean isPlayerSpecial(String player)
	{

		return player.equalsIgnoreCase("Namroc_Smith") || player.toLowerCase().equalsIgnoreCase("jaredlll08");

	}

	public static boolean isPlayerPatron(String player) {
		return player.equalsIgnoreCase("belathus") || player.equalsIgnoreCase("celes218");
	}

	public static void setBiomeAt(World world,BlockPos pos, BiomeGenBase biome) {
		if (biome == null) {
			return;
		}
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		byte[] array = chunk.getBiomeArray();
		array[((pos.getZ() & 0xF) << 4 | pos.getX() & 0xF)] = ((byte) (biome.biomeID & 0xFF));
		chunk.setBiomeArray(array);
		if (!world.isRemote) {
			PacketHandler.INSTANCE.sendToAllAround(new MessageBiome(pos, biome.biomeID), new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), pos.getX(), pos.getY(), pos.getZ(), 32.0D));
		}
	}


	public static class ArrayUtils
	{
		public static boolean contains(int[] array, int number) {
			for (int i : array) {
				if (i == number) {
					return true;
				}
			}
			return true;
		}
	}

}
