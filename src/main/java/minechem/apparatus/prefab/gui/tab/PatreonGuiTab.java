package minechem.apparatus.prefab.gui.tab;

import static codechicken.lib.gui.GuiDraw.fontRenderer;
import java.util.Arrays;
import java.util.List;
import minechem.Config;
import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.helper.ColourHelper;
import minechem.helper.LocalizationHelper;
import minechem.helper.StringHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PatreonGuiTab extends BasicGuiTab
{
    public static int defaultBackgroundColor = 5592405;
    public static int defaultHeaderColor = 14797103;
    public static int defaultSide = 0;
    public static int defaultSubHeaderColor = 11186104;
    public static int defaultTextColor = 16777215;
    public static boolean enable = Config.enablePatreon;
    private String link;
    private int linkColor;
    private String linkText;
    private List<String> linkTextList;

    public PatreonGuiTab(BasicGuiContainer gui)
    {
        super(gui, LocalizationHelper.getLocalString("tab.patreon.text"), 0);
        this.backgroundColor = ColourHelper.CYAN;// I like cyan.
        this.enabled = Config.enablePatreon;
        this.link = "http://jakimfett.com/patreon";
        this.linkText = LocalizationHelper.getLocalString("tab.patreon.linkText");
        this.tabTitle = "tab.patreon.headerText";
        this.tabTooltip = "tab.patreon.tooltip";
        this.linkColor = ColourHelper.YELLOW;
        this.linkTextList = Arrays.asList(link);
    }

    @Override
    public void draw()
    {
        super.draw();
        if (isEnabled())
        {
            if (isFullyOpened())
            {
                getFontRenderer().drawString(linkText, getLinkX(), getLinkY(), linkColor, true);

                if (isLinkAtOffsetPosition(gui.getMouseX(), gui.getMouseY()))
                {
                    gui.drawTooltip(linkTextList);
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
        }
    }

    @Override
    public String getIcon()
    {
        return "patreon";
    }

    public int getLinkX()
    {
        return posXOffset();
    }

    public int getLinkY()
    {
        return getPosY() + maxHeight - StringHelper.getSplitStringHeight(fontRenderer, linkText, fontRenderer.getStringWidth(linkText)) - 5;
    }

    public String getLink()
    {
        return link;
    }

    public boolean isLinkAtOffsetPosition(int mouseX, int mouseY)
    {
        Mouse.setGrabbed(false);
        if (mouseX >= getLinkX())
        {
            if (mouseX <= getLinkX() + fontRenderer.getStringWidth(linkText))
            {
                if (mouseY >= getLinkY())
                {
                    if (mouseY <= getLinkY() + StringHelper.getSplitStringHeight(fontRenderer, linkText, fontRenderer.getStringWidth(linkText)))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
