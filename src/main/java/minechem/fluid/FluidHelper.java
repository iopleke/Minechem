package minechem.fluid;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.IdentityHashMap;
import java.util.Map;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;

public class FluidHelper
{

	public static Map<MoleculeEnum, FluidChemical> molecules = new IdentityHashMap<MoleculeEnum, FluidChemical>();
	public static Map<ElementEnum, FluidElement> elements = new IdentityHashMap<ElementEnum, FluidElement>();

	public static Map<FluidChemical, FluidBlockChemical> moleculeBlocks = new IdentityHashMap<FluidChemical, FluidBlockChemical>();
	public static Map<FluidElement, FluidBlockElement> elementsBlocks = new IdentityHashMap<FluidElement, FluidBlockElement>();

	public static void registerElement(ElementEnum element)
	{
		FluidElement fluid = new FluidElement(element);
		elements.put(element, fluid);
		elementsBlocks.put(fluid, new FluidBlockElement(fluid));
		GameRegistry.registerBlock(elementsBlocks.get(fluid), fluid.getUnlocalizedName());
	}

	public static void registerMolecule(MoleculeEnum molecule)
	{
		FluidChemical fluid = new FluidChemical(molecule);
		molecules.put(molecule, fluid);
		moleculeBlocks.put(fluid, new FluidBlockChemical(fluid));
		GameRegistry.registerBlock(moleculeBlocks.get(fluid), fluid.getUnlocalizedName());
	}
}
