package fluxedCore.reference;

import net.minecraft.util.ResourceLocation;

public class Reference {

	public static final String MOD_ID = "fluxedcore";
	public static final String LOWERCASE_MOD_ID = MOD_ID.toLowerCase();
	public static final String MOD_NAME = "Fluxed-Core";
	public static final String VERSION = "2.0.1";
	public static final String CLIENT_PROXY = "fluxedCore.proxy.ClientProxy";
	public static final String SERVER_PROXY = "fluxedCore.proxy.ServerProxy";
	public static final String GUI_FACTORY_CLASS = "fluxedCore.client.gui.GuiFactory";
	public static final String DEPENDENCIES = "after:NotEnoughItems;after:Waila)";
	public static final ResourceLocation BUFF_LOCATION = new ResourceLocation(MOD_ID, "textures/buffs/buffs.png");
}
