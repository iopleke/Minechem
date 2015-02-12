package minechem.item.journal;

import codechicken.lib.gui.GuiDraw;
import fontbox.PageBox;
import minechem.Compendium;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

import java.util.UUID;

/**
 *
 */
public class JournalGUI extends GuiScreen
{
    private PageBox[] pageIndex;
    private int displayPage;

    /**
     *
     * @param playerUUID the player UUID
     */
    public JournalGUI(UUID playerUUID)
    {
        pageIndex = new PageBox[2];
        displayPage = 0;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float unused)
    {
        super.drawScreen(mouseX, mouseY, unused);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(width / 2 - 128, height / 2 - 94, 0.0f);
        GuiDraw.changeTexture(Compendium.Resource.GUI.journal);

        drawJournalBackground();
        drawFoldedPages();

        GL11.glPopMatrix();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void drawJournalBackground()
    {
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 256, 188);
    }

    public void drawFoldedPages()
    {

        if (pageIndex.length > 1 && displayPage > 0)
        {
            // Draw folded page on the left
            GuiDraw.drawTexturedModalRect(5, 163, 0, 188, 21, 21);
        }
        if (pageIndex.length > 1 && displayPage < pageIndex.length)
        {
            // Draw folded page on the right
            GuiDraw.drawTexturedModalRect(230, 160, 21, 188, 21, 21);
        }
    }
}
