package minechem.item.journal.pages.elements;

import minechem.helper.LocalizationHelper;
import net.afterlifelochie.fontbox.document.Element;
import net.afterlifelochie.fontbox.document.Heading;
import net.minecraft.entity.player.EntityPlayer;

public class JournalHeader extends JournalElement
{
    private String titleKey;

    public JournalHeader(String pageKey)
    {
        super(pageKey);
        titleKey = "journal" + (pageKey.isEmpty() ? "" : "." + pageKey) + ".title";
    }

    @Override
    public Element getElement(EntityPlayer player)
    {
        return getElement(0);
    }

    @Override
    public Element getElement(String[] keys)
    {
        return getElement(0);
    }

    public Element getElement(int indent)
    {
        String sIndent = "";
        for (int i = 0;  i < indent; i++)
            sIndent += "--";
        return new Heading(getKey(), sIndent + " " + LocalizationHelper.getLocalString(titleKey));
    }
}
