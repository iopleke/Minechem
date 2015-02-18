package minechem.item.journal.pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class SectionPage extends JournalPage
{
    private final Map<String, IJournalPage> pages = new LinkedHashMap<String, IJournalPage>();

    public SectionPage(String page)
    {
        this(page, new ArrayList<IJournalPage>());
    }

    public SectionPage(String page, List<IJournalPage> pageList)
    {
        this(page, "", pageList);
    }

    public SectionPage(String page, String chapter,List<IJournalPage> pageList)
    {
        super(page, chapter);
        for (IJournalPage jPage : pageList) pages.put(jPage.getPageKey(), jPage);
    }

    @Override
    public IJournalPage getPage(String key)
    {
        IJournalPage result = super.getPage(key);
        if (result == null)
        {
            if (pages.containsKey(key)) return pages.get(key);
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
        pages.put(page.getPageKey(), page);
    }
}
