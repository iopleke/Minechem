package minechem.registry;

import minechem.reference.Compendium;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabRegistry extends CreativeTabs
{
    public static CreativeTabs TAB_PRIMARY = new CreativeTabRegistry(Compendium.Naming.name, 0);

    public CreativeTabRegistry(String tabName, int i)
    {
        super(tabName);
    }

    @Override
    public Item getTabIconItem()
    {
        return null;
    }

    @Override
    public ItemStack getIconItemStack()
    {
        return new ItemStack(Items.fermented_spider_eye);

    }
}
