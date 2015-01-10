package minechem.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data object for elements
 */
public class Element
{
    private static final Matcher SHELL_ORDER = Pattern.compile("\\d+([a-z])").matcher("1s2s2p3s3p4s3d4p5s4d5p6s4f5d6p7s5f6d7p8s5g6f7d8p9s"); //Handles up to atomic number 170
    private static final int[] SUB_SHELL_ELECTRONS = new int[]
    {
        2, 6, 10, 14, 18
    };
    private static final String SUB_SHELL_STRING = "spdfg";																					//Super Powered Dog Fights God

    public final int atomicNumber;
    public final String fullName;
    public final String shortName;
    public final String form;
    public final int neutrons;

    private int valenceElectronCount;
    private String valenceSubshellIdentifier;

    /**
     * Basic data storage object for elements
     *
     * @param atomicNumber the element's atomic number and proton count
     * @param fullName the full name, eg "Gold"
     * @param shortName the abbreviation, eg "Au"
     * @param form solid, liquid, gas, or plasma
     * @param neutrons the number of neutrons in the element's nucleus
     */
    public Element(int atomicNumber, String fullName, String shortName, String form, int neutrons)
    {
        this.atomicNumber = atomicNumber;
        this.fullName = fullName;
        this.shortName = shortName;
        this.form = form;
        this.neutrons = neutrons;
        this.calculateValenceShells();
    }

    /**
     * Set all valence shell data
     *
     * @param valenceElectronCount default number of electrons in the valence shell
     * @param valenceSubshellIdentifier identifier for the valence shell
     */
    public void setValenceShell(int valenceElectronCount, String valenceSubshellIdentifier)
    {
        this.valenceElectronCount = valenceElectronCount;
        this.valenceSubshellIdentifier = valenceSubshellIdentifier;
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
}
