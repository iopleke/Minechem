package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.tileEntity.BasicTileEntity;
import minechem.reference.Compendium;

public class ElectrolysisTileEntity extends BasicTileEntity
{

    public ElectrolysisTileEntity()
    {
        super(2);
    }

    @Override
    public String getInventoryName()
    {
        return Compendium.Naming.electrolysis;
    }

    @Override
    public void updateEntity()
    {
    }

}
