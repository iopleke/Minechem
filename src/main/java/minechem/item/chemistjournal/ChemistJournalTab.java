package minechem.item.chemistjournal;

import minechem.Minechem;
import minechem.gui.GuiTab;
import minechem.gui.GuiTableOfElements;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class ChemistJournalTab extends GuiTab
{
	public static IIcon helpIcon;

	public ChemistJournalTab(Gui gui)
	{
		super(gui);

		this.currentX = GuiTableOfElements.GUI_WIDTH - 411;
		this.currentY = GuiTableOfElements.GUI_HEIGHT - 411;
		this.overlayColor = 0x2F7DAA;
	}

	@Override
	public void draw(int x, int y)
	{
		drawBackground(x, y);
		if (!isFullyOpened())
		{
			drawIcon(x + 2, y + 3);
			return;
		}

	}

	@Override
	public String getTooltip()
	{

		return "Journal";
	}

	@Override
	public ResourceLocation getIcon()
	{
		return Minechem.ICON_HELP;
	}

}
