package minechem.item.journal;

import java.io.IOException;
import java.util.List;

import codechicken.lib.gui.GuiDraw;
import minechem.Compendium;
import minechem.helper.LogHelper;
import minechem.registry.JournalRegistry;
import minechem.registry.ResearchRegistry;
import net.afterlifelochie.fontbox.Fontbox;
import net.afterlifelochie.fontbox.document.Document;
import net.afterlifelochie.fontbox.document.Element;
import net.afterlifelochie.fontbox.layout.DocumentProcessor;
import net.afterlifelochie.fontbox.layout.LayoutException;
import net.afterlifelochie.fontbox.layout.PageWriter;
import net.afterlifelochie.fontbox.layout.components.PageProperties;
import net.afterlifelochie.fontbox.render.BookGUI;
import net.minecraft.entity.player.EntityPlayer;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class JournalGUI extends BookGUI
{
    private String[] authorList;
    private JournalPage[] pageIndex;
    private int top, left;

    private boolean pageChanged = false;

    /**
     * @param who
     *            the player
     * @param knowledgeKeys
     *            a array with all knowledgeKeys of the pages to display
     * @param authors
     *            a list of authors
     */
    public JournalGUI(EntityPlayer who, String[] knowledgeKeys, String[] authors)
    {
        super(UpMode.TWOUP, new Layout[] { new Layout(10, 30), new Layout(138, 30) });

        /**
         * TODO: You will need to compile the page indexes and author list data
         * directly into the book's element stream for it to be written
         * correctly.
         */
        pageIndex = ResearchRegistry.getInstance().getResearchPages(knowledgeKeys);
        authorList = authors;

        try
        {
            /* Create a document */
            Document document = new Document();
            try
            {
                /* Copy the list of elements */
                List<Element> elements = JournalRegistry.journal.getElements(who);
                /* Write elements => document */
                document.pushAll(elements);
            }
            catch (Throwable thrown)
            {
                LogHelper.exception(thrown, Level.WARN);
            }

            /* Set up page formatting */
            PageProperties properties = new PageProperties(200, 800, Fontbox.fromName("Note this"));
            properties.headingFont(Fontbox.fromName("Ampersand"));
            properties.bothMargin(2).lineheightSize(8).spaceSize(4).densitiy(0.66f);

            /* Write elements => page stream */
            PageWriter writer = new PageWriter(properties);
            DocumentProcessor.generatePages(Fontbox.tracer(), document, writer);
            writer.close();

            /* Update system pages */
            changePages(writer.pages());
            markDirty();
        }
        catch (LayoutException layout)
        {
            LogHelper.exception(layout, Level.ERROR);
        }
        catch (IOException ioex)
        {
            LogHelper.exception(ioex, Level.ERROR);
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    private void drawFoldedPages()
    {
        if (ptr > 1)
        {
            // Draw folded page on the left
            GuiDraw.drawTexturedModalRect(5, 163, 0, 188, 21, 21);
        }
        if (ptr < pageIndex.length)
        {
            // Draw folded page on the right
            GuiDraw.drawTexturedModalRect(230, 160, 21, 188, 21, 21);
        }
    }

    private void drawJournalBackground()
    {
        GuiDraw.drawTexturedModalRect(0, 0, 0, 0, 256, 188);
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
        super.keyTyped(c, keycode);
        /* Don't listen to KEY_LEFT or KEY_RIGHT; already handled */
        if (keycode == Keyboard.KEY_DOWN)
        {
            previous();
        }
        if (keycode == Keyboard.KEY_UP)
        {
            next();
        }
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
        super.mouseClicked(mouseX, mouseY, mouseButton);
        mouseX -= left;
        mouseY -= top;
        if (wasRightTabClicked(mouseX, mouseY, mouseButton))
        {
            next();
        }

        if (wasLeftTabClicked(mouseX, mouseY, mouseButton))
        {
            previous();
        }

        LogHelper.info("mouseX:" + mouseX + " mouseY:" + mouseY + " mouseButton:" + mouseButton);
    }

    private boolean wasRightTabClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX >= 230 && mouseX <= 230 + 21)
            {
                if (mouseY >= 160 && mouseY <= 160 + 21)
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
            if (mouseX >= 5 && mouseX <= 5 + 21)
            {
                if (mouseY >= 163 && mouseY <= 163 + 21)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onPageChanged(BookGUI gui, int whatPtr)
    {
        markDirty();
    }

    @Override
    public void drawBackground(int mx, int my, float frame)
    {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(left = width / 2 - 128, top = height / 2 - 94, 0.0f);
        GuiDraw.changeTexture(Compendium.Resource.GUI.journal);
        drawJournalBackground();
        drawFoldedPages();
    }

    @Override
    public void drawForeground(int mx, int my, float frame)
    {
        GL11.glPopMatrix();
    }
}
