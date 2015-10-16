package fluxedCore.handlers;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import vazkii.botania.api.mana.IManaItem;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fluxedCore.config.ConfigHandler;
import fluxedCore.reference.Reference;

public class GuiEventHandler {
	public GuiEventHandler() {

	}

	@SubscribeEvent
	public void renderGUI(RenderGameOverlayEvent.Text e) {
		if ((e.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) || (e.type == RenderGameOverlayEvent.ElementType.TEXT)) {
			EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() != null) {
				if (ConfigHandler.showFlux) {
					if (player.getCurrentEquippedItem().getItem() instanceof IEnergyContainerItem && ((IEnergyContainerItem) player.getCurrentEquippedItem().getItem()).getEnergyStored(player.getCurrentEquippedItem()) > 0) {
						drawEnergyInfo(e.resolution.getScaledWidth() - 51, e.resolution.getScaledHeight() - 8, player.getCurrentEquippedItem(), FMLClientHandler.instance().getClient().fontRenderer);
					}
				}

				if (ConfigHandler.showMana) {
					if (player.getCurrentEquippedItem().getItem() instanceof IManaItem && ((IManaItem) player.getCurrentEquippedItem().getItem()).getMana(player.getCurrentEquippedItem()) > 0) {
						drawManaInfo(e.resolution.getScaledWidth() - 51, e.resolution.getScaledHeight() - 8, player.getCurrentEquippedItem(), FMLClientHandler.instance().getClient().fontRenderer);
					}
				}
			}

		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		GuiScreen gui = mc.currentScreen;
		if (ConfigHandler.showFlux)
			if (gui instanceof GuiContainer && mc.thePlayer.inventory.getItemStack() == null) {
				GuiContainer container = (GuiContainer) gui;
				Point mouse = getMouse(container);
				final ScaledResolution scaledresolution = ((GuiIngameForge) mc.ingameGUI).getResolution();
				int i = scaledresolution.getScaledWidth();
				int j = scaledresolution.getScaledHeight();
				final int k = Mouse.getX() * i / mc.displayWidth;
				final int l = j - Mouse.getY() * j / mc.displayHeight - 1;

				GL11.glPushMatrix();
				GL11.glPushAttrib(1048575);
				GL11.glDisable(2896);
				List slots = mc.thePlayer.openContainer.inventorySlots;
				for (int o = 0; o < slots.size(); o++) {
					Slot slot = (Slot) slots.get(o);
					if (mouse.x >= slot.xDisplayPosition - 1 && mouse.x <= slot.xDisplayPosition + 16 && mouse.y >= slot.yDisplayPosition - 1 && mouse.y <= slot.yDisplayPosition + 16) {
						// Mouse is hovering over this slot
						ItemStack stack = slot.getStack();
						if (stack == null)
							continue;
						boolean isEnergy = stack.getItem() instanceof IEnergyContainerItem && ((IEnergyContainerItem) stack.getItem()).getEnergyStored(stack) > 0;
						boolean isMana = stack.getItem() instanceof IManaItem && ((IManaItem) stack.getItem()).getMana(stack) > 0;
						if (isEnergy) {
							ArrayList<String> tooltip = (ArrayList<String>) stack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);

							int y = tooltip.size() == 0 ? 0 : 2 + (10 * tooltip.size());
							drawEnergyInfo(gui, k, l + 5 + y, stack, mc.fontRenderer);
						}
						if (isMana) {
							ArrayList<String> tooltip = (ArrayList<String>) stack.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips);

							int y = tooltip.size() == 0 ? 0 : 2 + (10 * tooltip.size());
							drawManaInfo(gui, k, l + 5 + y, stack, mc.fontRenderer);
						}
					}
				}

