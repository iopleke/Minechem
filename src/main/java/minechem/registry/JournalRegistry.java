package minechem.registry;

import minechem.item.journal.pages.IJournalPage;
import minechem.item.journal.pages.SectionPage;
import minechem.item.journal.pages.TextPage;

public class JournalRegistry
{
    public static SectionPage journal = new SectionPage("");

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

    public static void init()
    {
        addPage(new SectionPage("elements"));
        addPage(new SectionPage("apparatus"));
        addPage("apparatus", new TextPage("microscope"));
        addPage("elements", new TextPage("hydrogen"));
        addPage("elements", new TextPage("helium"));
        addPage("elements", new SectionPage("radioactive"));
        addPage("elements.radioactive", new TextPage("uranium"));
        return;
    }
}
