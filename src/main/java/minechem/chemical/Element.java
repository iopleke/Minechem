package minechem.chemical;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minechem.Compendium;
import minechem.helper.ColourHelper;
import minechem.helper.LogHelper;

/**
 * Data object for elements
 */
public class Element extends ChemicalBase implements Comparable<Element>
{
    private static final Matcher SHELL_ORDER = Pattern.compile("\\d+([a-z])").matcher("1s2s2p3s3p4s3d4p5s4d5p6s4f5d6p7s5f6d7p8s5g6f7d8p9s"); //Handles up to atomic number 170
    private static final int[] SUB_SHELL_ELECTRONS = new int[]
    {
        2, 6, 10, 14, 18
    };
    private static final String SUB_SHELL_STRING = "spdfg"; //Super Powered Dog Fights God

    public static enum Type
    {
        alkaliMetal, alkalineEarth, transitionMetal, basicMetal, semiMetal, nonMetal, halogen, nobleGas, lanthanide, actinide
    }

    public final int atomicNumber;
    public final int neutrons;
    public final String shortName;
    public final Type type;

    private int valenceElectronCount;
    private String valenceSubshellIdentifier;

    /**
     * Basic data storage object for elements
     *
     * @param atomicNumber the element's atomic number and proton count
     * @param fullName     the full name, eg "Gold"
     * @param shortName    the abbreviation, eg "Au"
     * @param form         solid, liquid, gas, or plasma
     * @param type         alkaliMetal, alkalineEarth, transitionMetal, basicMetal, semiMetal, nonMetal, halogen, nobleGas, lanthanide or actinide
     * @param neutrons     the number of neutrons in the element's nucleus
     */
    public Element(int atomicNumber, String fullName, String shortName, String form, String type, int neutrons)
    {
        super(fullName, form, Compendium.Color.TrueColor.transparent);
        this.atomicNumber = atomicNumber;
        this.type = Type.valueOf(type);
        this.colour = setColour();
        this.neutrons = neutrons;
        this.shortName = shortName;
        this.calculateValenceShells();
    }

    /**
     * Iterates over the {@code SHELL_ORDER} string, calculates the size of the sub-shell from it's type and subtracts it from the electron count. when that is count <= 0 the latest sub-shell is the
     * valence shell and full shell electrons + the count (which is normally -ve) gives the valence electrons. Can easily be modified to recover the Shell no. separately to the full "1s" value by
     * changing the SHELL_ORDER pattern to: (//d+)([a-z]) and the casting group(1) to an int, and using group(2) to find the sub-shell type
     */
    private void calculateValenceShells()
    {
        SHELL_ORDER.reset();
        int electronCount = atomicNumber, subShell = 0;
        while (electronCount > 0 && SHELL_ORDER.find())
        {
            subShell = SUB_SHELL_STRING.indexOf(SHELL_ORDER.group(1));
            electronCount -= SUB_SHELL_ELECTRONS[subShell];
        }
        setValenceShell(SUB_SHELL_ELECTRONS[subShell] + electronCount, SHELL_ORDER.group(0));
    }

    public int getValenceElectronCount()
    {
        return this.valenceElectronCount;
    }

    public String getValenceSubshellIdentifier()
    {
        return this.valenceSubshellIdentifier;
    }

    /**
     * Set all valence shell data
     *
     * @param valenceElectronCount      default number of electrons in the valence shell
     * @param valenceSubshellIdentifier identifier for the valence shell
     */
    public void setValenceShell(int valenceElectronCount, String valenceSubshellIdentifier)
    {
        this.valenceElectronCount = valenceElectronCount;
        this.valenceSubshellIdentifier = valenceSubshellIdentifier;
    }

    /**
     * Write element to the FML Log
     */
    @Override
    public void log()
    {
        LogHelper.debug("Atomic Number: " + this.atomicNumber);
        LogHelper.debug("Element name: " + this.fullName);
        LogHelper.debug("Element abbreviation: " + this.shortName);
        LogHelper.debug("Form: " + this.form);
        LogHelper.debug("Neutrons: " + this.neutrons);
    }

    public int setColour()
    {
        switch (this.type)
        {
            case alkaliMetal:
                return ColourHelper.RGB("#F63FFF");
            case alkalineEarth:
                return ColourHelper.RGB("#A84DFF");
            case transitionMetal:
                return ColourHelper.RGB("#3DD4FF");
            case basicMetal:
                return ColourHelper.RGB("#FFBA50");
            case semiMetal:
                return ColourHelper.RGB("#0AFF76");
            case nonMetal:
                return ColourHelper.RGB("#329EFF");
            case halogen:
                return ColourHelper.RGB("#FFCB08");
            case nobleGas:
                return ColourHelper.RGB("#FFD148");
            case lanthanide:
                return ColourHelper.RGB("#C2FF00");
            case actinide:
                return ColourHelper.RGB("#FF0D0B");
            default:
                return Compendium.Color.TrueColor.white;
        }
    }

    @Override
    public boolean isElement()
    {
        return true;
    }

    @Override
    public String getFormula()
    {
        return this.shortName;
    }

    @Override
    public List<String> getToolTip()
    {
        List<String> list = new LinkedList<String>();
        list.add("Form: " + this.form);
        list.add("Neutrons: " + this.neutrons);
        return list;
    }

    @Override
    public int compareTo(Element other)
    {
        return Integer.compare(this.atomicNumber, other.atomicNumber);
    }
}
