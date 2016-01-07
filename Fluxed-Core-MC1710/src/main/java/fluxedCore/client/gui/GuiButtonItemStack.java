package fluxedCore.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class GuiButtonItemStack extends GuiButton {

	private ItemStack stack;

	public GuiButtonItemStack(int index, int x, int y, ItemStack stack) {
		super(index, x, y, 18, 18, "");
		this.stack = stack;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GL11.glColor4d(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderItem.getInstance().renderWithColor = true;
		RenderHelper.disableStandardItemLighting();
		RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, getStack(), this.xPosition + 2, this.yPosition + 2);
		RenderItem.getInstance().renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, getStack(), this.xPosition + 2, this.yPosition + 2);
		RenderHelper.enableStandardItemLighting();
		GL11.glColor4d(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

}
