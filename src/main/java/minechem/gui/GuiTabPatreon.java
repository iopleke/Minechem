package minechem.gui;

import minechem.reference.Resources;
import minechem.utils.MinechemUtil;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author jakimfett
 */
public class GuiTabPatreon extends GuiTab
{
    String tabText[];
    int tabTextHeight[];
    String link;
    int stringWidth;
    String linkText;
    private int linkX;
    private int linkY;

    public GuiTabPatreon(GuiContainerTabbed gui)
    {
        super(gui);
        this.tabText = new String[2];
        this.tabTextHeight = new int[2];

        this.maxWidth = 120;
        this.stringWidth = this.maxWidth - 10;

        this.tabText[0] = MinechemUtil.getLocalString("tab.patreon.firstline");
        this.tabTextHeight[0] = MinechemUtil.getSplitStringHeight(fontRenderer, tabText[0], this.stringWidth);
        this.tabText[1] = MinechemUtil.getLocalString("tab.patreon.secondline");
        this.tabTextHeight[1] = MinechemUtil.getSplitStringHeight(fontRenderer, tabText[1], this.stringWidth);

        this.link = "http://jakimfett.com/patreon";
        this.linkText = MinechemUtil.getLocalString("tab.patreon.linktext");

        this.maxHeight = tabTextHeight[0] + tabTextHeight[1] + 35;
        this.overlayColor = 0xFFFFFF;
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

        fontRenderer.drawSplitString(tabText[0], x + 6, y + 10, this.stringWidth, 0x000000);
        fontRenderer.drawSplitString(tabText[1], x + 6, y + 15 + tabTextHeight[0], this.stringWidth, 0x000000);

        this.linkX = x + 6;
        this.linkY = y + 20 + tabTextHeight[0] + tabTextHeight[1];
        fontRenderer.drawSplitString(linkText, linkX, linkY, this.stringWidth, 0x0066CC);
    }

    @Override
    public String getTooltip()
    {
        if (!isOpen())
        {
            String localizedTooltip = MinechemUtil.getLocalString("tab.tooltip.patreon");
            if (localizedTooltip.equals("tab.tooltip.patreon") || localizedTooltip.isEmpty())
            {
                return "Support Minechem";
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

    public boolean isLinkAtOffsetPosition(int mouseX, int mouseY)
    {

        if (mouseX >= linkX)
        {
            if (mouseX <= linkX + fontRenderer.getStringWidth(linkText))
            {
                if (mouseY >= linkY)
                {
                    if (mouseY <= linkY + MinechemUtil.getSplitStringHeight(fontRenderer, linkText, fontRenderer.getStringWidth(linkText)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
