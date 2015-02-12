package minechem.item.journal;

import codechicken.lib.gui.GuiDraw;
import fontbox.PageBox;
import minechem.Compendium;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class JournalGUI extends GuiScreen
{
    private PageBox[] pageIndex;

    /**
     *
     * @param playerName need to use UUID if possible, using displayname for now
     */
    public JournalGUI(String playerName)
    {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float unused)
    {
        super.drawScreen(mouseX, mouseY, unused);
        GL11.glPushMatrix();
        drawJournalBackground();
        GL11.glPopMatrix();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public void drawJournalBackground()
    {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(width / 2 - 128, height / 2 - 94, 0.0f);
        GuiDraw.changeTexture(Compendium.Resource.GUI.journal);
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 256, 188);
    }
}
