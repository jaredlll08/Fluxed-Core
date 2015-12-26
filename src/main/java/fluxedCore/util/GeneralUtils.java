package fluxedCore.util;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.network.NetworkRegistry;
import fluxedCore.network.PacketHandler;
import fluxedCore.network.messages.MessageBiome;

public class GeneralUtils
{

	public static boolean isPlayerSpecial(String player)
	{

		return player.equalsIgnoreCase("Namroc_Smith") || player.toLowerCase().equalsIgnoreCase("jaredlll08");

	}

	public static boolean isPlayerPatron(String player) {
		return player.equalsIgnoreCase("belathus") || player.equalsIgnoreCase("celes218");
	}

	public static void setBiomeAt(World world, int x, int z, BiomeGenBase biome) {
		if (biome == null) {
			return;
		}
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		byte[] array = chunk.getBiomeArray();
		array[((z & 0xF) << 4 | x & 0xF)] = ((byte) (biome.biomeID & 0xFF));
		chunk.setBiomeArray(array);
		if (!world.isRemote) {
			PacketHandler.INSTANCE.sendToAllAround(new MessageBiome(x, z, biome.biomeID), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, world.getHeightValue(x, z), z, 32.0D));
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
