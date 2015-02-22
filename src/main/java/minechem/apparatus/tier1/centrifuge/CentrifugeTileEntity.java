package minechem.apparatus.tier1.centrifuge;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicInventoryTileEntity;

public class CentrifugeTileEntity extends BasicInventoryTileEntity
{

    public CentrifugeTileEntity()
    {
        super(Compendium.Naming.centrifuge, 2);
    }

    @Override
    public String getInventoryName()
    {
        return Compendium.Naming.centrifuge;
    }

    @Override
    public void updateEntity()
    {
    }

}
