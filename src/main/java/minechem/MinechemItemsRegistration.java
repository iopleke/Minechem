package minechem;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.fluid.FluidHelper;
import minechem.item.ItemAtomicManipulator;
import minechem.item.OpticalMicroscopeLens;
import minechem.item.blueprint.ItemBlueprint;
import minechem.item.chemistjournal.ChemistJournalItem;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.item.polytool.PolytoolItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class MinechemItemsRegistration
{
	public static ElementItem element;
	public static MoleculeItem molecule;
	public static OpticalMicroscopeLens lens;
	public static ItemAtomicManipulator atomicManipulator;
	public static ItemBlueprint blueprint;
	public static ChemistJournalItem journal;
	public static ItemStack convexLens;
	public static ItemStack concaveLens;
	public static ItemStack projectorLens;
	public static ItemStack microscopeLens;
	public static ItemStack minechempills;
	public static Item polytool;

	public static void init()
	{
		element = new ElementItem();
		GameRegistry.registerItem(element, Minechem.ID + "Element");

		molecule = new MoleculeItem();
		GameRegistry.registerItem(molecule, Minechem.ID + "Molecule");

		lens = new OpticalMicroscopeLens();
		concaveLens = new ItemStack(lens, 1, 0);
		convexLens = new ItemStack(lens, 1, 1);
		microscopeLens = new ItemStack(lens, 1, 2);
		projectorLens = new ItemStack(lens, 1, 3);
		GameRegistry.registerItem(lens, Minechem.ID + "OpticalMicroscopeLens");

		atomicManipulator = new ItemAtomicManipulator();
		GameRegistry.registerItem(atomicManipulator, Minechem.ID + "AtomicManipulator");

		blueprint = new ItemBlueprint();
		GameRegistry.registerItem(blueprint, Minechem.ID + "Blueprint");

		journal = new ChemistJournalItem();
		GameRegistry.registerItem(journal, Minechem.ID + "Journal");

		polytool = new PolytoolItem();
		GameRegistry.registerItem(polytool, Minechem.ID + "Polytool");

		registerFluidContainers();
	}

	private static void registerFluidContainers()
	{
		ItemStack emptyTube = new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.heaviestMass);
		for (ElementEnum element : ElementEnum.elements)
		{
			if (element != null)
			{
				ItemStack tube = new ItemStack(MinechemItemsRegistration.element, 1, element.ordinal());
				FluidContainerRegistry.registerFluidContainer(FluidHelper.elements.get(element), tube, emptyTube);
			}
		}

		for (MoleculeEnum molecule : MoleculeEnum.molecules.values())
		{
			if (molecule != null)
			{
				ItemStack tube = new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id());
				FluidContainerRegistry.registerFluidContainer(FluidHelper.molecules.get(molecule), tube, emptyTube);
			}
		}
	}

	public static void registerToOreDictionary()
	{
		for (ElementEnum element : ElementEnum.elements)
		{
			if (element != null)
			{
				OreDictionary.registerOre("element_" + element.name(), new ItemStack(MinechemItemsRegistration.element, 1, element.ordinal()));
			}
		}
		OreDictionary.registerOre("dustSaltpeter", new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.potassiumNitrate.id()));
		OreDictionary.registerOre("dustSalt", new ItemStack(MinechemItemsRegistration.molecule, 1, MoleculeEnum.salt.id()));
		OreDictionary.registerOre("quicksilver", new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.Hg.ordinal()));
	}
}
