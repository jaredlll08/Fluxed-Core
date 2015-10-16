package fluxedCore.client.gui.objects;


import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import fluxedCore.client.gui.GuiObject;
import fluxedCore.util.RenderUtils;

public class GuiObjectItemButton extends GuiObject {

	private ItemStack stack;

	public GuiObjectItemButton(int index, Gui gui, double width, double height, int xPosition, int yPosition, ItemStack stack) {
		super(index, gui, width, height, xPosition, yPosition);
		this.stack = stack;
	}

	@Override
	public void renderBackground(Gui gui, int mouseX, int mouseY) {
//		RenderUtils.drawRect(getXPosition(), getYPosition(), getWidth(), getYPosition() +getHeight(), 0, 0.8f, 0.8f, 0.3f);
		RenderUtils.drawSquare(getXPosition(), getYPosition(), getWidth(), 0, 1f, 1f, 2f);
	}

	@Override
	public void renderForeground(Gui gui, int mouseX, int mouseY) {
		RenderHelper.disableStandardItemLighting();
		RenderItem.getInstance().renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack, getXPosition()+2, getYPosition()+2);
		RenderHelper.enableStandardItemLighting();
		
	}

	@Override
	public void onClick(Gui gui, int mouseX, int mouseY) {
		mc.thePlayer.inventory.setInventorySlotContents(2, stack);
	}

	@Override
	public void onCollided(Gui gui, int mouseX, int mouseY) {
	}

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}
	
	

}
