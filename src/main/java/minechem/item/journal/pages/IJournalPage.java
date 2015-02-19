package minechem.item.journal.pages;

import net.afterlifelochie.fontbox.document.Element;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public interface IJournalPage
{
    String getPageKey();

    IJournalPage getPage(String key);

    String getPageName();

    boolean hasSubPages();

    void addSubPage(IJournalPage page);

    void setChapter(String chapter);

    int getSubPages();

    List<Element> getElements(EntityPlayer player);

    boolean isUnlocked(EntityPlayer player);
}
