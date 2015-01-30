package minechem.apparatus.prefab.gui.tab;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.MathHelper;
import java.awt.Color;
import java.util.List;
import minechem.helper.LocalizationHelper;
import minechem.reference.Compendium;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author jakimfett
 */
public abstract class BasicGuiTab extends TabBase
{
    private int firstLine;
    private final int maxFirstLine;
    private final int numLines;
    private final List<String> tabText;
    protected String tabTitle;
    protected String tabTooltip;
    protected boolean enabled = true;

    public BasicGuiTab(GuiBase gui, String tabText)
    {
        this(gui, tabText, 1);

    }

    public BasicGuiTab(GuiBase gui, String tabText, int side)
    {
        super(gui, side);
        textColor = Color.WHITE.getRGB();
        maxWidth = 120;
        maxHeight = 92;
        this.tabText = getFontRenderer().listFormattedStringToWidth(tabText, this.maxWidth - 16);
        this.numLines = Math.min(this.tabText.size(), (this.maxHeight - 24) / getFontRenderer().FONT_HEIGHT);
        this.maxFirstLine = (this.tabText.size() - this.numLines);

        if (side == LEFT)
        {
            this.texture = Compendium.Resource.Tab.left;
        } else
        {
            this.texture = Compendium.Resource.Tab.right;
        }

    }

    @Override
    public void draw()
    {
        super.draw();
        if (isEnabled())
        {
            drawBackground();
            drawTabIcon(getIcon());
            if (isFullyOpened())
            {
                getFontRenderer().drawStringWithShadow(getTitle(), posXOffset() + 18, this.posY + 6, this.headerColor);
                for (int i = this.firstLine; i < this.firstLine + this.numLines; i++)
                {
                    getFontRenderer().drawString((String) this.tabText.get(i), posXOffset() + 2, this.posY + 20 + (i - this.firstLine) * getFontRenderer().FONT_HEIGHT, this.textColor, true);
                }
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

    }

    public abstract String getIcon();

    public String getTitle()
    {
        return LocalizationHelper.getLocalString(this.tabTitle);
    }

    public String getTooltip()
    {
        if (!isFullyOpened())
        {
            LocalizationHelper.getLocalString(this.tabTooltip);
        }
        return null;

    }

    @Override
    public boolean onMousePressed(int x, int y, int z)
    {
        if (!isFullyOpened())
        {
            return false;
        }
        if (this.side == LEFT)
        {
            x += this.currentWidth;
        }
        x -= this.currentShiftX;
        y -= this.currentShiftY;

        if (x < 108)
        {
            return false;
        }
        if (y < 52)
        {
            this.firstLine = MathHelper.clampI(this.firstLine - 1, 0, this.maxFirstLine);
        } else
        {
            this.firstLine = MathHelper.clampI(this.firstLine + 1, 0, this.maxFirstLine);
        }
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return this.enabled;
    }

}
