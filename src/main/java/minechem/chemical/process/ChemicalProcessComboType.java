package minechem.chemical.process;

import java.util.Arrays;

public class ChemicalProcessComboType extends ChemicalProcessType
{
    private ChemicalProcessType[] childTypes;

    public ChemicalProcessComboType(ChemicalProcessType... childTypes)
    {
        super(Arrays.toString(childTypes));
        this.childTypes = childTypes;
    }

    public ChemicalProcessType[] getChildTypes()
    {
        return childTypes;
    }
}