				GL11.glPopAttrib();
				GL11.glPopMatrix();
			}
	}

	public static Point getMouse(GuiContainer container) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		Dimension size = new Dimension(res.getScaledWidth(), res.getScaledHeight());
		Dimension resolution = new Dimension(mc.displayWidth, mc.displayHeight);
		Point mousepos = new Point(Mouse.getX() * size.width / resolution.width, size.height - Mouse.getY() * size.height / resolution.height - 1);
		int guiLeft = (container.width - container.xSize) / 2;
		int guiTop = (container.height - container.ySize) / 2;
		Point relMouse = new Point(mousepos.x - guiLeft, mousepos.y - guiTop);
		return relMouse;
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public static void drawEnergyInfo(GuiScreen gui, int x, int y, ItemStack item, FontRenderer font) {
		IEnergyContainerItem energy = (IEnergyContainerItem) item.getItem();
		int k = 51;
		int i1 = x + 12;
		int j1 = y - 12;
		int k1 = 9;
		int defaultLength = 0;
		ArrayList<String> tooltip = (ArrayList<String>) item.getTooltip(FMLClientHandler.instance().getClient().thePlayer, FMLClientHandler.instance().getClient().gameSettings.advancedItemTooltips);
		Iterator<String> it = tooltip.iterator();

		while (it.hasNext()) {
			String s = (String) it.next();
			int l = font.getStringWidth(s);

			if (l > defaultLength)
			{
				defaultLength = l;
			}
		}
		if (i1 + defaultLength > gui.width) {
			i1 -= 60 + defaultLength;
		}

		if (j1 + k1 + 6 > gui.height) {
			j1 = gui.height - k1 - 6;
		}

		int l1 = -267386864;
		gui.zLevel = 500F;
		gui.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
		gui.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
		gui.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
		gui.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
		gui.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
		int i2 = 1347420415;
		int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		gui.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
		gui.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
		gui.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
		gui.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
		GL11.glColor4d(1, 1, 1, 1);
		gui.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/tooltip.png"));
		double energyStored = energy.getEnergyStored(item);
		double maxEnergy = energy.getMaxEnergyStored(item);
		int width = ((int) ((energyStored / maxEnergy) * 102) >> 1) + 2;
		gui.drawTexturedModalRect(i1, j1, 0, 0, 102, 8);
		if (energyStored > 0)
			gui.drawTexturedModalRect(i1, j1, 0, 8, width, 8);

		gui.zLevel = 0;
	}

	@SideOnly(Side.CLIENT)
	public static void drawEnergyInfo(int x, int y, ItemStack item, FontRenderer font) {
		IEnergyContainerItem energy = (IEnergyContainerItem) item.getItem();
		int i1 = x;
		int j1 = y;

		GL11.glColor4d(1, 1, 1, 1);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/tooltip.png"));
		double energyStored = energy.getEnergyStored(item);
		double maxEnergy = energy.getMaxEnergyStored(item);
		int width = ((int) ((energyStored / maxEnergy) * 102) >> 1) + 2;
		FMLClientHandler.instance().getClient().ingameGUI.drawTexturedModalRect(i1, j1, 0, 0, 102, 8);
		if (energyStored > 0)
			FMLClientHandler.instance().getClient().ingameGUI.drawTexturedModalRect(i1, j1, 0, 8, width, 8);

	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public static void drawManaInfo(GuiScreen gui, int x, int y, ItemStack item, FontRenderer font) {
		IManaItem energy = (IManaItem) item.getItem();
		int k = 51;
		int i1 = x + 12;
		int j1 = y - 12;
		int k1 = 9;
		int defaultLength = 0;
		ArrayList<String> tooltip = (ArrayList<String>) item.getTooltip(FMLClientHandler.instance().getClient().thePlayer, FMLClientHandler.instance().getClient().gameSettings.advancedItemTooltips);
		Iterator<String> it = tooltip.iterator();

		while (it.hasNext()) {
			String s = (String) it.next();
			int l = font.getStringWidth(s);

			if (l > defaultLength)
			{
				defaultLength = l;
			}
		}
		if (i1 + defaultLength > gui.width) {
			i1 -= 60 + defaultLength;
		}

		if (j1 + k1 + 6 > gui.height) {
			j1 = gui.height - k1 - 6;
		}

		int l1 = -267386864;
		gui.zLevel = 500F;
		gui.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
		gui.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
		gui.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
		gui.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
		gui.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
		int i2 = 1347420415;
		int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		gui.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
		gui.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
		gui.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
		gui.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
		GL11.glColor4d(1, 1, 1, 1);
		gui.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/tooltip.png"));
		double energyStored = energy.getMana(item);
		double maxEnergy = energy.getMaxMana(item);
		int width = ((int) ((energyStored / maxEnergy) * 102) >> 1) + 2;
		gui.drawTexturedModalRect(i1, j1, 0, 16, 102, 8);
		if (energyStored > 0)
			gui.drawTexturedModalRect(i1, j1, 0, 24, width, 8);

		gui.zLevel = 0;
	}

	@SideOnly(Side.CLIENT)
	public static void drawManaInfo(int x, int y, ItemStack item, FontRenderer font) {
		IManaItem mana = (IManaItem) item.getItem();
		int i1 = x;
		int j1 = y;

		GL11.glColor4d(1, 1, 1, 1);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/tooltip.png"));
		double energyStored = mana.getMana(item);
		double maxEnergy = mana.getMaxMana(item);
		int width = ((int) ((energyStored / maxEnergy) * 102) >> 1) + 2;
		FMLClientHandler.instance().getClient().ingameGUI.drawTexturedModalRect(i1, j1, 0, 16, 102, 8);
		if (energyStored > 0)
			FMLClientHandler.instance().getClient().ingameGUI.drawTexturedModalRect(i1, j1, 0, 24, width, 8);

	}
}
