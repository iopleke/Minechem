package minechem.tileentity.multiblock;

import java.util.HashMap;
import minechem.item.blueprint.BlueprintBlock;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.tileentity.prefab.MinechemTileEntityElectric;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.utils.SafeTimeTracker;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class MultiBlockTileEntity extends MinechemTileEntityElectric
{

    public MultiBlockTileEntity(int maxEnergy)
    {
        super(maxEnergy);
    }

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
        TileEntity tileEntity = worldObj.getTileEntity(worldX, worldY, worldZ);
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
        TileEntity tileEntity = worldObj.getTileEntity(worldX, worldY, worldZ);
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
                    MultiBlockStatusEnum multiBlockStatusEnum = checkBlock(x, y, z);
                    if (multiBlockStatusEnum == MultiBlockStatusEnum.INCORRECT)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private MultiBlockStatusEnum checkBlock(int x, int y, int z)
    {
        if (x == blueprint.getManagerPosX() && y == blueprint.getManagerPosY() && z == blueprint.getManagerPosZ())
        {
            return MultiBlockStatusEnum.CORRECT;
        }
        int worldX = xCoord + (offsetX + x);
        int worldY = yCoord + (offsetY + y);
        int worldZ = zCoord + (offsetZ + z);
        Integer structureID = structure[y][x][z];
        Block block = worldObj.getBlock(worldX, worldY, worldZ);
        if (structureID == MinechemBlueprint.wildcard)
        {
            return MultiBlockStatusEnum.CORRECT;
        } else if (structureID == air)
        {
            if (block == Blocks.air)
            {
                return MultiBlockStatusEnum.CORRECT;
            } else
            {
                return MultiBlockStatusEnum.INCORRECT;
            }
        } else
        {
            HashMap<Integer, BlueprintBlock> lut = blueprint.getBlockLookup();
            BlueprintBlock blueprintBlock = lut.get(structureID);
            if (block == blueprintBlock.block && worldObj.getBlockMetadata(worldX, worldY, worldZ) == blueprintBlock.metadata)
            {
                return MultiBlockStatusEnum.CORRECT;
            } else
            {
                return MultiBlockStatusEnum.INCORRECT;
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
