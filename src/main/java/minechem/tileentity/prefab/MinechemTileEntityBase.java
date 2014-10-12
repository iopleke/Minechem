package minechem.tileentity.prefab;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public abstract class MinechemTileEntityBase extends TileEntity
{
    protected long ticks = 0;

    @Override
    public int getBlockMetadata()
    {
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }

    @Override
    public Block getBlockType()
    {
        if (this.blockType == null)
        {
            this.blockType = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockType;
    }

    /** Called on the TileEntity's first tick. */
    //TODO: Remove this once its reason for being is found
    //public void initiate()
    //{
    //}

    @Override
    public void updateEntity()
    {
        //if (this.ticks == 0)
        //{
            //this.initiate();
        //}

        if (this.ticks >= Long.MAX_VALUE)
        {
            this.ticks = 1;
        }

        this.ticks++;
    }
}
