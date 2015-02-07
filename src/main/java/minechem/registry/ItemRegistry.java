package minechem.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.item.journal.JournalItem;

public class ItemRegistry
{
    public static JournalItem journal;

    public static void init()
    {
        journal = new JournalItem();
        GameRegistry.registerItem(journal, journal.getUnlocalizedName());
    }

}
