package minechem.gui;

import minechem.MinechemItemGeneration;
import minechem.item.element.EnumElement;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabMinechem extends CreativeTabs
{

    public CreativeTabMinechem(String label)
    {
        super(label);
    }

    public CreativeTabMinechem(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    @Override
    public ItemStack getIconItemStack()
    {
<<<<<<< HEAD:src/main/java/minechem/gui/CreativeTabMinechem.java
        return new ItemStack(MinechemItemGeneration.element, 1, EnumElement.U.ordinal());
=======
        return new ItemStack(MinechemItemsGeneration.element, 1, EnumElement.U.ordinal());
>>>>>>> MaxwolfRewrite:src/main/java/minechem/CreativeTabMinechem.java
    }

}
