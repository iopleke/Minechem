package minechem.common;

import minechem.api.core.EnumElement;
import minechem.common.items.ItemAtomicManipulator;
import minechem.common.items.ItemBlueprint;
import minechem.common.items.ItemChemistJournal;
import minechem.common.items.ItemElement;
import minechem.common.items.ItemFusionStar;
import minechem.common.items.ItemLens;
import minechem.common.items.ItemMolecule;
import minechem.common.items.ItemPills;
import minechem.common.items.PhotonicInduction;
import minechem.common.polytool.ItemPolytool;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MinechemItems
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
    public static PhotonicInduction IAintAvinit;
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
            OreDictionary.registerOre("element" + element.descriptiveName(), new ItemStack(MinechemItems.element, 1, element.ordinal()));
        }
    }
}