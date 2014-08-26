package minechem.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabMinechem extends CreativeTabs
{
    private final Item tabIconItem;

    public CreativeTabMinechem(String tabName, Item tabIconItem)
    {
        super(tabName);

        this.tabIconItem = tabIconItem;
    }

    @Override
    public Item getTabIconItem()
    {
        return this.tabIconItem;
    }

}
