package minechem.chemical.process;

import minechem.chemical.ChemicalBase;

import java.util.Random;

public class ChemicalChanceProcess extends ChemicalProcess
{
    private float chance;

    /**
     * A process that has a chance to give output
     * @param type the ChemicalProcessType
     * @param level the level needed
     * @param chance the chance in a float format min 0, max 1
     * @param components the output
     */
    public ChemicalChanceProcess(ChemicalProcessType type, int level, float chance, ChemicalBase... components)
    {
        super(type, level, components);
        this.chance = chance;
        if (this.chance > 1) this.chance = 1;
        if (this.chance < 0) this.chance = 0;
    }

    @Override
    public ChemicalBase[] getOutput(ChemicalProcessType type, int level)
    {
        if (new Random().nextFloat() > chance)
        {
            return super.getOutput(type, level);
        }
        return empty;
    }
}
