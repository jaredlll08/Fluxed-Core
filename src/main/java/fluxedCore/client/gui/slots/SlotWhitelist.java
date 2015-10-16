package fluxedCore.client.gui.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWhitelist extends Slot {

	public ItemStack[] valid;
	public SlotWhitelist(IInventory inv, int index, int x, int y, ItemStack... valid) {
		super(inv, index, x, y);
		this.valid = valid;
	}


	@Override
	public boolean isItemValid(ItemStack stack) {
		for(ItemStack val :valid){
			if(stack.isItemEqual(val)){
				return true;
			}
		}
		return false;
	}
	
}
