package minechem.helper;

import minechem.chemical.ChemicalBase;
import minechem.chemical.Element;
import minechem.registry.ElementRegistry;
import minechem.registry.MoleculeRegistry;

/**
 * Loyal servant to find your elements and molecules
 */
public class Jenkins
{
    /**
     * Get {@link minechem.chemical.Element} by atomic number
     * @param atomicNumber the atomic number
     * @return can be null if atomicNumber does not exists
     */
    public static Element get(int atomicNumber)
    {
        return ElementRegistry.getInstance().getElement(atomicNumber);
    }

    /**
     * Get an element or molecule by abbreviation or full name
     * @param s eg. 'H', 'H2O', 'hydrogen', 'water'
     * @return the element or molecule that matches given abbreviation or full name
     */
    public static <T extends ChemicalBase> T get(String s)
    {
        ChemicalBase chemicalBase = ElementRegistry.getInstance().getElement(s);
        if (chemicalBase == null) chemicalBase = ElementRegistry.getInstance().getElementByName(s);
        if (chemicalBase == null) chemicalBase = MoleculeRegistry.getInstance().getMoleculeByFormula(s);
        if (chemicalBase == null) chemicalBase = MoleculeRegistry.getInstance().getMoleculeByName(s);
        return (T)chemicalBase;
    }
}
