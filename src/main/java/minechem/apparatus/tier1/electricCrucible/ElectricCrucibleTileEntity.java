package minechem.apparatus.tier1.electricCrucible;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicInventoryTileEntity;

public class ElectricCrucibleTileEntity extends BasicInventoryTileEntity
{

    public ElectricCrucibleTileEntity()
    {
        super(Compendium.Naming.electricCrucible, 2);
    }

    @Override
    public String getInventoryName()
    {
        return Compendium.Naming.electricCrucible;
    }

    @Override
    public void updateEntity()
    {
    }

}
