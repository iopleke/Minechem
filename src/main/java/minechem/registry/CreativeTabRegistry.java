package minechem.registry;

import minechem.reference.Compendium;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabRegistry
{
    public static CreativeTabs TAB_PRIMARY = new CreativeTabs(Compendium.Naming.name)
    {
        @Override
        public Item getTabIconItem()
        {
            return Items.fermented_spider_eye;
        }
    };
}
