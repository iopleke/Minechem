package minechem.item.journal.pages.elements;

import minechem.helper.LocalizationHelper;
import net.afterlifelochie.fontbox.document.Element;
import net.afterlifelochie.fontbox.document.Paragraph;
import net.minecraft.entity.player.EntityPlayer;

public class JournalText extends JournalElement
{
    private String textKey;

    public JournalText(String pageKey)
    {
        this(pageKey, pageKey + ".text");
    }

    public JournalText(String pageKey, String textKey)
    {
        super(pageKey);
        this.textKey = "journal." + textKey;
    }

    @Override
    public Element getElement(EntityPlayer player)
    {
        return isUnlocked(player, getKey()) ? new Paragraph(LocalizationHelper.getLocalString(textKey)) : null;
    }
}
