package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.tileEntity.BasicInventoryTileEntity;
import minechem.Compendium;

public class ElectrolysisTileEntity extends BasicInventoryTileEntity
{

    public ElectrolysisTileEntity()
    {
        super(Compendium.Naming.electrolysis,2);
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
