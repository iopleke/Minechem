package minechem.item;

import minechem.registry.CreativeTabRegistry;
import net.minecraft.item.Item;

/**
 * Defines properties of a basic item
 */
public abstract class BasicItem extends Item
{
    public BasicItem()
    {
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
    }
}
