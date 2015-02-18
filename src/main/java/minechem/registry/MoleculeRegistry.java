package minechem.registry;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import minechem.chemical.Element;
import minechem.chemical.Molecule;

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
    public void registerMolecule(String fullName, String form, String formula)
    {
        registerMolecule(Molecule.parseMolecule(fullName, form, formula));
    }

    /**
     * Get a molecule by name
     *
     * @param name the full name of the molecule
     * @return can return null
     */
    public Molecule getMoleculeByName(String name)
    {
        if (name == null) return null;
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
        if (formula == null) return null;
        return formulaMoleculeMap.get(formula);
    }

    public Collection<Molecule> getMolecules()
    {
        return nameMoleculeMap.values();
    }
}
