package minechem.item.journal;

import codechicken.lib.gui.GuiDraw;
import fontbox.PageBox;
import minechem.Compendium;
import minechem.helper.FontBoxHelper;
import minechem.helper.LocalizationHelper;
import minechem.helper.LogHelper;
import minechem.registry.ResearchRegistry;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class JournalGUI extends GuiScreen
{
    private String[] authorList;
    private PageBox[] currentPages, currentTitles;
    private int displayPage; // the left page
    private JournalPage[] pageIndex;

    private FontBoxHelper.PageBoxMetrics pageMetrics;
    private FontBoxHelper.PageBoxMetrics titleMetrics;

    private boolean pageChanged = false;

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
        pageMetrics = new FontBoxHelper.PageBoxMetrics(220, 800, 5, 0, 5, 30);
        titleMetrics = new FontBoxHelper.PageBoxMetrics(230, 100, 5, 0, 5, 30);
        displayPage = 0;
        markDirty();
    }

    private void decrementPage()
    {
        if (displayPage > 0)
        {
            displayPage--;
            markDirty();
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    private void drawFoldedPages()
    {
        if (displayPage > 1)
        {
            // Draw folded page on the left
            GuiDraw.drawTexturedModalRect(5, 163, 0, 188, 21, 21);
        }
        if (displayPage < pageIndex.length)
        {
            // Draw folded page on the right
            GuiDraw.drawTexturedModalRect(230, 160, 21, 188, 21, 21);
        }
    }

    private void drawJournalBackground()
    {
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 256, 188);
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
    public void drawScreen(int mouseX, int mouseY, float unused)
    {
        super.drawScreen(mouseX, mouseY, unused);

        if (isDirty())
        {
            loadPage(displayPage);
            markClean();
        }

        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(width / 2 - 128, height / 2 - 94, 0.0f);
        GuiDraw.changeTexture(Compendium.Resource.GUI.journal);

        drawJournalBackground();
        drawFoldedPages();
        drawPages();

        GL11.glPopMatrix();
    }

    private void incrementPage()
    {
        if (displayPage < pageIndex.length)
        {
            displayPage++;
            markDirty();
        }
    }

    private boolean isDirty()
    {
        return pageChanged;
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
            decrementPage();
            decrementPage();
        }
        if (keycode == Keyboard.KEY_RIGHT)
        {
            incrementPage();
            incrementPage();
        }

        loadPage(displayPage);
        super.keyTyped(c, keycode);
    }

    private void markClean()
    {
        pageChanged = false;
    }

    private void markDirty()
    {
        pageChanged = true;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {

        // TODO: make clicking respect GUI resizing
        if (wasRightTabClicked(mouseX, mouseY, mouseButton))
        {
            incrementPage();
            incrementPage();
        }

        if (wasLeftTabClicked(mouseX, mouseY, mouseButton))
        {
            decrementPage();
            decrementPage();
        }

        LogHelper.info("mouseX:" + mouseX + " mouseY:" + mouseY + " mouseButton:" + mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private boolean wasRightTabClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX >= 316 && mouseX <= 335)
            {
                if (mouseY >= 188 && mouseY <= 204)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean wasLeftTabClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX >= 91 && mouseX <= 110)
            {
                if (mouseY >= 190 && mouseY <= 209)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Loads the given page pair Where page 0 is the author list Page 0 is the authors page, page 1 is the Index The following pages will be the actual content
     *
     * @param displayPage will show the pair eg. 1 -> 0, 1; 1 -> 0, 1; 2 -> 2, 3; ...
     */
    private void loadPage(int displayPage)
    {
        if (displayPage % 2 != 0)
        {
            displayPage -= 1;
        }
        currentPages[0] = FontBoxHelper.boxText("", pageMetrics);
        currentTitles[0] = FontBoxHelper.boxText("", titleMetrics);
        currentPages[1] = FontBoxHelper.boxText("", pageMetrics);
        currentTitles[1] = FontBoxHelper.boxText("", titleMetrics);
        if (displayPage < 2)
        {
            String sAuthors = "";
            for (String author : authorList)
            {
                sAuthors += "- " + author + "\n";
            }
            currentPages[0] = FontBoxHelper.boxText(sAuthors, pageMetrics);
            currentTitles[0] = FontBoxHelper.boxText(LocalizationHelper.getLocalString("gui.journal.writtenBy") + ":", titleMetrics);
            String sIndex = "";
            for (JournalPage page : pageIndex)
            {
                sIndex += page + "\n";
            }
            currentPages[1] = FontBoxHelper.boxText(sIndex, pageMetrics);
            currentTitles[1] = FontBoxHelper.boxText(LocalizationHelper.getLocalString("gui.journal.index"), titleMetrics);
        } else
        {
            currentPages[0] = FontBoxHelper.boxText(pageIndex[displayPage - 2].content, pageMetrics);
            currentTitles[0] = FontBoxHelper.boxText(pageIndex[displayPage - 2].title, titleMetrics);
            if (displayPage - 1 < pageIndex.length)
            {
                currentPages[1] = FontBoxHelper.boxText(pageIndex[displayPage - 1].content, pageMetrics);
                currentTitles[1] = FontBoxHelper.boxText(pageIndex[displayPage - 1].title, titleMetrics);
            }
        }
    }
}
