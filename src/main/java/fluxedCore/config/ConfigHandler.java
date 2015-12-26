package fluxedCore.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static boolean showFlux = true;
	public static boolean showMana = true;
	public static List<String> blacklistedModIds = new ArrayList<String>();

	public static void preInit(File file) {

		Configuration config = new Configuration(file);
		config.load();
		showFlux = config.getBoolean("fluxBar", "CLIENT", showFlux, "Should show the Flux Bar.");
		showMana = config.getBoolean("manaBar", "CLIENT", showMana, "Should show the Mana Bar.");

		String[] blacklistedModId = new String[] { "tconstruct", "test" };
		blacklistedModId = config.getStringList("ModId Blacklist", "BLACKLIST", blacklistedModId,
				"Will ignore all items from this mod.");

		config.save();
	}

	public static boolean contains(String string) {
		for(String s : blacklistedModIds){
			if(s.startsWith(string)){
				return true;
			}
		}
		return false;
	}
}
