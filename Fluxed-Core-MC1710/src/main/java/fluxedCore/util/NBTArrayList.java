package fluxedCore.util;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;

@SuppressWarnings("serial")
public class NBTArrayList<E> extends ArrayList<E> {

	public NBTTagList toNBTTagList() {
		NBTTagList list = new NBTTagList();
		Iterator it = iterator();
		while (it.hasNext()) {
			Object o = it.next();

		}
		return list;
	}

}
