package minechem.registry;

import minechem.handler.StructuredJournalHandler;
import minechem.item.journal.pages.IJournalPage;
import minechem.item.journal.pages.SectionPage;
import net.afterlifelochie.fontbox.document.Element;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class JournalRegistry
{
    private static SectionPage journal;

    public static void init()
    {
        StructuredJournalHandler.init();
    }

    public static IJournalPage addPage(IJournalPage page)
    {
        return addPage("", page);
    }

    public static IJournalPage addPage(String chapter, IJournalPage page)
    {
        IJournalPage section = journal.getPage(chapter);
        if (section != null && section.hasSubPages())
        {
            page.setChapter(chapter);
            section.addSubPage(page);
        }
        return page;
    }
    
    public static boolean hasPage(String key)
    {
        return journal.getPage(key.toLowerCase()) != null;
    }
    
    public static List<Element> getJournalFor(EntityPlayer player)
    {
        return journal.getElements(player);
    }
    
    public static SectionPage setJournal(SectionPage journal)
    {
        JournalRegistry.journal = journal;
        return JournalRegistry.journal;
    }
}
