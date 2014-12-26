package minechem.item.chemistjournal;

import minechem.gui.GuiTab;
import minechem.gui.GuiTableOfElements;
import minechem.reference.Resources;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class ChemistJournalTab extends GuiTab
{
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
		return Resources.Icon.HELP;
	}

}
