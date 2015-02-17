package minechem.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.apparatus.tier1.electrolysis.ElectrolysisBlock;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeBlock;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntity;
import minechem.Compendium;
import minechem.blocks.BlockLight;
import minechem.blocks.BlockRedstone;
import net.minecraft.block.Block;

public class BlockRegistry
{
    public static OpticalMicroscopeBlock opticalMicroscope;
    public static ElectrolysisBlock electrolysisBlock;
    public static BlockLight blockLight;
    public static BlockRedstone blockRedstone;

    public static void init()
    {
        opticalMicroscope = new OpticalMicroscopeBlock();
        GameRegistry.registerBlock(opticalMicroscope, opticalMicroscope.getUnlocalizedName());
        GameRegistry.registerTileEntity(OpticalMicroscopeTileEntity.class, Compendium.Naming.opticalMicroscope + "TileEntity");

        electrolysisBlock = new ElectrolysisBlock();
        GameRegistry.registerBlock(electrolysisBlock, electrolysisBlock.getUnlocalizedName());
        GameRegistry.registerTileEntity(ElectrolysisTileEntity.class, Compendium.Naming.electrolysis + "TileEntity");

        blockLight = new BlockLight();
        GameRegistry.registerBlock(blockLight, blockLight.getLocalizedName());

        blockRedstone = new BlockRedstone();
        GameRegistry.registerBlock(blockRedstone, blockRedstone.getLocalizedName());
    }
}
