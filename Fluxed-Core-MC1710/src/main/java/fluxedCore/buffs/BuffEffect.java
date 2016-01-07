package fluxedCore.buffs;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;

public class BuffEffect {

	private Buff buff;
	private int duration;
	private int power;

	/**
	 * @param buff
	 * @param duration
	 * @param power
	 */
	public BuffEffect(Buff buff, int duration, int power) {
		this.buff = buff;
		this.duration = duration;
		this.power = power;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("buff", buff.getUnlocalizedName());
		tag.setInteger("duration", getDuration());
		tag.setInteger("power", getPower());
		nbt.setTag("FCBuff", tag);
	}

	public static BuffEffect readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound tag = nbt.getCompoundTag("FCBuff");
		return new BuffEffect(BuffHelper.getBuffFromString(tag.getString("buff")), tag.getInteger("duration"), tag.getInteger("power"));
	}

	public void writeToByteBuf(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, buff.getUnlocalizedName());
		buf.writeInt(duration);
		buf.writeInt(power);
	}

	public static BuffEffect readFromByteBuf(ByteBuf buf) {
		return new BuffEffect(BuffHelper.getBuffFromString(ByteBufUtils.readUTF8String(buf)), buf.readInt(), buf.readInt());
	}

	public Buff getBuff() {
		return buff;
	}

	public int getDuration() {
		return duration;
	}

	public int getPower() {
		return power;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public String toString() {
		return "BuffEffect [buff=" + getBuff() + ", duration=" + getDuration() + ", power=" + getPower() + "]";
	}

}
