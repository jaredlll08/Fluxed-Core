package fluxedCore.handlers;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import fluxedCore.buffs.Buff;
import fluxedCore.buffs.BuffEffect;
import fluxedCore.buffs.BuffHelper;
import fluxedCore.entity.EntityData;
import fluxedCore.network.PacketHandler;
import fluxedCore.network.messages.MessageDataSync;
import fluxedCore.reference.Reference;

public class EventHandler {
	public EventHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityLivingBase) {
			event.entity.registerExtendedProperties(Reference.MOD_ID, new EntityData());
		}
	}

	private static final ResourceLocation INVENTORY_TEXTURES = new ResourceLocation("textures/gui/container/inventory.png");

	@SubscribeEvent
	public void onOverlayRender(GuiScreenEvent.DrawScreenEvent event)
	{
		if (event.gui != null && event.gui instanceof InventoryEffectRenderer)
		{
			InventoryEffectRenderer gui = (InventoryEffectRenderer) event.gui;
			int x = gui.guiLeft - 124;
			int y = gui.guiTop;
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			Collection<BuffEffect> effects = BuffHelper.getEntityEffects(player);

			if (!effects.isEmpty())
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				int yOffset = 33;
				int totalSize = effects.size() + player.getActivePotionEffects().size();

				if (player.getActivePotionEffects().size() > 5)
				{
					yOffset = 132 / (totalSize - 1);
				}

				y += yOffset * (totalSize - 1);

				for (Iterator<BuffEffect> iterator = effects.iterator(); iterator.hasNext(); y += yOffset)
				{
					BuffEffect effect = iterator.next();
					Buff data = effect.getBuff();
					GL11.glColor4f(1, 1, 1, 1);
					Minecraft.getMinecraft().getTextureManager().bindTexture(INVENTORY_TEXTURES);
					gui.drawTexturedModalRect(x, y, 0, 166, 140, 32);

					if (data.getResourceInformation() != null)
					{
						Minecraft.getMinecraft().getTextureManager().bindTexture(data.getResourceInformation().getLocation());
						gui.drawTexturedModalRect(x + 6, y + 7, data.getResourceInformation().getUvPair().getX(), data.getResourceInformation().getUvPair().getY(), 18, 18);
					}
					Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
					String s = I18n.format(data.getUnlocalizedName(), new Object[0]);
					s = appendLevel(s, effect);

					FontRenderer fnt = Minecraft.getMinecraft().fontRenderer;
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

	public static String appendLevel(String s, BuffEffect data)
	{
		if (data.getPower() > 1)
		{
			s += " " + integerToRomanNumeral(data.getPower());
		}
		return s;
	}

	@SubscribeEvent
	public void onPlayerStartTracking(PlayerEvent.StartTracking event)
	{
		if (event.target instanceof EntityLivingBase)
		{
			syncDataFor((EntityLivingBase) event.target, (EntityPlayerMP) event.entityPlayer);
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		syncDataFor(event.player, (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void onPlayerChangeDimension(PlayerChangedDimensionEvent event)
	{
		syncDataFor(event.player, (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public void onPlayerSpawn(PlayerRespawnEvent event)
	{
		syncDataFor(event.player, (EntityPlayerMP) event.player);
	}

	public static void syncDataFor(EntityLivingBase entity, EntityPlayerMP to)
	{
		EntityData data = EntityData.getInstance(entity);
		if (data != null && data.getBuffs().tagCount() > 0)
		{
			NBTTagCompound tag = new NBTTagCompound();

			data.saveNBTData(tag);
			
			PacketHandler.INSTANCE.sendTo(new MessageDataSync(entity, tag), to);
		}
	}
}
