package fluxedCore.handlers;

import java.util.ArrayList;
import java.util.List;

import fluxedCore.client.gui.GuiButtonItemStack;

public class Page {
	public int index;
	public List<GuiButtonItemStack> items = new ArrayList<GuiButtonItemStack>();

	public Page(int index, List<GuiButtonItemStack> items) {
		this.index = index;
		this.items = items;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<GuiButtonItemStack> getItems() {
		return items;
	}

	public void setItems(List<GuiButtonItemStack> items) {
		this.items = items;
	}

}
