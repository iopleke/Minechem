package pixlepix.minechem.client.gui.tabs;

import pixlepix.minechem.common.ModMinechem;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class TabTable extends Tab{
	public static Icon helpIcon;
	public TabTable(Gui gui) {
		super(gui);
	
		this.overlayColor = 0x2F7DAA;// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(int x, int y) {
		 drawBackground(x, y);
	        if (!isFullyOpened()) {
	            drawIcon(x + 2, y + 3);
	            return;
	        }
		
	}

	@Override
	public String getTooltip() {
		
		return "Table Of Elements";
	}

	@Override
	public ResourceLocation getIcon() {
		// TODO Auto-generated method stub
		return ModMinechem.ICON_HELP;
	}

}
