package fluxedCore.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class GuiObject {

	private double width;
	private double height;
	private int xPosition;
	private int yPosition;
	private Gui gui;
	protected int index;
	public Minecraft mc = Minecraft.getMinecraft();

	public GuiObject(int index, Gui gui, double width, double height, int xPosition, int yPosition) {
		this.width = width;
		this.height = height;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.gui = gui;
		this.index = index;
	}

	public abstract void renderBackground(Gui gui,  int mouseX, int mouseY);

	public abstract void renderForeground(Gui gui, int mouseX, int mouseY);

	public abstract void onClick(Gui gui, int mouseX, int mouseY);
	
	public abstract void onCollided(Gui gui, int mouseX, int mouseY);

	public boolean collidedWithMouse(int mouseX, int mouseY){
		boolean collided = false;
		if(mouseX>getXPosition() && mouseX < getXPosition()+getWidth()){
			if(mouseY>getYPosition() && mouseY < getYPosition()+getHeight()){
				collided = true;
			}	
		}
		
		return collided;
	}
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getXPosition() {
		return xPosition;
	}

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public Gui getGui() {
		return gui;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
