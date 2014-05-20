package minechem.item.chemistjournal;

import minechem.ModMinechem;
import minechem.gui.GuiTableOfElements;
import minechem.gui.GuiTab;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class ChemistJournalTab extends GuiTab
{
    public static Icon helpIcon;

    public ChemistJournalTab(Gui gui)
    {
        super(gui);

        this.currentShiftX = GuiTableOfElements.GUI_WIDTH - 411;
        this.currentShiftY = GuiTableOfElements.GUI_HEIGHT - 411;
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
        // TODO Auto-generated method stub
        return ModMinechem.ICON_HELP;
    }

}
