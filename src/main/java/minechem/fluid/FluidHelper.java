package minechem.fluid;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;
import java.util.HashMap;

public class FluidHelper
{

    public static HashMap<MoleculeEnum, FluidChemical> molecule = new HashMap();
    public static HashMap<ElementEnum, FluidElement> elements = new HashMap();

    public static HashMap<FluidChemical, FluidBlockChemical> moleculeBlocks = new HashMap();
    public static HashMap<FluidElement, FluidBlockElement> elementsBlocks = new HashMap();

    public static void registerFluids()
    {
        for (MoleculeEnum moleculeToCreate : MoleculeEnum.molecules)
        {
        	if (moleculeToCreate!=null){
        		molecule.put(moleculeToCreate, new FluidChemical(moleculeToCreate));
        	}
        }
        for (ElementEnum moleculeToCreate : ElementEnum.elements)
        {
        	if (moleculeToCreate!=null){
        		elements.put(moleculeToCreate, new FluidElement(moleculeToCreate));
        	}
        }
    }


    public static void registerFluidBlock()
    {
        for (FluidElement fluid : elements.values())
        {
            elementsBlocks.put(fluid, new FluidBlockElement(fluid));
            GameRegistry.registerBlock(elementsBlocks.get(fluid), fluid.getUnlocalizedName());
        }
        for (FluidChemical fluid : molecule.values())
        {
            moleculeBlocks.put(fluid, new FluidBlockChemical(fluid));
            GameRegistry.registerBlock(moleculeBlocks.get(fluid), fluid.getUnlocalizedName());
        }
    }

}
