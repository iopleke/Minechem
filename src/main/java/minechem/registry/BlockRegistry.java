package minechem.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.Compendium;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleBlock;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.tier1.electrolysis.ElectrolysisBlock;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeBlock;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntity;
import minechem.blocks.BlockLight;
import minechem.blocks.BlockRedstone;

public class BlockRegistry
{
    public static OpticalMicroscopeBlock opticalMicroscope;
    public static ElectrolysisBlock electrolysisBlock;
    public static ElectricCrucibleBlock electricCrucibleBlock;
    public static BlockLight blockLight;
    public static BlockRedstone blockRedstone;

    public static void init()
    {
        opticalMicroscope = new OpticalMicroscopeBlock();
        GameRegistry.registerBlock(opticalMicroscope, opticalMicroscope.getUnlocalizedName());
        GameRegistry.registerTileEntity(OpticalMicroscopeTileEntity.class, Compendium.Naming.opticalMicroscope + "TileEntity");

        electricCrucibleBlock = new ElectricCrucibleBlock();
        GameRegistry.registerBlock(electricCrucibleBlock, electricCrucibleBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(ElectricCrucibleTileEntity.class, Compendium.Naming.electricCrucible + "TileEntity");

        electrolysisBlock = new ElectrolysisBlock();
        GameRegistry.registerBlock(electrolysisBlock, electrolysisBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(ElectrolysisTileEntity.class, Compendium.Naming.electrolysis + "TileEntity");

        blockLight = new BlockLight();
        GameRegistry.registerBlock(blockLight, blockLight.getLocalizedName());

        blockRedstone = new BlockRedstone();
        GameRegistry.registerBlock(blockRedstone, blockRedstone.getLocalizedName());
    }
}
