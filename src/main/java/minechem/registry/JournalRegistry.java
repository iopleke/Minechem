package minechem.registry;

import minechem.handler.StructuredJournalHandler;
import minechem.item.journal.pages.IJournalPage;
import minechem.item.journal.pages.SectionPage;

public class JournalRegistry
{
    public static SectionPage journal;

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
}
