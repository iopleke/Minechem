package minechem;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.item.ItemAtomicManipulator;
import minechem.item.OpticalMicroscopeLens;
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

public class MinechemItemsRegistration
{
    public static ElementItem element;
    public static MoleculeItem molecule;
    public static OpticalMicroscopeLens lens;
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

    public static void init()
    {
        element = new ElementItem();
        GameRegistry.registerItem(element, "minechemElement");
        
        molecule = new MoleculeItem();
        GameRegistry.registerItem(molecule, "minechemMolecule");
        
        lens = new OpticalMicroscopeLens();
        concaveLens = new ItemStack(lens, 1, 0);
        convexLens = new ItemStack(lens, 1, 1);
        microscopeLens = new ItemStack(lens, 1, 2);
        projectorLens = new ItemStack(lens, 1, 3);
        GameRegistry.registerItem(lens, "opticalMicroscopeLens");
        
        atomicManipulator = new ItemAtomicManipulator();
        GameRegistry.registerItem(atomicManipulator, "atomicManipulator");
        
        fusionStar = new FusionStarItem();
        GameRegistry.registerItem(fusionStar, "fusionStar");
        
        blueprint = new ItemBlueprint();
        GameRegistry.registerItem(blueprint, "blueprint");
        
        journal = new ChemistJournalItem();
        GameRegistry.registerItem(journal, "journal");
        
        polytool = new PolytoolItem();
        GameRegistry.registerItem(polytool, "polytool");

    }

    public static void registerToOreDictionary()
    {
        for (ElementEnum element : ElementEnum.values())
        {
            OreDictionary.registerOre("element" + element.descriptiveName(), new ItemStack(MinechemItemsRegistration.element, 1, element.ordinal()));
        }
    }
}