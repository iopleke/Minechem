package minechem.registry;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import minechem.chemical.Molecule;
import minechem.helper.LogHelper;

public class MoleculeRegistry
{
    private Map<String, Molecule> formulaMoleculeMap;
    private Map<String, Molecule> nameMoleculeMap;
    private static MoleculeRegistry instance;

    public static MoleculeRegistry getInstance()
    {
        if (instance == null)
        {
            instance = new MoleculeRegistry();
        }
        return instance;
    }

    private MoleculeRegistry()
    {
        this.formulaMoleculeMap = new TreeMap<String, Molecule>();
        this.nameMoleculeMap = new TreeMap<String, Molecule>();
    }

    /**
     * Register a molecule
     *
     * @param molecule
     */
    public void registerMolecule(Molecule molecule)
    {
        formulaMoleculeMap.put(molecule.getFormula(), molecule);
        nameMoleculeMap.put(molecule.fullName.toLowerCase(), molecule);
        molecule.log();// TODO: make this debug only later
    }

    /**
     * Register a molecule using construction inputs
     *
     * @param fullName the full name eg. 'water'
     * @param form     solid, liquid, gas, or plasma
     * @param formula  the formula eg. 'H2O'
     */
    public boolean registerMolecule(String fullName, String form, int colour, String formula)
    {
        try
        {
            registerMolecule(Molecule.parseMolecule(fullName, form, colour, formula));
        } catch (IllegalArgumentException e)
        {
            //Cannot read the compound formula - not actually an error as it might just not have parsed a sub-compound - returns true to include molecule for next parsing
            return true;
        } catch (NullPointerException e)
        {
            LogHelper.warn(fullName + " has a null or empty formula");
        }
        return false;
    }

    /**
     * Get a molecule by name
     *
     * @param name the full name of the molecule
     * @return can return null
     */
    public Molecule getMoleculeByName(String name)
    {
        if (name == null)
        {
            return null;
        }
        return nameMoleculeMap.get(name.toLowerCase());
    }

    /**
     * Get a molecule by formula
     *
     * @param formula the formula for the molecule
     * @return can return null
     */
    public Molecule getMoleculeByFormula(String formula)
    {
        if (formula == null)
        {
            return null;
        }
        return formulaMoleculeMap.get(formula);
    }

    public Collection<Molecule> getMolecules()
    {
        return nameMoleculeMap.values();
    }
}
