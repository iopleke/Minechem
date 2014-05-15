package pixlepix.minechem.common;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.common.items.*;
import pixlepix.minechem.common.polytool.ItemPolytool;
import pixlepix.minechem.common.utils.MinechemHelper;

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