package minechem.chemical.process;

import minechem.chemical.ChemicalBase;

public class ChemicalProcess
{
    public static ChemicalBase[] empty = new ChemicalBase[0];
    private ChemicalProcessType type;
    private int level;
    private ChemicalBase[] output;

    /**
     * Create a process to bind to an ItemStack
     *
     * @param type       the ChemicalProcessType
     * @param level      the level needed
     * @param components the output components
     */
    public ChemicalProcess(ChemicalProcessType type, int level, ChemicalBase... components)
    {
        this.type = type;
        this.level = level;
        this.output = components;
    }

    public ChemicalBase[] getOutput(ChemicalProcessType type, int level)
    {
        if (this.type == type && level >= this.level)
        {
            return output;
        }
        return new ChemicalBase[0];
    }

    public ChemicalProcessType getType()
    {
        return type;
    }
}
