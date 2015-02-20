package minechem.apparatus.prefab.tileEntity;

import net.minecraft.tileentity.TileEntity;

public abstract class BaseTileEntity extends TileEntity
{
    @Override
    public int getBlockMetadata()
    {
        return worldObj != null ? super.getBlockMetadata() : 0;
    }
}
