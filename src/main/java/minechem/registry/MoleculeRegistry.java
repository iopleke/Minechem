package minechem.registry;

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
        LogHelper.debug("Molecule registered: " + molecule.getDebugInfo());
    }

    /**
     * Register a molecule using construction inputs
     *
     * @param fullName the full name eg. 'water'
     * @param form     solid, liquid, gas, or plasma
     * @param formula  the formula eg. 'H2O'
     */
    public void registerMolecule(String fullName, String formula, int meltingPoint, int boilingPoint, int temp)
    {
        registerMolecule(Molecule.parseMolecule(fullName, formula, meltingPoint, boilingPoint, temp));
    }
    
    public void registerMolecule(String fullName, String formula, int meltingPoint, int boilingPoint)
    {
        registerMolecule(Molecule.parseMolecule(fullName, formula, meltingPoint, boilingPoint));
    }

    /**
     * Get a molecule by name
     *
     * @param name the full name of the molecule
     * @return can return null
     */
    public Molecule getMoleculeByName(String name)
    {
        return nameMoleculeMap.get(name);
    }

    /**
     * Get a molecule by formula
     *
     * @param formula the formula for the molecule
     * @return can return null
     */
    public Molecule getMoleculeByFormula(String formula)
    {
        return formulaMoleculeMap.get(formula);
    }

}
