package minechem.item.journal.pages;

import minechem.helper.LocalizationHelper;
import minechem.registry.ResearchRegistry;
import net.afterlifelochie.fontbox.document.Element;
import net.afterlifelochie.fontbox.document.Heading;
import net.afterlifelochie.fontbox.document.Paragraph;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Element> getElements(EntityPlayer player)
    {
        List<Element> result = new ArrayList<Element>();
        if (isUnlocked(player))
        {
            result.add(new Heading(getUnlocalizedKey(), LocalizationHelper.getLocalString(getUnlocalizedKey() + ".title")));
            String text;
            if (unlocks.length > 0)
            {
                String[] unlockText = new String[unlocks.length];
                for (int i = 0; i < unlocks.length; i++)
                {
                    unlockText[i] = getUnlockedText(i, player);
                }
                text = LocalizationHelper.getFormattedString(getUnlocalizedKey() + ".text", unlockText);
            }
            else
            {
                text = LocalizationHelper.getLocalString(getUnlocalizedKey() + ".text");
            }
            result.add(new Paragraph(text));
        }
        return result;
    }

    private String getUnlockedText(int i, EntityPlayer player)
    {
        String result = LocalizationHelper.getLocalString(getUnlocalizedKey() + ".text." + i);
        if (!ResearchRegistry.getInstance().hasUnlockedResearch(player, unlocks[i]))
        {
            result.replaceAll(".","X");
        }
        return result;
    }


}
