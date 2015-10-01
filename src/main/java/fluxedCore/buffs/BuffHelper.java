package fluxedCore.buffs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import fluxedCore.entity.EntityData;
import fluxedCore.network.PacketHandler;
import fluxedCore.network.messages.MessageBuffSync;
import fluxedCore.network.messages.MessageBuffUpdate;

public class BuffHelper {

	private static BiMap<String, Buff> buffMap = HashBiMap.create();

	public static void registerBuff(String name, Buff buff) {
		buffMap.put(name, buff);
	}

	public static Buff getBuffFromString(String name) {
		try {
			return buffMap.get(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<BuffEffect> getEntityEffects(EntityLivingBase entity) {
		List<BuffEffect> effects = new ArrayList<BuffEffect>();
		NBTTagList list = EntityData.getInstance(entity).getBuffs();
		if (list != null) {
			for (int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound tag = list.getCompoundTagAt(i);
				BuffEffect buff = BuffEffect.readFromNBT(tag);
				effects.add(buff);
			}
		}
		return effects;
	}

	public static BuffEffect getEntitybuff(EntityLivingBase entity, Buff buff) {
		if (hasBuff(entity, buff))
			for (BuffEffect effect : getEntityEffects(entity)) {
				if (effect.getBuff().equals(buff)) {
					return effect;
				}
			}

		return null;
	}

	public static boolean applyToEntity(World world, EntityLivingBase entity, BuffEffect effect) {
		if (entity != null) {
			NBTTagList list = EntityData.getInstance(entity).getBuffs();
			if (hasBuff(entity, effect.getBuff())) {
				int toRemove = -1;
				for (int i = 0; i < getEntityEffects(entity).size(); i++) {
					BuffEffect current = getEntityEffects(entity).get(i);
					if (current.getBuff().equals(effect.getBuff())) {
						toRemove = i;
					}
				}
				list.removeTag(toRemove);
			}
			NBTTagCompound buff = new NBTTagCompound();
			effect.writeToNBT(buff);
			list.appendTag(buff);
			if (!world.isRemote) {
				PacketHandler.INSTANCE.sendToAllAround(new MessageBuffSync(entity, effect), new TargetPoint(world.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 128D));
			}
			EntityData.getInstance(entity).setBuffs(list);
			return true;
		}
		return false;
	}

	public static boolean updateBuff(World world, EntityLivingBase entity, BuffEffect effect) {
		if (entity != null && hasBuff(entity, effect.getBuff())) {
			NBTTagList list = EntityData.getInstance(entity).getBuffs();
			int count = 0;
			boolean foundBuff = false;
			for (BuffEffect buffE : getEntityEffects(entity)) {
				if (!foundBuff) {
					if (!buffE.getBuff().equals(effect.getBuff())) {
						count++;
					} else {
						foundBuff = true;
					}

				}

			}
			NBTTagCompound buff = new NBTTagCompound();
			effect.writeToNBT(buff);
			if (!(effect.getDuration() <= 0)) {
				list.func_150304_a(count, buff);
			} else {
				list.removeTag(count);
			}
			if (!world.isRemote) {
				PacketHandler.INSTANCE.sendToAllAround(new MessageBuffUpdate(entity, effect), new TargetPoint(world.provider.dimensionId, entity.posX, entity.posY, entity.posZ, 128D));
			}
			EntityData.getInstance(entity).setBuffs(list);
		}
		return false;
	}

	public static BiMap<String, Buff> getBuffMap() {
		return buffMap;
	}

	public static boolean hasBuff(EntityLivingBase entity, Buff buff) {
		for (BuffEffect effect : getEntityEffects(entity)) {
			if (effect.getBuff().equals(buff)) {
				return true;
			}
		}

		return false;
	}

}
