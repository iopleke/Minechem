package minechem.utils;

import minechem.item.element.Element;
import minechem.item.molecule.Molecule;
import minechem.potion.PotionChemical;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


public class MapKey{

    int hashcode;

    public MapKey(ItemStack stack)
    {
        hashcode = stack.getItem().hashCode() ^ stack.getItemDamage();
    }

    public MapKey(FluidStack stack)
    {
        hashcode = stack.getFluid().hashCode() ^ stack.amount;
    }

    public MapKey(PotionChemical chemical)
    {
        if (chemical instanceof Element)
            hashcode = ((Element)chemical).element.hashCode();
        else if (chemical instanceof Molecule)
            hashcode = ((Molecule)chemical).molecule.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof MapKey)
            return hashcode == ((MapKey) o).hashcode;
        return false;
    }

    @Override
    public int hashCode()
    {
        return hashcode;
    }

}
