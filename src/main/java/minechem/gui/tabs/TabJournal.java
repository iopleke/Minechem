package minechem.gui.tabs;

import minechem.ModMinechem;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class TabJournal extends Tab
{
    public static Icon helpIcon;

    public TabJournal(Gui gui)
    {
        super(gui);
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
