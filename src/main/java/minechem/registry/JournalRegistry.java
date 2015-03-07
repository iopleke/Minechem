package minechem.registry;

import java.util.List;
import minechem.handler.StructuredJournalHandler;
import minechem.item.journal.pages.IJournalPage;
import minechem.item.journal.pages.SectionPage;
import net.afterlifelochie.fontbox.document.CompilerHint;
import net.afterlifelochie.fontbox.document.Element;
import net.minecraft.entity.player.EntityPlayer;

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
        return journal.getPage(key) != null;
    }

    public static List<Element> getJournalFor(EntityPlayer player)
    {
        List<Element> result = getIndexPageFor(player);
        result.add(new CompilerHint(CompilerHint.HintType.PAGEBREAK));
        result.addAll(journal.getElements(player));
        result.remove(result.size() - 1);
        return result;
    }

    public static List<Element> getJournalFor(String[] keys)
    {
        List<Element> result = getIndexPageFor(keys);
        result.add(new CompilerHint(CompilerHint.HintType.PAGEBREAK));
        result.addAll(journal.getElements(keys));
        result.remove(result.size() - 1);
        return result;
    }

    public static List<Element> getIndexPageFor(EntityPlayer player)
    {
        return journal.getIndexPage(player, 0);
    }

    public static List<Element> getIndexPageFor(String[] keys)
    {
        return journal.getIndexPage(keys, 0);
    }

    public static SectionPage setJournal(SectionPage journal)
    {
        JournalRegistry.journal = journal;
        return JournalRegistry.journal;
    }
}
