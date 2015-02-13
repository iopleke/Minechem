package minechem.item.journal;

public class JournalPage
{
    public final String title;
    public final String content;

    /**
     * *
     * Creates a new JournalPage
     *
     * @param title   page title
     * @param content page content
     */
    public JournalPage(String title, String content)
    {
        this.content = content;
        this.title = title;
    }

    @Override
    public String toString()
    {
        return this.title;
    }
}
