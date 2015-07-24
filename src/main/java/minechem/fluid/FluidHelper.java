package minechem.fluid;

import java.util.IdentityHashMap;
import java.util.Map;
import minechem.Minechem;
import minechem.item.MinechemChemicalType;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;
import cpw.mods.fml.common.registry.GameRegistry;

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

    public static MinechemFluid getFluid(MinechemChemicalType chemical)
    {
        if (chemical instanceof ElementEnum)
        {
            return elements.get(chemical);
        } else if (chemical instanceof MoleculeEnum)
        {
            return molecules.get(chemical);
        }
        return null;
    }

    public static MinechemFluidBlock getFluidBlock(MinechemFluid fluid)
    {
        if (fluid instanceof FluidElement)
        {
            return elementsBlocks.get(fluid);
        } else if (fluid instanceof FluidMolecule)
        {
            return moleculeBlocks.get(fluid);
        }
        return null;
    }

    public static MinechemFluidBlock getFluidBlock(MinechemChemicalType chemical)
    {
        if (chemical instanceof ElementEnum)
        {
            return elementsBlocks.get(elements.get(chemical));
        } else if (chemical instanceof MoleculeEnum)
        {
            return moleculeBlocks.get(molecules.get(chemical));
        }
        return null;
    }
}
