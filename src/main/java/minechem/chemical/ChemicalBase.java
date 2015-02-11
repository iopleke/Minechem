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

    public final Form form;// TODO: this should become a temperature so that the state can be defined on that maybe?
    public final String fullName;

    public ChemicalBase(String fullName, String form)
    {
        this.fullName = fullName;
        this.form = Form.valueOf(form);
    }

    /**
     * Used for logging the {@link minechem.chemical.ChemicalBase} to the {@link cpw.mods.fml.common.FMLLog}
     */
    public abstract void log();

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
}
