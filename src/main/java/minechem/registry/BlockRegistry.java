package minechem.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeBlock;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntity;
import minechem.reference.Compendium;

public class BlockRegistry
{
    public static OpticalMicroscopeBlock opticalMicroscope;

    public static void init()
    {
        opticalMicroscope = new OpticalMicroscopeBlock();
        GameRegistry.registerBlock(opticalMicroscope, opticalMicroscope.getUnlocalizedName());
        GameRegistry.registerTileEntity(OpticalMicroscopeTileEntity.class, Compendium.Naming.opticalMicroscope + "TileEntity");
    }
}
