package minechem.gui;

import minechem.item.fusionstar.FusionStarItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabMinechem extends CreativeTabs
{
    private final Item tabIconItem;

    public CreativeTabMinechem(String tabName)
    {
        super(tabName);

        this.tabIconItem = new FusionStarItem();
    }

    @Override
    public Item getTabIconItem()
    {
        return this.tabIconItem;
    }

}
