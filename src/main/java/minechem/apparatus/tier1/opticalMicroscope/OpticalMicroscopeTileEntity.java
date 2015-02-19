package minechem.apparatus.tier1.opticalMicroscope;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicInventoryTileEntity;

public class OpticalMicroscopeTileEntity extends BasicInventoryTileEntity
{

    public OpticalMicroscopeTileEntity()
    {
        super(Compendium.Naming.opticalMicroscope, 1);
    }

    @Override
    public String getInventoryName()
    {
        return Compendium.Naming.opticalMicroscope;
    }

    @Override
    public void updateEntity()
    {
    }

}
