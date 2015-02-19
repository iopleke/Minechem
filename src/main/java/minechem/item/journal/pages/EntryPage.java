package minechem.item.journal.pages;

import minechem.item.journal.pages.elements.IJournalElement;
import minechem.item.journal.pages.elements.JournalHeader;
import net.afterlifelochie.fontbox.document.Element;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntryPage extends JournalPage
{
    List<IJournalElement> elements;

    public EntryPage(String page, String chapter, IJournalElement... elements)
    {
        this(page, chapter, new ArrayList<IJournalElement>(Arrays.asList(elements)));
    }

    public EntryPage(String page, String chapter, List<IJournalElement> elements)
    {
        super(page, chapter);
        if (elements.size() == 0 || !(elements.get(0) instanceof JournalHeader)) elements.add(0, new JournalHeader(getUnlocalizedKey()));
        this.elements = elements;
    }

    @Override
    public List<Element> getElements(EntityPlayer player)
    {
        List<Element> result = new ArrayList<Element>();
        if (isUnlocked(player))
        {
            for (IJournalElement element : elements)
            {
                Element e = element.getElement(player);
                if (e != null) result.add(e);
            }
        }
        return result;
    }
}
