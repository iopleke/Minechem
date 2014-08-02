package minechem.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabMinechem extends CreativeTabs
{
    private final Item tabIconItem;
    
    public CreativeTabMinechem(int tabID, String tabName, Item tabIconItem)
    {
        super(tabID, tabName);
        
        this.tabIconItem = tabIconItem;
    }

    @Override
    public Item getTabIconItem()
    {
        return this.tabIconItem;
    }

}
