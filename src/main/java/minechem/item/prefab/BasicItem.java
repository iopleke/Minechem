package minechem.item.prefab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.reference.Compendium;
import minechem.registry.CreativeTabRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

/**
 * Defines properties of a basic item
 */
public abstract class BasicItem extends Item
{
    public BasicItem()
    {
        this("basicItem");
    }

    public BasicItem(String itemName)
    {
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        setUnlocalizedName(itemName);
        setTextureName(itemName + "Icon");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(Compendium.Naming.id + ":" + iconString);
    }
}
