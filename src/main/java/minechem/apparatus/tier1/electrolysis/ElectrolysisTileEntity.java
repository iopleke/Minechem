package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.tileEntity.BasicInventoryTileEntity;
import minechem.reference.Compendium;

public class ElectrolysisTileEntity extends BasicInventoryTileEntity
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
