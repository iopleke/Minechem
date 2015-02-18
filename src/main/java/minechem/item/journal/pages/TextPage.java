package minechem.item.journal.pages;

public class TextPage extends JournalPage
{
    private String[] unlocks;

    public TextPage(String page, String... unlocks)
    {
        super(page);
        this.unlocks = unlocks;
    }

    public TextPage(String page, String chapter, String... unlocks)
    {
        super(page, chapter);
        this.unlocks = unlocks;
    }
}
