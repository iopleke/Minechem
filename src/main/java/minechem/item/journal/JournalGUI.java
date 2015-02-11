package minechem.item.journal;

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
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(Compendium.Resource.GUI.journal);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

}
