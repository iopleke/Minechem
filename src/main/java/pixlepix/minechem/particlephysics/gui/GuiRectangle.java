package pixlepix.particlephysics.common.gui;

import java.util.Arrays;

public class GuiRectangle {
	public int x;
	public int y;
	public int w;
	public int h;
	
	public GuiRectangle(int x, int y, int w, int h){
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
	}
	
	public void setX(int x){
		this.x=x;
	}
	public void setY(int t){
		this.y=y;
	}
	
	public boolean inRect(GuiEmitter gui, int mouseX, int mouseY){
		mouseX -= gui.getLeft();
		mouseY -= gui.getTop();
		return mouseX >= x && mouseX <= x+w&& mouseY>=y&&mouseY <= y + w;
		
	}
	
	public void drawString(GuiEmitter gui, int mouseX, int mouseY, String str){
		if(inRect(gui, mouseX, mouseY)){
			gui.drawHoverString(Arrays.asList(str.split("\n")), mouseX-gui.getLeft(), mouseY-gui.getTop());
		}
	}
	
	public void draw(GuiEmitter gui, int srcX, int srcY){
		gui.drawTexturedModalRect(gui.getLeft()+x, gui.getTop()+y, srcX, srcY, w, h);
	}
}
