package minechem.item.journal.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import minechem.item.journal.pages.elements.JournalHeader;
import net.afterlifelochie.fontbox.document.Element;
import net.minecraft.entity.player.EntityPlayer;

public class SectionPage extends JournalPage
{
    private final Map<String, IJournalPage> pages = new LinkedHashMap<String, IJournalPage>();
    JournalHeader heading;

    public SectionPage(String page)
    {
        this(page, new ArrayList<IJournalPage>());
    }

    public SectionPage(String page, List<IJournalPage> pageList)
    {
        this(page, "", pageList);
    }

    public SectionPage(String page, String chapter, List<IJournalPage> pageList)
    {
        super(page, chapter);
        for (IJournalPage jPage : pageList)
        {
            pages.put(jPage.getPageName(), jPage);
        }
        heading = new JournalHeader(getPageKey());
    }

    @Override
    public IJournalPage getPage(String key)
    {
        IJournalPage result = super.getPage(key);
        if (result == null)
        {
            if (pages.containsKey(key))
            {
                return pages.get(key);
            }
            Matcher matcher = subPagePattern.matcher(key);
            if (matcher.find())
            {
                if (pages.containsKey(matcher.group(1)))
                {
                    return pages.get(matcher.group(1)).getPage(matcher.group(2));
                }
            }
        }
        return result;
    }

    @Override
    public boolean hasSubPages()
    {
        return true;
    }

    @Override
    public void addSubPage(IJournalPage page)
    {
        pages.put(page.getPageName(), page);
    }

    @Override
    public int getSubPages()
    {
        int total = pages.size();
        for (IJournalPage page : pages.values())
        {
            total += page.getSubPages();
        }
        return total;
    }

    public List<Element> getPageElements(EntityPlayer player)
    {
        List<Element> result = new ArrayList<Element>();
        result.add(heading.getElement(player));
        for (IJournalPage page : pages.values())
        {
            if (page.isUnlocked(player))
            {
                //TODO: for every unlocked page add a link.
            }
        }
        return result;
    }

    @Override
    public List<Element> getElements(EntityPlayer player)
    {
        List<Element> result = new ArrayList<Element>();
        for (IJournalPage page : pages.values())
        {
            result.addAll(page.getElements(player));
        }
        if (!result.isEmpty())
        {
            result.addAll(0, getPageElements(player));
        }
        return result;
    }

    @Override
    public boolean isUnlocked(EntityPlayer player)
    {
        for (IJournalPage page : pages.values())
        {
            if (page.isUnlocked(player))
            {
                return true;
            }
        }
        return false;
    }
}
