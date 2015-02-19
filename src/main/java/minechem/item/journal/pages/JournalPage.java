package minechem.item.journal.pages;

import minechem.registry.ResearchRegistry;
import net.minecraft.entity.player.EntityPlayer;

import java.util.regex.Pattern;

public abstract class JournalPage implements IJournalPage
{
    private final String page;
    private String chapter;
    private String key;
    protected final static Pattern subPagePattern = Pattern.compile("(.+?)\\.(.+)");

    protected JournalPage(String page)
    {
        this(page, "");
    }

    protected JournalPage(String page, String chapter)
    {
        this.page = page;
        this.chapter = chapter;
        setKey();
    }

    private void setKey()
    {
        this.key = (chapter.isEmpty() ? "" : chapter + ".") + page;
    }

    @Override
    public final String getUnlocalizedKey()
    {
        return key;
    }

    @Override
    public IJournalPage getPage(String key)
    {
        if (key.equals(page)) return this;
        return null;
    }

    @Override
    public final String getPageName()
    {
        return page;
    }

    @Override
    public void setChapter(String chapter)
    {
        this.chapter = chapter;
        setKey();
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

    @Override
    public int getSubPages()
    {
        return 0;
    }

    @Override
    public boolean isUnlocked(EntityPlayer player)
    {
        return ResearchRegistry.getInstance().hasUnlockedResearch(player, getUnlocalizedKey());
    }
}
