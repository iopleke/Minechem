package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.tileEntity.BasicInventoryTileEntity;
import minechem.reference.Compendium;

public class OpticalMicroscopeTileEntity extends BasicInventoryTileEntity
{

    public OpticalMicroscopeTileEntity()
    {
        super(1);
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
