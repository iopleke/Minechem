package minechem.item.journal.pages;

public interface IJournalPage
{
    String getUnlocalizedKey();

    IJournalPage getPage(String key);

    String getPageKey();

    boolean hasSubPages();

    void addSubPage(IJournalPage page);

    void setChapter(String chapter);

    int getSubPages();
}
