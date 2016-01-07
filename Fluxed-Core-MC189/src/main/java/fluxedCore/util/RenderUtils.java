//package fluxedCore.util;
//
//import java.util.Random;
//
//import org.lwjgl.opengl.GL11;
//
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.util.MathHelper;
//import net.minecraftforge.fml.client.FMLClientHandler;
//
//public class RenderUtils {
//
//	public static void drawLine(double x, double y, double x2, double y2, float red, float green, float blue, float lineWidth) {
//
//		Tessellator var12 = Tessellator.getInstance();
//		GL11.glPushMatrix();
//		GL11.glLineWidth(lineWidth);
//		GL11.glDisable(3553);
//		var12.startDrawing(3);
//
//		var12.setColorRGBA_F(red, green, blue, 1);
//		var12.addVertex(x, y, 0.0D);
//		var12.addVertex(x2, y2, 0.0D);
//		var12.draw();
//		GL11.glBlendFunc(770, 771);
//		GL11.glDisable(32826);
//		GL11.glEnable(3553);
//		GL11.glPopMatrix();
//	}
//
//	public static void drawLine(double x, double y, double z, double x2, double y2, double z2, float red, float green, float blue, float lineWidth) {
//
//		Tessellator var12 = Tessellator.instance;
//		GL11.glPushMatrix();
//		GL11.glLineWidth(lineWidth);
//		GL11.glDisable(3553);
//		var12.startDrawing(3);
//
//		var12.setColorRGBA_F(red, green, blue, 1);
//		var12.addVertex(x, y, z);
//		var12.addVertex(x2, y2, z2);
//		var12.draw();
//		GL11.glBlendFunc(770, 771);
//		GL11.glDisable(32826);
//		GL11.glEnable(3553);
//		GL11.glPopMatrix();
//	}
//
//	public static void drawFace(double x, double y, double z, double x2, double y2, double z2, float red, float green, float blue, float lineWidth) {
//
//		Tessellator var12 = Tessellator.instance;
//		GL11.glPushMatrix();
//		GL11.glLineWidth(lineWidth);
//		GL11.glDisable(3553);
//		var12.startDrawing(3);
//
//		var12.setColorRGBA_F(red, green, blue, 1);
//		var12.addVertex(x, y, z);
//		var12.addVertex(x2, y, z2);
//		var12.addVertex(x2, y2, z2);
//		var12.addVertex(x, y2, z2);
//
//		// var12.addVertex(x2, y2, z2);
//		var12.draw();
//		GL11.glBlendFunc(770, 771);
//		GL11.glDisable(32826);
//		GL11.glEnable(3553);
//		GL11.glPopMatrix();
//	}
//
//	public static void drawLine(double x, double y, double x2, double y2, float red, float green, float blue, float lineWidth, float fadeSpeed) {
//		int count = FMLClientHandler.instance().getClient().thePlayer.ticksExisted;
//		float alpha = fadeSpeed + MathHelper.sin((float) (count + x)) * 0.3F + 0.3F;
//
//		Tessellator var12 = Tessellator.instance;
//		GL11.glPushMatrix();
//		GL11.glLineWidth(lineWidth);
//		GL11.glDisable(3553);
//		GL11.glEnable(3042);
//		var12.startDrawing(3);
//
//		var12.setColorRGBA_F(red, green, blue, alpha);
//		var12.addVertex(x, y, 0.0D);
//		var12.addVertex(x2, y2, 0.0D);
//
//		var12.draw();
//		GL11.glBlendFunc(770, 771);
//		GL11.glDisable(32826);
//		GL11.glDisable(3042);
//		GL11.glEnable(3553);
//		GL11.glPopMatrix();
//	}
//
//	public static void drawLine(double x, double y, double z, double x2, double y2, double z2, float red, float green, float blue, float lineWidth, float fadeSpeed) {
//		int count = FMLClientHandler.instance().getClient().thePlayer.ticksExisted;
//		float alpha = 0.3F + MathHelper.sin((float) (count + x)) * 0.3F + 0.3F;
//
//		Tessellator var12 = Tessellator.instance;
//		GL11.glPushMatrix();
//		GL11.glLineWidth(lineWidth);
//		GL11.glDisable(3553);
//		GL11.glEnable(3042);
//		var12.startDrawing(3);
//
//		var12.setColorRGBA_F(red, green, blue, alpha);
//		var12.setBrightness(0xFFFFFF);
//		var12.addVertex(x, y, z);
//		var12.addVertex(x2, y2, z2);
//
//		var12.draw();
//		GL11.glBlendFunc(770, 771);
//		GL11.glDisable(32826);
//		GL11.glDisable(3042);
//		GL11.glEnable(3553);
//		GL11.glPopMatrix();
//	}
//
//	public static void drawRect(double x, double y, double width, double height, float red, float green, float blue, float lineWidth) {
//		drawLine(x, y, x + width, y, red, green, blue, lineWidth);
//		drawLine(x + width, y, x + width, y + width, red, green, blue, lineWidth);
//		drawLine(x + width, y + width, x, y + width, red, green, blue, lineWidth);
//		drawLine(x, y + width, x, y, red, green, blue, lineWidth);
//	}
//
//	public static void drawRect(double x, double y, double width, double height, float red, float green, float blue, float lineWidth, float fadeSpeed) {
//		drawLine(x, y, x + width, y, red, green, blue, lineWidth, fadeSpeed);
//		drawLine(x + width, y, x + width, y + width, red, green, blue, lineWidth, fadeSpeed);
//		drawLine(x + width, y + width, x, y + width, red, green, blue, lineWidth, fadeSpeed);
//		drawLine(x, y + width, x, y, red, green, blue, lineWidth, fadeSpeed);
//	}
//
//	public static void drawRect(double x, double y, double z, double width, double height, float red, float green, float blue, float lineWidth) {
//		drawLine(x, y, z, x + width, y, z, red, green, blue, lineWidth);
//		drawLine(x + width, y, z, x + width, y + width, z, red, green, blue, lineWidth);
//		drawLine(x + width, y + width, z, x, y + width, z, red, green, blue, lineWidth);
//		drawLine(x, y + width, z, x, y, z, red, green, blue, lineWidth);
//	}
//
//	public static void drawSquare(double x, double y, double size, float red, float green, float blue, float lineWidth) {
//		drawRect(x, y, size, size, red, green, blue, lineWidth);
//	}
//
//	public static void drawLine(double x, double y, double x2, double y2, float r, float g, float b, float te, boolean wiggle) {
//		float count = FMLClientHandler.instance().getClient().thePlayer.ticksExisted + new Random().nextFloat() + te;
//
//		Tessellator var12 = Tessellator.instance;
//
//		GL11.glPushMatrix();
//		GL11.glAlphaFunc(516, 0.003921569F);
//		GL11.glDisable(3553);
//		GL11.glEnable(3042);
//		GL11.glBlendFunc(770, 771);
//
//		double d3 = x - x2;
//		double d4 = y - y2;
//		float dist = MathHelper.sqrt_double(d3 * d3 + d4 * d4);
//		int inc = (int) (dist / 2.0F);
//		float dx = (float) (d3 / inc);
//		float dy = (float) (d4 / inc);
//		if (Math.abs(d3) > Math.abs(d4)) {
//			dx *= 2.0F;
//		} else {
//			dy *= 2.0F;
//		}
//		GL11.glLineWidth(3.0F);
//		GL11.glEnable(2848);
//		GL11.glHint(3154, 4354);
//
//		var12.startDrawing(3);
//		for (int a = 0; a <= inc; a++) {
//			float r2 = r;
//			float g2 = g;
//			float b2 = b;
//			float mx = 0.0F;
//			float my = 0.0F;
//			float op = 0.6F;
//			if (wiggle) {
//				float phase = a / inc;
//				mx = MathHelper.sin((count + a) / 7.0F) * 5.0F * (1.0F - phase);
//				my = MathHelper.sin((count + a) / 5.0F) * 5.0F * (1.0F - phase);
//				r2 *= (1.0F - phase);
//				g2 *= (1.0F - phase);
//				b2 *= (1.0F - phase);
//				op *= phase;
//			}
//			var12.setColorRGBA_F(r2, g2, b2, op);
//
//			var12.addVertex(x - dx * a + mx, y - dy * a + my, 0.0D);
//			if (Math.abs(d3) > Math.abs(d4)) {
//				dx *= (1.0F - 1.0F / (inc * 3.0F / 2.0F));
//			} else {
//				dy *= (1.0F - 1.0F / (inc * 3.0F / 2.0F));
//			}
//		}
//		var12.draw();
//
//		GL11.glBlendFunc(770, 771);
//		GL11.glDisable(2848);
//		GL11.glDisable(3042);
//		GL11.glDisable(32826);
//		GL11.glEnable(3553);
//		GL11.glAlphaFunc(516, 0.1F);
//		GL11.glPopMatrix();
//	}
//
//}
