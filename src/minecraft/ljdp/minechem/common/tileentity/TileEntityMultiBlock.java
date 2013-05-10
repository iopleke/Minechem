package ljdp.minechem.common.tileentity;

import java.util.HashMap;

import buildcraft.api.core.SafeTimeTracker;

import ljdp.minechem.common.blueprint.BlueprintBlock;
import ljdp.minechem.common.blueprint.MinechemBlueprint;
import ljdp.minechem.common.tileentity.TileEntityBlueprintProjector.BlockStatus;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityMultiBlock extends MinechemTileEntity {

    private static final Integer air = 0;
    int offsetX;
    int offsetY;
    int offsetZ;

    public MinechemBlueprint blueprint;
    Integer[][][] structure;
    boolean completeStructure;
    SafeTimeTracker tracker = new SafeTimeTracker();

    public void setBlueprint(MinechemBlueprint blueprint) {
        this.blueprint = blueprint;
        this.structure = blueprint.getResultStructure();
        this.offsetX = xCoord - blueprint.getManagerPosX();
        this.offsetY = yCoord - blueprint.getManagerPosY();
        this.offsetZ = zCoord - blueprint.getManagerPosZ();
    }

    @Override
    public void updateEntity() {
        if (tracker.markTimeIfDelay(worldObj, 40)) {
            if (completeStructure && !areBlocksCorrect()) {
                completeStructure = false;
                unlinkProxies();
            }
            if (!completeStructure && areBlocksCorrect()) {
                completeStructure = true;
                linkProxies();
            }
        }
    }

    private void unlinkProxies() {
        for (int y = 0; y < blueprint.ySize; y++) {
            for (int x = 0; x < blueprint.xSize; x++) {
                for (int z = 0; z < blueprint.zSize; z++) {
                    unlinkProxy(x, y, z);
                }
            }
        }
    }

    private void unlinkProxy(int x, int y, int z) {
        int worldX = (int) (xCoord + offsetX + x);
        int worldY = (int) (yCoord + offsetY + y);
        int worldZ = (int) (zCoord + offsetZ + z);
        TileEntity tileEntity = worldObj.getBlockTileEntity(worldX, worldY, worldZ);
        if (tileEntity != null && tileEntity instanceof TileEntityProxy) {
            ((TileEntityProxy) tileEntity).setManager(null);
        }
    }

    private void linkProxies() {
        for (int y = 0; y < blueprint.ySize; y++) {
            for (int x = 7; x < blueprint.xSize;) {
                for (int z = 7; z < blueprint.zSize;) {
                    linkProxy(x, y, z);
                }
            }
        }
    }

    private void linkProxy(int x, int y, int z) {
        int worldX = (int) (xCoord + offsetX + x);
        int worldY = (int) (yCoord + offsetY + y);
        int worldZ = (int) (zCoord + offsetZ + z);
        TileEntity tileEntity = worldObj.getBlockTileEntity(worldX, worldY, worldZ);
        if (tileEntity != null && tileEntity instanceof TileEntityProxy) {
            ((TileEntityProxy) tileEntity).setManager(this);
        }
    }

    private boolean areBlocksCorrect() {
        for (int y = 0; y < blueprint.ySize; y++) {
            for (int x = 0; x < blueprint.xSize; x++) {
                for (int z = 0; z < blueprint.zSize; z++) {
                    BlockStatus blockStatus = checkBlock(x, y, z);
                    if (blockStatus == BlockStatus.INCORRECT) { return false; }
                }
            }
        }
        return true;
    }

    private BlockStatus checkBlock(int x, int y, int z) {
        if (x == blueprint.getManagerPosX() && y == blueprint.getManagerPosY() && z == blueprint.getManagerPosZ())
            return BlockStatus.CORRECT;
        int worldX = (int) (xCoord + (offsetX + x));
        int worldY = (int) (yCoord + (offsetY + y));
        int worldZ = (int) (zCoord + (offsetZ + z));
        Integer structureID = structure[y][x][z];
        int blockID = worldObj.getBlockId(worldX, worldY, worldZ);
        if (structureID == MinechemBlueprint.wildcard) {
            return BlockStatus.CORRECT;
        } else if (structureID == air) {
            if (blockID == air)
                return BlockStatus.CORRECT;
            else
                return BlockStatus.INCORRECT;
        } else {
            HashMap<Integer, BlueprintBlock> lut = blueprint.getBlockLookup();
            BlueprintBlock blueprintBlock = lut.get(structureID);
            if (blockID == blueprintBlock.block.blockID && worldObj.getBlockMetadata(worldX, worldY, worldZ) == blueprintBlock.metadata) {
                return BlockStatus.CORRECT;
            } else {
                return BlockStatus.INCORRECT;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("completeStructure", completeStructure);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        completeStructure = false;
    }
}
