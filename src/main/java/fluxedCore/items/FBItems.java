package fluxedCore.items;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.registry.GameRegistry;
import fluxedCore.config.ConfigHandler;
import fluxedCore.reference.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemTool;

public class FBItems {
	public static void init() {
		List<ItemTool> picks = new ArrayList<ItemTool>();
		List<ItemTool> shovels = new ArrayList<ItemTool>();
		List<ItemTool> axes = new ArrayList<ItemTool>();
		for (Object o : Item.itemRegistry.getKeys()) {
			Item i = (Item) Item.itemRegistry.getObject((String) o);
			if (!ConfigHandler.contains((String) o) && i instanceof ItemTool && !(i instanceof IEnergyContainerItem)) {
				if ((i instanceof ItemPickaxe || ((String) o).toLowerCase().contains("pickaxe"))) {
					picks.add((ItemTool) i);
				}
				if ((i instanceof ItemSpade || ((String) o).toLowerCase().contains("shovel"))) {
					shovels.add((ItemTool) i);
				}
				if ((i instanceof ItemAxe || (!((String) o).toLowerCase().contains("pickaxe") && ((String) o).toLowerCase().contains("axe")))) {
					axes.add((ItemTool) i);
				}
			}
		}
		for (ItemTool pic : picks) {
			ItemEnergyTool pick = new ItemEnergyTool(pic, Reference.MOD_ID + ":energyPickaxeOverlay");
			String unlocalized = pic.getUnlocalizedName();
			if (pic.getUnlocalizedName().contains(":")) {
				System.out.println(1 + pic.getUnlocalizedName());
				unlocalized = pic.getUnlocalizedName().split(":")[1];
				System.out.println(2 + unlocalized);
			}
			pick.setUnlocalizedName("energy" + unlocalized);
			GameRegistry.registerItem(pick, "energy" + unlocalized);
		}
		for (ItemTool pic : shovels) {
			ItemEnergyTool pick = new ItemEnergyTool(pic, Reference.MOD_ID + ":energyShovelOverlay");
			String unlocalized = pic.getUnlocalizedName();
			if (pic.getUnlocalizedName().contains(":")) {
				unlocalized = pic.getUnlocalizedName().split(":")[1];
			}
			pick.setUnlocalizedName("energy" + unlocalized);
			GameRegistry.registerItem(pick, "energy" + unlocalized);
		}
		for (ItemTool pic : axes) {
			ItemEnergyTool pick = new ItemEnergyTool(pic,Reference.MOD_ID +  ":energyAxeOverlay");
			String unlocalized = pic.getUnlocalizedName();
			if (pic.getUnlocalizedName().contains(":")) {
				unlocalized = pic.getUnlocalizedName().split(":")[1];
			}
			pick.setUnlocalizedName("energy" + unlocalized);
			GameRegistry.registerItem(pick, "energy" + unlocalized);
		}
	}
}
