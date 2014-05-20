package minechem.fluid;

import java.util.HashMap;

import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;

public class FluidHelper
{

    public static HashMap<MoleculeEnum, FluidChemical> molecule = new HashMap();
    public static final int FLUID_CONSTANT = 5;
    public static HashMap<ElementEnum, FluidElement> elements = new HashMap();

    public static void registerFluids()
    {
        for (MoleculeEnum moleculeToCreate : MoleculeEnum.values())
        {
            molecule.put(moleculeToCreate, new FluidChemical(moleculeToCreate));
        }
        for (ElementEnum moleculeToCreate : ElementEnum.values())
        {
            elements.put(moleculeToCreate, new FluidElement(moleculeToCreate));
        }
    }

}
