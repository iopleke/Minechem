package minechem.utils;

import minechem.item.element.Element;
import minechem.item.molecule.Molecule;
import minechem.potion.PotionChemical;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MapKey
{

    int hashcode;

    public MapKey(ItemStack stack)
    {
        hashcode = stack.getItem().hashCode() ^ stack.getItemDamage();
    }

    public MapKey(FluidStack stack)
    {
        hashcode = stack.getFluid().hashCode();
    }

    public MapKey(PotionChemical chemical)
    {
        if (chemical instanceof Element)
        {
            hashcode = ((Element) chemical).element.hashCode();
        } else if (chemical instanceof Molecule)
        {
            hashcode = ((Molecule) chemical).molecule.hashCode();
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof MapKey)
        {
            return hashcode == ((MapKey) o).hashcode;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return hashcode;
    }

    public static MapKey getKey(ItemStack stack)
    {
        if (stack == null || stack.getItem() == null)
        {
            return null;
        }
        return new MapKey(stack);
    }

    public static MapKey getKey(FluidStack stack)
    {
        if (stack == null)
        {
            return null;
        }
        return new MapKey(stack);
    }
}
