package fluxedCore.entity;

import fluxedCore.reference.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class EntityData implements IExtendedEntityProperties {

	@SuppressWarnings("unused")
	private EntityLivingBase entity;
	private NBTTagList buffs;

	public static final String tagKnowledge = "FCBuffs";

	@Override
	public void saveNBTData(NBTTagCompound tag) {
		tag.setTag(tagKnowledge, buffs);
	}

	@Override
	public void loadNBTData(NBTTagCompound tag) {
		buffs = tag.getTagList(tagKnowledge, NBT.TAG_COMPOUND);
	}

	public static EntityData getInstance(EntityLivingBase entity) {
		return (EntityData) entity.getExtendedProperties(Reference.MOD_ID);
	}

	@Override
	public void init(Entity entity, World world) {

		if (entity instanceof EntityLivingBase) {
			this.entity = (EntityLivingBase) entity;
		}
	}

	public NBTTagList getBuffs() {
		if (buffs == null) {
			buffs = new NBTTagList();
		}
		return buffs;
	}

	public void setBuffs(NBTTagList buffs) {
		this.buffs = buffs;
	}

}
