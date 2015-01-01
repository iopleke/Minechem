package minechem.gui;

import minechem.reference.Resources;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class GuiTabTable extends GuiTab
{
    public GuiTabTable(Gui gui)
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
        }

    }

    @Override
    public String getTooltip()
    {

        return "Table Of Elements";
    }

    @Override
    public ResourceLocation getIcon()
    {
        return Resources.Icon.HELP;
    }

}
