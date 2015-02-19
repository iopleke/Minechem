package minechem.item.journal.pages.elements;

import minechem.registry.ResearchRegistry;
import net.minecraft.entity.player.EntityPlayer;

public abstract class JournalElement implements IJournalElement
{
    private String pageKey;

    protected JournalElement(String pageKey)
    {
        this.pageKey = pageKey;
    }

    public String getKey()
    {
        return pageKey;
    }

    public boolean isUnlocked(EntityPlayer player, String key)
    {
        return ResearchRegistry.getInstance().hasUnlockedResearch(player, key);
    }
}
