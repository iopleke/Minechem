package minechem.fluid;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.IdentityHashMap;
import java.util.Map;
import minechem.Minechem;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;

public class FluidHelper
{

    public static Map<MoleculeEnum, FluidMolecule> molecules = new IdentityHashMap<MoleculeEnum, FluidMolecule>();
    public static Map<ElementEnum, FluidElement> elements = new IdentityHashMap<ElementEnum, FluidElement>();

    public static Map<FluidMolecule, FluidBlockMolecule> moleculeBlocks = new IdentityHashMap<FluidMolecule, FluidBlockMolecule>();
    public static Map<FluidElement, FluidBlockElement> elementsBlocks = new IdentityHashMap<FluidElement, FluidBlockElement>();

    public static void registerElement(ElementEnum element)
    {
        FluidElement fluid = new FluidElement(element);
        elements.put(element, fluid);
        elementsBlocks.put(fluid, new FluidBlockElement(fluid));
        GameRegistry.registerBlock(elementsBlocks.get(fluid), fluid.getUnlocalizedName());
        Minechem.PROXY.onAddFluid(fluid, elementsBlocks.get(fluid));
        MinechemBucketHandler.getInstance().registerCustomMinechemBucket(elementsBlocks.get(fluid), element, "element.");
    }

    public static void registerMolecule(MoleculeEnum molecule)
    {
        FluidMolecule fluid = new FluidMolecule(molecule);
        molecules.put(molecule, fluid);
        moleculeBlocks.put(fluid, new FluidBlockMolecule(fluid));
        GameRegistry.registerBlock(moleculeBlocks.get(fluid), fluid.getUnlocalizedName());
        Minechem.PROXY.onAddFluid(fluid, moleculeBlocks.get(fluid));
        MinechemBucketHandler.getInstance().registerCustomMinechemBucket(moleculeBlocks.get(fluid), molecule, "molecule.");
    }
}
