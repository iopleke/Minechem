package minechem.chemical.process;

import minechem.chemical.ChemicalBase;

import java.util.Random;

public class ChemicalMultiProcess extends ChemicalProcess
{
    private ChemicalBase[][] outputs;

    /**
     * Process that has different possible outputs, chance will be equally divided
     * @param type the ChemicalProcessType
     * @param level the level needed
     * @param components arrays of possible outputs
     */
    public ChemicalMultiProcess(ChemicalProcessType type, int level, ChemicalBase[]... components)
    {
        super(type, level);
        outputs = components;
    }

    @Override
    public ChemicalBase[] getOutput(ChemicalProcessType type, int level)
    {
        if (super.getOutput(type, level) != empty)
        {
            return outputs[new Random().nextInt(outputs.length)];
        }
        return empty;
    }
}
