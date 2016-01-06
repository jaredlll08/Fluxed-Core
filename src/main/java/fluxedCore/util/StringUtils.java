package fluxedCore.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class StringUtils {

	
	public static String format(ItemStack stack, EntityPlayer player, String unformated){
		String formated = unformated;
		if (formated.contains("{itemNameDisplay}")) {
			formated = formated.replaceAll("{itemNameDisplay}", stack.getDisplayName());
		}
		if (formated.contains("{itemNameUnlocalized}")) {
			formated = formated.replaceAll("{itemNameUnlocalized}", stack.getUnlocalizedName());
		}
		if (formated.contains("{itemStackSize}")) {
			formated = formated.replaceAll("{itemStackSize}", String.valueOf(stack.stackSize));
		}
		if (formated.contains("{itemStackSizeMax}")) {
			formated = formated.replaceAll("{itemStackSizeMax}", String.valueOf(stack.getMaxStackSize()));
		}
		if (formated.contains("{itemDamage}")) {
			formated = formated.replaceAll("{itemDamage}", String.valueOf(stack.getItemDamage()));
		}
		if (formated.contains("{playerX}")) {
			formated = formated.replace("{playerX}", String.valueOf(player.posX));
		}
		if (formated.contains("{playerY}")) {
			formated = formated.replace("{playerY}", String.valueOf(player.posY));
		}
		if (formated.contains("{playerZ}")) {
			formated = formated.replace("{playerZ}", String.valueOf(player.posZ));
		}
		if (formated.contains("{playerDimension}")) {
			formated = formated.replace("{playerDimension}", String.valueOf(player.dimension));
		}
		if (formated.contains("{playerHealth}")) {
			formated = formated.replace("{playerHealth}", String.valueOf(player.getHealth()));
		}
		if (formated.contains("{playerScore}")) {
			formated = formated.replace("{playerScore}", String.valueOf(player.getScore()));
		}
		if (formated.contains("{playerArmor}")) {
			formated = formated.replace("{playerArmor}", String.valueOf(player.getTotalArmorValue()));
		}
		if (formated.contains("{playerExperience}")) {
			formated = formated.replace("{playerExperience}", String.valueOf(player.experienceTotal));
		}
		if (formated.contains("{playerAir}")) {
			formated = formated.replace("{playerAir}", String.valueOf(player.getAir()));
		}
		if (formated.contains("{playerName}")) {
			formated = formated.replace("{playerName}", String.valueOf(player.getName()));
		}
		return formated;
	}
	public static String capitalizeFirst(String s){
		return s.toUpperCase().charAt(0) + s.toLowerCase().substring(1);
	}
}
