package minechem.tileentity.multiblock;

import java.util.HashMap;

import minechem.item.blueprint.BlueprintBlock;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.tileentity.blueprintprojector.TileEntityBlueprintProjector;
import minechem.tileentity.prefab.MinechemTileEntity;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.utils.SafeTimeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityMultiBlock extends MinechemTileEntity
{

    private static final Integer air = 0;
    int offsetX;
    int offsetY;
    int offsetZ;

    public MinechemBlueprint blueprint;
    Integer[][][] structure;
    protected boolean completeStructure;
    SafeTimeTracker tracker = new SafeTimeTracker();

    public void setBlueprint(MinechemBlueprint blueprint)
    {
        this.blueprint = blueprint;
        this.structure = blueprint.getResultStructure();
        this.offsetX = xCoord - blueprint.getManagerPosX();
        this.offsetY = yCoord - blueprint.getManagerPosY();
        this.offsetZ = zCoord - blueprint.getManagerPosZ();
    }

    @Override
    public void updateEntity()
    {
        if (tracker.markTimeIfDelay(worldObj, 40))
        {
            if (completeStructure && !areBlocksCorrect())
            {
                completeStructure = false;
                unlinkProxies();
            }
            if (!completeStructure && areBlocksCorrect())
            {
                completeStructure = true;
                linkProxies();

            }
        }
    }

    private void unlinkProxies()
    {
        for (int y = 0; y < blueprint.ySize; y++)
        {
            for (int x = 0; x < blueprint.xSize; x++)
            {
                for (int z = 0; z < blueprint.zSize; z++)
                {
                    unlinkProxy(x, y, z);
                }
            }
        }
    }

    private void unlinkProxy(int x, int y, int z)
    {
        int worldX = xCoord + offsetX + x;
        int worldY = yCoord + offsetY + y;
        int worldZ = zCoord + offsetZ + z;
        TileEntity tileEntity = worldObj.getBlockTileEntity(worldX, worldY, worldZ);
        if (tileEntity != null && tileEntity instanceof TileEntityProxy)
        {
            ((TileEntityProxy) tileEntity).setManager(null);
        }
    }

    private void linkProxies()
    {
        for (int y = 0; y < blueprint.ySize; y++)
        {
            for (int x = 0; x < blueprint.xSize; x++)
            {
                for (int z = 0; z < blueprint.zSize; z++)
                {
                    linkProxy(x, y, z);
                }
            }
        }
    }

    private void linkProxy(int x, int y, int z)
    {
        int worldX = xCoord + offsetX + x;
        int worldY = yCoord + offsetY + y;
        int worldZ = zCoord + offsetZ + z;
        HashMap<Integer, BlueprintBlock> lut = blueprint.getBlockLookup();
        TileEntity tileEntity = worldObj.getBlockTileEntity(worldX, worldY, worldZ);
        if (tileEntity != null && tileEntity instanceof TileEntityProxy)
        {
            ((TileEntityProxy) tileEntity).setManager(this);
        }
    }

    private boolean areBlocksCorrect()
    {
        for (int y = 0; y < blueprint.ySize; y++)
        {
            for (int x = 0; x < blueprint.xSize; x++)
            {
                for (int z = 0; z < blueprint.zSize; z++)
                {
                    EnumBlockStatus enumBlockStatus = checkBlock(x, y, z);
                    if (enumBlockStatus == EnumBlockStatus.INCORRECT)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private EnumBlockStatus checkBlock(int x, int y, int z)
    {
        if (x == blueprint.getManagerPosX() && y == blueprint.getManagerPosY() && z == blueprint.getManagerPosZ())
            return EnumBlockStatus.CORRECT;
        int worldX = xCoord + (offsetX + x);
        int worldY = yCoord + (offsetY + y);
        int worldZ = zCoord + (offsetZ + z);
        Integer structureID = structure[y][x][z];
        int blockID = worldObj.getBlockId(worldX, worldY, worldZ);
        if (structureID == MinechemBlueprint.wildcard)
        {
            return EnumBlockStatus.CORRECT;
        }
        else if (structureID == air)
        {
            if (blockID == air)
                return EnumBlockStatus.CORRECT;
            else
                return EnumBlockStatus.INCORRECT;
        }
        else
        {
            HashMap<Integer, BlueprintBlock> lut = blueprint.getBlockLookup();
            BlueprintBlock blueprintBlock = lut.get(structureID);
            if (blockID == blueprintBlock.block.blockID && worldObj.getBlockMetadata(worldX, worldY, worldZ) == blueprintBlock.metadata)
            {
                return EnumBlockStatus.CORRECT;
            }
            else
            {
                return EnumBlockStatus.INCORRECT;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("completeStructure", completeStructure);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        completeStructure = false;
    }
}
