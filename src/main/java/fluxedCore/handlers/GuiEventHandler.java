package fluxedCore.handlers;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import fluxedCore.buffs.Buff;
import fluxedCore.buffs.BuffEffect;
import fluxedCore.buffs.BuffHelper;
import fluxedCore.config.ConfigHandler;
import fluxedCore.reference.Reference;
import fluxedCore.util.GeneralUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiEventHandler {
	public GuiEventHandler() {

	}

	@SubscribeEvent
	public void chat(ClientChatReceivedEvent e) {
		if (e.message.getFormattedText().toLowerCase().contains("jared") || e.message.getFormattedText().toLowerCase().contains("jaredlll08")) {
			EnumChatFormatting[] colours = new EnumChatFormatting[] { EnumChatFormatting.AQUA, EnumChatFormatting.BLACK, EnumChatFormatting.BLUE, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.DARK_BLUE, EnumChatFormatting.DARK_GRAY, EnumChatFormatting.DARK_GREEN, EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.DARK_RED, EnumChatFormatting.GRAY, EnumChatFormatting.GOLD, EnumChatFormatting.GREEN, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.RED, EnumChatFormatting.YELLOW };
			int rand = new Random().nextInt(colours.length);
			String message = e.message.getFormattedText().toLowerCase().replace("jaredlll08", colours[rand] + "Jared" + EnumChatFormatting.RESET).replace("jared", colours[rand] + "Jared" + EnumChatFormatting.RESET);
			e.message = new ChatComponentText(message);
		}
		// if (e.displayname.equalsIgnoreCase("Jaredlll08")) {
		//
		//
		// } else if (e.displayname.equalsIgnoreCase("namroc_smith")) {
		// e.displayname = EnumChatFormatting.RED + "Namroc" +
		// EnumChatFormatting.RESET;
		// }
	}

	private boolean resetRender;
	private float trans = 0;
	private boolean descending;

	private float red, green, blue;

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void render(RenderLivingEvent.Pre event) {
		String s = EnumChatFormatting.getTextWithoutFormattingCodes(event.entity.getName());
		if (GeneralUtils.isPlayerSpecial(s) || GeneralUtils.isPlayerPatron(s)) {
			if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				if (new Random().nextInt(2) == 0) {
					if (!descending) {
						trans++;
					} else {
						trans--;
					}
					if (trans > 100) {
						descending = true;
						trans = 100f;
					}
					if (trans < 0) {
						descending = false;
						trans = 0;
					}
				}

			} else {
				red = ClientEventHandler.getRed();
				green = ClientEventHandler.getGreen();
				blue = ClientEventHandler.getBlue();
				if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
					if (new Random().nextInt(2) == 0) {
						if (!descending) {
							trans++;
						} else {
							trans--;
						}
						if (trans > 100) {
							descending = true;
							trans = 100f;
						}
						if (trans < 0) {
							descending = false;
							trans = 0;
						}
					}
					GL11.glColor4f(red, green, blue, trans / 100);
				} else {
					GL11.glColor4f(red, green, blue, 1);
				}

			}
			if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
				GL11.glColor4f(1, 1, 1, trans / 100);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			resetRender = true;
		}

	}

	@SubscribeEvent
	public void entityColorRender(RenderLivingEvent.Post event) {
		if (this.resetRender) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(3042);
			resetRender = false;
		}
	}

	public int currentPage = 0;
	public int prevPage = 0;
	List<Page> pages = new LinkedList<Page>();

	public static Point getMouse(GuiContainer container) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		ScaledResolution res = new ScaledResolution(mc);
		Dimension size = new Dimension(res.getScaledWidth(), res.getScaledHeight());
		Dimension resolution = new Dimension(mc.displayWidth, mc.displayHeight);
		Point mousepos = new Point(Mouse.getX() * size.width / resolution.width, size.height - Mouse.getY() * size.height / resolution.height - 1);
		int guiLeft = (container.width - container.xSize) / 2;
		int guiTop = (container.height - container.ySize) / 2;
		Point relMouse = new Point(mousepos.x - guiLeft, mousepos.y - guiTop);
		return relMouse;
	}

	private static final ResourceLocation INVENTORY_TEXTURES = new ResourceLocation("textures/gui/container/inventory.png");

	@SubscribeEvent
	public void onOverlayRender(GuiScreenEvent.DrawScreenEvent event) {
		if (event.gui != null && event.gui instanceof InventoryEffectRenderer) {
			InventoryEffectRenderer gui = (InventoryEffectRenderer) event.gui;
			int x = gui.guiLeft - 124;
			int y = gui.guiTop;
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			Collection<BuffEffect> effects = BuffHelper.getEntityEffects(player);

			if (!effects.isEmpty()) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				int yOffset = 33;
				int totalSize = effects.size() + player.getActivePotionEffects().size();

				if (player.getActivePotionEffects().size() > 5) {
					yOffset = 132 / (totalSize - 1);
				}

				y += yOffset * (totalSize - 1);

				for (Iterator<BuffEffect> iterator = effects.iterator(); iterator.hasNext(); y += yOffset) {
					BuffEffect effect = iterator.next();
					Buff data = effect.getBuff();
					GL11.glColor4f(1, 1, 1, 1);
					Minecraft.getMinecraft().getTextureManager().bindTexture(INVENTORY_TEXTURES);
					gui.drawTexturedModalRect(x, y, 0, 166, 140, 32);

					if (data.getResourceInformation() != null) {
						GL11.glEnable(GL11.GL_BLEND);
						Minecraft.getMinecraft().getTextureManager().bindTexture(data.getResourceInformation().getLocation());
						gui.drawTexturedModalRect(x + 6, y + 7, data.getResourceInformation().getUvPair().getX(), data.getResourceInformation().getUvPair().getY(), 18, 18);
						GL11.glDisable(GL11.GL_BLEND);
					}
					Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
					String s = I18n.format(data.getUnlocalizedName(), new Object[0]);
					s = appendLevel(s, effect);

					FontRenderer fnt = Minecraft.getMinecraft().fontRendererObj;
					fnt.drawStringWithShadow(s, x + 10 + 18, y + 6, 16777215);
					fnt.drawStringWithShadow(StringUtils.ticksToElapsedTime(effect.getDuration()), x + 10 + 18, y + 6 + 10, 8355711);
				}
			}
		}
	}

	public static String integerToRomanNumeral(int input) {
		if (input < 1 || input > 3999)
			throw new IndexOutOfBoundsException(input + " is not a valid Roman Numeral.");
		String s = "";
		while (input >= 1000) {
			s += "M";
			input -= 1000;
		}
		while (input >= 900) {
			s += "CM";
			input -= 900;
		}
		while (input >= 500) {
			s += "D";
			input -= 500;
		}
		while (input >= 400) {
			s += "CD";
			input -= 400;
		}
		while (input >= 100) {
			s += "C";
			input -= 100;
		}
		while (input >= 90) {
			s += "XC";
			input -= 90;
		}
		while (input >= 50) {
			s += "L";
			input -= 50;
		}
		while (input >= 40) {
			s += "XL";
			input -= 40;
		}
		while (input >= 10) {
			s += "X";
			input -= 10;
		}
		while (input >= 9) {
			s += "IX";
			input -= 9;
		}
		while (input >= 5) {
			s += "V";
			input -= 5;
		}
		while (input >= 4) {
			s += "IV";
			input -= 4;
		}
		while (input >= 1) {
			s += "I";
			input -= 1;
		}
		return s;
	}

	public static String appendLevel(String s, BuffEffect data) {
		if (data.getPower() > 1) {
			s += " " + integerToRomanNumeral(data.getPower());
		}
		return s;
	}

}
