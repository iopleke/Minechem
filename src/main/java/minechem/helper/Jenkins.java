package minechem.helper;

import java.util.ArrayList;
import java.util.List;
import minechem.chemical.ChemicalBase;
import minechem.chemical.Element;
import minechem.registry.ElementRegistry;
import minechem.registry.MoleculeRegistry;
import net.minecraft.item.ItemStack;

/**
 * Loyal servant to find your elements and molecules
 */
public class Jenkins
{
    /**
     * Get {@link minechem.chemical.Element} by atomic number
     *
     * @param atomicNumber the atomic number
     * @return can be null if atomicNumber does not exists
     */
    public static Element get(int atomicNumber)
    {
        return ElementRegistry.getInstance().getElement(atomicNumber);
    }

    /**
     * Get an element or molecule by abbreviation or full name
     *
     * @param s eg. 'H', 'H2O', 'hydrogen', 'water'
     * @return the element or molecule that matches given abbreviation or full name
     */
    public static <T extends ChemicalBase> T get(String s)
    {
        ChemicalBase chemicalBase = ElementRegistry.getInstance().getElement(s);
        if (chemicalBase == null)
        {
            chemicalBase = ElementRegistry.getInstance().getElementByName(s);
        }
        if (chemicalBase == null)
        {
            chemicalBase = MoleculeRegistry.getInstance().getMoleculeByFormula(s);
        }
        if (chemicalBase == null)
        {
            chemicalBase = MoleculeRegistry.getInstance().getMoleculeByName(s);
        }
        return (T) chemicalBase;
    }

    public static ItemStack getStack(String chemical)
    {
        // @TODO: when we have, y'know.. items
        return null;
    }

    /**
     * Gets all {@link minechem.chemical.Element}s and {@link minechem.chemical.Molecule}s that are registered
     *
     * @return a list of all {@link minechem.chemical.ChemicalBase}s registered
     */
    public static List<? extends ChemicalBase> getAll()
    {
        List<ChemicalBase> all = new ArrayList<ChemicalBase>();
        all.addAll(ElementRegistry.getInstance().getElements());
        all.addAll(MoleculeRegistry.getInstance().getMolecules());
        return all;
    }
}
