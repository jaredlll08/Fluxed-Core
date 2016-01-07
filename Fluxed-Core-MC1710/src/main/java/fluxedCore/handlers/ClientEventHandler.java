package fluxedCore.handlers;

import java.awt.Color;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ClientEventHandler {

	private static Color color = new Color(255, 0, 0);

	@SubscribeEvent
	public void updateColor(TickEvent.ClientTickEvent e) {
		color = updateColours();
	}

	public Color updateColours() {
		int oldRed = color.getRed();
		int oldGreen = color.getGreen();
		int oldBlue = color.getBlue();
		int red = oldRed;
		int green = oldGreen;
		int blue = oldBlue;

		if (oldRed == 255 && oldBlue < 255 && oldGreen == 0) {
			blue += 5;
			if (blue > 255) {
				blue = 255;
			}
		} else if (oldBlue == 255 && oldRed > 0 && oldGreen == 0) {
			red -= 5;
			if (red < 0) {
				red = 0;
			}
		} else if (oldBlue == 255 && oldRed == 0 && oldGreen < 255) {
			green += 5;
			if (green > 255) {
				green = 255;
			}
		} else if (oldGreen == 255 && oldRed == 0 && oldBlue > 0) {
			blue -= 5;
			if (blue < 0) {
				blue = 0;
			}
		} else if (oldGreen == 255 && oldBlue == 0 && oldRed < 255) {
			red += 5;
			if (red > 255) {
				red = 255;
			}
		} else if (oldRed == 255 && oldGreen > 0 && oldBlue == 0) {
			green -= 5;
			if (green < 0) {
				green = 0;
			}
		}

		return new Color(red, green, blue);

	}

	public static Color getColor() {
		return color;
	}

	public static void setColor(Color color) {
		ClientEventHandler.color = color;
	}

	public static int getColorInt() {
		return color.getRGB();
	}
	
	public static float getRed(){
		return (float) color.getRed()/255;
	}
	
	public static float getGreen(){
		return (float) color.getGreen()/255;
	}
	public static float getBlue(){
		return (float) color.getBlue()/255;
	}

}
