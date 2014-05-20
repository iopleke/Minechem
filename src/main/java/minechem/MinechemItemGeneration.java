package minechem;

import minechem.item.ItemAtomicManipulator;
import minechem.item.ItemLens;
import minechem.item.ItemPills;
import minechem.item.blueprint.ItemBlueprint;
import minechem.item.chemistjournal.ItemChemistJournal;
import minechem.item.element.EnumElement;
import minechem.item.element.ItemElement;
import minechem.item.fusionstar.ItemFusionStar;
import minechem.item.molecule.ItemMolecule;
import minechem.item.polytool.ItemPolytool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MinechemItemGeneration
{
    public static ItemElement element;
    public static ItemMolecule molecule;
    public static ItemLens lens;
    public static ItemAtomicManipulator atomicManipulator;
    public static ItemFusionStar fusionStar;
    public static ItemBlueprint blueprint;
    public static ItemChemistJournal journal;
    public static ItemStack convexLens;
    public static ItemStack concaveLens;
    public static ItemStack projectorLens;
    public static ItemStack microscopeLens;
    public static ItemPills EmptyPillz;
    public static ItemStack minechempills;
    public static Item polytool;

    public static void registerItems()
    {
        element = new ItemElement(Settings.Element);
        molecule = new ItemMolecule(Settings.Molecule);
        lens = new ItemLens(Settings.Lens);
        atomicManipulator = new ItemAtomicManipulator(Settings.AtomicManipulator);
        fusionStar = new ItemFusionStar(Settings.FusionStar);
        blueprint = new ItemBlueprint(Settings.Blueprint);
        journal = new ItemChemistJournal(Settings.ChemistJournal);
        EmptyPillz = new ItemPills(Settings.EmptyPills, 0);
        polytool = new ItemPolytool(Settings.Polytool);

        concaveLens = new ItemStack(lens, 1, 0);
        convexLens = new ItemStack(lens, 1, 1);
        microscopeLens = new ItemStack(lens, 1, 2);
        projectorLens = new ItemStack(lens, 1, 3);
        minechempills = new ItemStack(EmptyPillz, 1, 0);
    }

    public static void registerToOreDictionary()
    {
        for (EnumElement element : EnumElement.values())
        {
            OreDictionary.registerOre("element" + element.descriptiveName(), new ItemStack(MinechemItemGeneration.element, 1, element.ordinal()));
        }
    }
}