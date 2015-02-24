package minechem.chemical;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minechem.Compendium;
import minechem.helper.LogHelper;
import minechem.registry.ElementRegistry;
import minechem.registry.MoleculeRegistry;
import net.minecraft.util.ResourceLocation;

/**
 * Data object for molecules
 */
public class Molecule extends ChemicalBase
{
    public ChemicalStructure structure;
    private ResourceLocation structureResource;

    /**
     * Parses a String to a Molecule
     *
     * @param fullName the full name eg. 'water'
     * @param form     solid, liquid, gas, or plasma
     * @param colour   int value representing the molecule's colour - passing 0 will cause one to be automatically generated
     * @param formula  the formula eg. 'H2O'
     * @return can throw an IllegalArgumentException
     */
    public static Molecule parseMolecule(String fullName, String form, int colour, String formula)
    {
        return new Molecule(fullName, form, colour, parseStructure(formula));
    }

    private Molecule(String fullName, String form, int colour, ChemicalStructure structure)
    {
        super(fullName, form, colour);
        this.structure = structure;
        this.structureResource = Compendium.Resource.GUI.getResourceForStructure(fullName.toLowerCase());
    }

    @Override
    public void log()
    {
        LogHelper.debug("Molecule name: " + this.fullName);
        LogHelper.debug("Molecule structure: " + this.getFormula());
        LogHelper.debug("Form: " + this.form);
    }

    @Override
    public boolean isElement()
    {
        return false;
    }

    @Override
    public int getColour()
    {
        int colour = super.getColour();
        return colour == Compendium.Color.TrueColor.transparent? structure.getColour() : colour;
    }

    @Override
    public String getFormula()
    {
        return structure.getFormula();
    }

    @Override
    public List<String> getToolTip()
    {
        List<String> list = new LinkedList<String>();
        list.add("Form: " + this.form);
        list.add("Formula: " + this.getFormula());
        return list;
    }

    /**
     * Helper methods
     */
    private static int toInteger(String string)
    {
        if (isNull(string))
        {
            return 1;
        }
        return Integer.valueOf(string);
    }

    private static Element toElement(String string)
    {
        return ElementRegistry.getInstance().getElement(string);
    }

    private static Molecule toMolecule(String string, boolean name)
    {
        return name ? MoleculeRegistry.getInstance().getMoleculeByName(string) : MoleculeRegistry.getInstance().getMoleculeByFormula(string);
    }

    private static boolean isNull(String string)
    {
        return string == null || string.isEmpty();
    }

    private static boolean put(ChemicalStructure structure, ChemicalBase chemical, int multiplier)
    {
        if (chemical == null)
        {
            return false;
        }
        structure.add(chemical, multiplier);
        return true;
    }

    /**
     * Pattern for detecting molecules from strings (equation safe) Group 1: Matches the Number of Molecules (in an equation) eg 2He Group 2: Matches a Elemental Symbols eg. "Na" Group 3: Matches a
     * molecule grouping without the brackets eg (H2O)=>H2O Group 4: Matches a @(Chemical name) name to allow for shorthand so @water=>water=>H2O Group 5: Matches the multiplier for the preceding
     * element or molecule eg. H2=> H * 2 or C3PO=>(C * 3)(P * 1)(O * 1)
     *
     * @author Hilburn
     */
    public static Pattern molecule = Pattern.compile("(\\d*)(?:([A-Z][a-z]{0,2})|(?:\\((\\w+)\\))|(?:@([^\\d\\s]+)))(\\d*)");

    private static ChemicalStructure parseStructure(String formula)
    {
        if (isNull(formula))
        {
            throw new NullPointerException("Can't parse null or empty formula");
        }
        ChemicalStructure structure = new ChemicalStructure();
        Matcher matcher = molecule.matcher(formula);
        while (matcher.find())
        {
            int multiplier = toInteger(matcher.group(5));
            Element element = toElement(matcher.group(2));
            if (put(structure, element, multiplier))
            {
                continue;
            }
            Molecule chemMolecule = toMolecule(parseStructure(matcher.group(3)).getFormula(), false);
            if (put(structure, chemMolecule, multiplier))
            {
                continue;
            }
            Molecule definedMolecule = toMolecule(matcher.group(4), true);
            if (put(structure, definedMolecule, multiplier))
            {
                continue;
            }
            throw new IllegalArgumentException("Error parsing " + formula);
        }
        return structure;
    }

    public ResourceLocation getStructureResource()
    {
        return structureResource;
    }
}
