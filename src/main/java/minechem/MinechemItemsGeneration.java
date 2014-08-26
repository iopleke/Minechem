package minechem;

import minechem.item.ItemAtomicManipulator;
import minechem.item.ItemLens;
import minechem.item.blueprint.ItemBlueprint;
import minechem.item.chemistjournal.ChemistJournalItem;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.fusionstar.FusionStarItem;
import minechem.item.molecule.MoleculeItem;
import minechem.item.polytool.PolytoolItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MinechemItemsGeneration
{
    public static ElementItem element;
    public static MoleculeItem molecule;
    public static ItemLens lens;
    public static ItemAtomicManipulator atomicManipulator;
    public static FusionStarItem fusionStar;
    public static ItemBlueprint blueprint;
    public static ChemistJournalItem journal;
    public static ItemStack convexLens;
    public static ItemStack concaveLens;
    public static ItemStack projectorLens;
    public static ItemStack microscopeLens;
    public static ItemStack minechempills;
    public static Item polytool;

    public static void registerItems()
    {
        element = new ElementItem();
        molecule = new MoleculeItem();
        lens = new ItemLens();
        atomicManipulator = new ItemAtomicManipulator();
        fusionStar = new FusionStarItem();
        blueprint = new ItemBlueprint();
        journal = new ChemistJournalItem();
        polytool = new PolytoolItem();

        concaveLens = new ItemStack(lens, 1, 0);
        convexLens = new ItemStack(lens, 1, 1);
        microscopeLens = new ItemStack(lens, 1, 2);
        projectorLens = new ItemStack(lens, 1, 3);
    }

    public static void registerToOreDictionary()
    {
        for (ElementEnum element : ElementEnum.values())
        {
            OreDictionary.registerOre("element" + element.descriptiveName(), new ItemStack(MinechemItemsGeneration.element, 1, element.ordinal()));
        }
    }
}