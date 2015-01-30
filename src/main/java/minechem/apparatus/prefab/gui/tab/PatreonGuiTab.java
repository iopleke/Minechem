package minechem.apparatus.prefab.gui.tab;

import static codechicken.lib.gui.GuiDraw.fontRenderer;
import minechem.Config;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.helper.ColourHelper;
import minechem.helper.LocalizationHelper;
import minechem.helper.StringHelper;

/**
 *
 * @author jakimfett
 */
public class PatreonGuiTab extends BasicGuiTab
{
    String link;
    String linkText;
    private int linkX;
    private int linkY;

    public static boolean enable = Config.enablePatreon;
    // TODO: @jakimfett what are all these unused default values?
    public static int defaultSide = 0;
    public static int defaultHeaderColor = 14797103;
    public static int defaultSubHeaderColor = 11186104;
    public static int defaultTextColor = 16777215;
    public static int defaultBackgroundColor = 5592405;

    public PatreonGuiTab(BasicGuiContainer gui)
    {
        super(gui, LocalizationHelper.getLocalString("tab.patreon.text"), 0);
        this.backgroundColor = ColourHelper.RGB(35, 45, 50);// TODO: Find a good color
        this.enabled = Config.enablePatreon;
        this.link = "http://jakimfett.com/patreon";
        this.linkText = LocalizationHelper.getLocalString("tab.patreon.linktext");
        this.tabTitle = "tab.patreon.headerText";
        this.tabTooltip = "tab.patreon.tooltip";
    }

    @Override
    public String getIcon()
    {
        return "patreon";
    }

    public boolean isLinkAtOffsetPosition(int mouseX, int mouseY)
    {

        if (mouseX >= linkX)
        {
            if (mouseX <= linkX + fontRenderer.getStringWidth(linkText))
            {
                if (mouseY >= linkY)
                {
                    if (mouseY <= linkY + StringHelper.getSplitStringHeight(fontRenderer, linkText, fontRenderer.getStringWidth(linkText)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
