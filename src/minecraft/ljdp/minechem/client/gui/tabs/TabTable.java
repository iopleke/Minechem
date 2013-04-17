package ljdp.minechem.client.gui.tabs;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;

public class TabTable extends Tab{
	public static Icon helpIcon;
	public TabTable(Gui gui) {
		super(gui);
	
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
		
		return "Table Of Elements";
	}

}
