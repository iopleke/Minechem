package ljdp.minechem.client.gui.tabs;

import ljdp.minechem.client.gui.GuiTableOfElements;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;

public class TabJournel extends Tab{
	public static Icon helpIcon;
	public TabJournel(Gui gui) {
		super(gui);
		
		this.currentShiftX = GuiTableOfElements.GUI_WIDTH - 411;
        this.currentShiftY = GuiTableOfElements.GUI_HEIGHT - 411;
        
		this.overlayColor = 0x88BBBB;// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(int x, int y) {
		 drawBackground(x, y);
	        if (!isFullyOpened()) {
	        	drawIcon(helpIcon, x + 2, y + 3);
	            return;
	        }
	        
	}

	@Override
	public String getTooltip() {
		
		return "Journal";
	}

}
