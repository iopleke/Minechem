package minechem.item.journal;

import codechicken.lib.gui.GuiDraw;
import fontbox.PageBox;
import minechem.Compendium;
import minechem.helper.FontBoxHelper;
import minechem.registry.ResearchRegistry;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class JournalGUI extends GuiScreen
{
    private PageBox[] currentPages, currentTitles;
    private JournalPage[] pageIndex;
    private int displayPage; // the left page
    private String[] authorList;

    /**
     * FontBox values
     */
    private int pageWidth = 230;
    private int pageHeight = 800;
    private int titleWidth = 230;
    private int titleHeight = 100;
    private int margin_l = 5;
    private int margin_r = 5;
    private int space = 1;
    private int fontSize = 1;

    /**
     *
     * @param knowledgeKeys a array with all knowledgeKeys of the pages to display
     * @param authors       a list of authors
     */
    public JournalGUI(String[] knowledgeKeys, String[] authors)
    {
        pageIndex = ResearchRegistry.getInstance().getResearchPages(knowledgeKeys);
        authorList = authors;
        currentPages = new PageBox[2];
        currentTitles = new PageBox[2];
        displayPage = 0;
        loadPage(displayPage);
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
        drawPages();

        GL11.glPopMatrix();
    }

    /**
     * Draw the actual page content
     */
    private void drawPages()
    {
        FontBoxHelper.renderPageBox(currentPages[0], 10, 30, zLevel);
        FontBoxHelper.renderPageBox(currentTitles[0], 20, 10, zLevel);
        FontBoxHelper.renderPageBox(currentPages[1], 10 + 128, 30, zLevel);
        FontBoxHelper.renderPageBox(currentTitles[1], 20 + 128, 10, zLevel);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    private void drawJournalBackground()
    {
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 256, 188);
    }

    private void drawFoldedPages()
    {

        if (displayPage > 0)
        {
            // Draw folded page on the left
            GuiDraw.drawTexturedModalRect(5, 163, 0, 188, 21, 21);
        }
        if (displayPage + 2 < pageIndex.length)
        {
            // Draw folded page on the right
            GuiDraw.drawTexturedModalRect(230, 160, 21, 188, 21, 21);
        }
    }

    /**
     * Loads the given page pair Where page 0 is the author list
     *
     * @param displayPage will show the pair eg. 1 -> 0, 1; 1 -> 0, 1; 2 -> 2, 3; ...
     */
    private void loadPage(int displayPage)
    {
        if (displayPage % 2 != 0)
        {
            displayPage -= 1;
        }
        currentPages[0] = FontBoxHelper.boxText("", pageWidth, pageHeight, margin_l, margin_r, space, fontSize);
        currentTitles[0] = FontBoxHelper.boxText("", titleWidth, titleHeight, margin_l, margin_r, space, fontSize);
        currentPages[1] = FontBoxHelper.boxText("", pageWidth, pageHeight, margin_l, margin_r, space, fontSize);
        currentTitles[1] = FontBoxHelper.boxText("", titleWidth, titleHeight, margin_l, margin_r, space, fontSize);
        if (displayPage == 0)
        {
            String sAuthors = "";
            for (String author : authorList)
            {
                sAuthors += "- " + author + "\n";
            }
            currentPages[0] = FontBoxHelper.boxText(sAuthors, pageWidth, pageHeight, margin_l, margin_r, space, fontSize);
            currentTitles[0] = FontBoxHelper.boxText("Written by:", titleWidth, titleHeight, margin_l, margin_r, space, fontSize);
        }
        if (pageIndex.length > 0)
        {
            currentPages[1] = FontBoxHelper.boxText(pageIndex[displayPage].content, pageWidth, pageHeight, margin_l, margin_r, space, fontSize);
            currentTitles[1] = FontBoxHelper.boxText(pageIndex[displayPage].title, titleWidth, titleHeight, margin_l, margin_r, space, fontSize);
            if (displayPage != 0)
            {
                currentPages[0] = FontBoxHelper.boxText(pageIndex[displayPage - 1].content, pageWidth, pageHeight, margin_l, margin_r, space, fontSize);
                currentTitles[0] = FontBoxHelper.boxText(pageIndex[displayPage - 1].title, titleWidth, titleHeight, margin_l, margin_r, space, fontSize);
            }
        }
    }

    /**
     *
     * @param c
     * @param keycode
     */
    @Override
    protected void keyTyped(char c, int keycode)
    {
        if (keycode == Keyboard.KEY_LEFT)
        {
            if (displayPage > 0)
            {
                displayPage--;
                if (displayPage > 0)
                {
                    displayPage--;
                }
            }
        }
        if (keycode == Keyboard.KEY_RIGHT)
        {
            if (displayPage < pageIndex.length)
            {
                displayPage++;
                if (displayPage < pageIndex.length)
                {
                    displayPage++;
                }
            }
        }

        loadPage(displayPage);
        super.keyTyped(c, keycode);

    }
}
