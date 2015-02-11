package minechem.chemical;

/**
 * The base for chemicals This class will hold all functions that will shared by the {@link minechem.chemical.Element} and {@link minechem.chemical.Molecule} This way they can be handled as being the
 * same
 */
public abstract class ChemicalBase
{
    public static enum Form
    {
        solid, liquid, gas, plasma
    }

    /**
     * mcTemp = floor(celsiusTemp*10.0)
     */
    public final int temp;
    public final int meltingPoint;
    public final int boilingPoint;
    public final String fullName;

    /**
     * Used for creating a ChemicalBase
     * 
     * @param fullName
     * @param temp
     * @param meltingPoint
     * @param boilingPoint
     */
    public ChemicalBase(String fullName, int meltingPoint, int boilingPoint, int temp)
    {
        this.fullName = fullName;
        this.temp = temp;
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
    }

    /**
     * Used for creating a ChemicalBase with deault temp(200, celsius:20)
     * 
     * @param fullName
     * @param meltingPoint
     * @param boilingPoint
     */
    public ChemicalBase(String fullName, int meltingPoint, int boilingPoint)
    {
        this(fullName, meltingPoint, boilingPoint, 200);
    }

    public abstract String getDebugInfo();

    /**
     * Get the short name for the {@link minechem.chemical.ChemicalBase}
     *
     * @return a short String representation
     */
    public abstract String getFormula();

    @Override
    public String toString()
    {
        return getFormula();
    }

    public Form getForm(int temp)
    {
        if (temp >= boilingPoint)
        {
            return Form.gas;
        } else if (temp >= meltingPoint)
        {
            return Form.liquid;
        } else
        {
            return Form.solid;
        }
    }

    public Form getForm()
    {
        return getForm(temp);
    }
}
