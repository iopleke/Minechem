package minechem.item.journal.pages;

import java.util.regex.Pattern;

public abstract class JournalPage implements IJournalPage
{
    private final String page;
    private String chapter;
    protected final static Pattern subPagePattern = Pattern.compile("(.+?)\\.(.+)");

    protected JournalPage(String page)
    {
        this(page, "");
    }

    protected JournalPage(String page, String chapter)
    {
        this.page = page;
        this.chapter = chapter;
    }

    @Override
    public final String getUnlocalizedKey()
    {
        return chapter + "." + page;
    }

    @Override
    public IJournalPage getPage(String key)
    {
        if (key.equals(page)) return this;
        return null;
    }

    @Override
    public final String getPageKey()
    {
        return page;
    }

    @Override
    public void setChapter(String chapter)
    {
        this.chapter = chapter;
    }

    @Override
    public boolean hasSubPages()
    {
        return false;
    }

    @Override
    public void addSubPage(IJournalPage page)
    {
    }
}
