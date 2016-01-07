package fluxedCore.util;

import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;


public class CoordinatePair {
	
	private final int x;
	private final int y;

	/**
	 * @param x
	 * @param y
	 */
	public CoordinatePair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void writeToByteBuf(ByteBuf buf){
		buf.writeInt(x);
		buf.writeInt(y);
	}
	
	public static CoordinatePair readFromByteBuf(ByteBuf buf){
		return new CoordinatePair(buf.readInt(), buf.readInt());
	}
	
	public void writeToNBT(NBTTagCompound tag){
		tag.setInteger("x", x);
		tag.setInteger("y", y);
	}
	
	public static CoordinatePair readFromNBT(NBTTagCompound tag){
		return new CoordinatePair(tag.getInteger("x"), tag.getInteger("y"));
	}
}
