package fluxedCore.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static boolean showFlux = true;
	public static boolean showMana = true;

	public static void preInit(File file) {

		Configuration config = new Configuration(file);
		config.load();
		showFlux = config.getBoolean("fluxBar", "CLIENT", showFlux, "Should show the Flux Bar.");
		showFlux = config.getBoolean("manaBar", "CLIENT", showMana, "Should show the Mana Bar.");

		config.save();
	}
}
