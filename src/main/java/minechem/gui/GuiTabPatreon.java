/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minechem.gui;

import minechem.reference.Resources;
import minechem.utils.MinechemUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jakimfett
 */
public class GuiTabPatreon extends GuiTab
{
    String helpString;
    String link;
    int stringWidth;
    String linkText;

    public GuiTabPatreon(Gui gui)
    {
        super(gui);
        this.helpString = MinechemUtil.getLocalString("tab.patreon");
        this.link = "http://www.patreon.com/jakimfett";
        this.linkText = "Patreon";
        this.maxWidth = 120;
        this.stringWidth = this.maxWidth - 10;
        this.maxHeight = MinechemUtil.getSplitStringHeight(fontRenderer, helpString, this.stringWidth) + 20;
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
            String localizedTooltip = MinechemUtil.getLocalString("tab.tooltip.patreon");
            if (localizedTooltip.equals("tab.tooltip.patreon") || localizedTooltip.isEmpty())
            {
                return "Supporting Minechem";
            } else
            {
                return localizedTooltip;
            }
        } else
        {
            return null;
        }
    }

    @Override
    public ResourceLocation getIcon()
    {
        return Resources.Icon.PATREON;
    }

    public boolean isLinkAtPosition(int mX, int mY)
    {

        int xShift = 0;
        int yShift = 4;

        if (this.intersectsWith(mX, mY, xShift, yShift))
        {
            return true;
        }
        return false;
    }

}
