package minechem.gui;

import minechem.Minechem;
import minechem.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiTabHelp extends GuiTab
{
    public static IIcon helpIcon;

    String helpString;
    int stringWidth;

    public GuiTabHelp(Gui gui, String helpString)
    {
        super(gui);
        this.helpString = helpString;
        this.maxWidth = 120;
        this.stringWidth = this.maxWidth - 10;
        this.maxHeight = MinechemHelper.getSplitStringHeight(fontRenderer, helpString, this.stringWidth) + 20;
        this.overlayColor = 0x88BBBB;
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
        fontRenderer.drawSplitString(helpString, x + 6, y + 10, this.stringWidth, 0x000000);
    }

    @Override
    public String getTooltip()
    {
        if (!isOpen())
        {
            return "Help";
        } else
        {
            return null;
        }
    }

    @Override
    public ResourceLocation getIcon()
    {
        // TODO Auto-generated method stub
        return Minechem.ICON_HELP;
    }

}
