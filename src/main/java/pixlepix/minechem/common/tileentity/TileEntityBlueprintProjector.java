package pixlepix.minechem.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.minechem.common.MinechemBlocks;
import pixlepix.minechem.common.MinechemItems;
import pixlepix.minechem.common.blueprint.BlueprintBlock;
import pixlepix.minechem.common.blueprint.BlueprintBlock.Type;
import pixlepix.minechem.common.blueprint.MinechemBlueprint;
import pixlepix.minechem.common.sound.LoopingSound;
import pixlepix.minechem.common.utils.LocalPosition;
import pixlepix.minechem.common.utils.LocalPosition.Pos3;
import pixlepix.minechem.common.utils.MinechemHelper;

import java.util.HashMap;

public class TileEntityBlueprintProjector extends MinechemTileEntity {

    private static int air;
    MinechemBlueprint blueprint;

    enum BlockStatus {
        CORRECT,
        INCORRECT
    }

    boolean isComplete = false;
    Integer[][][] structure;
    LoopingSound projectorSound;

    public TileEntityBlueprintProjector() {
        this.projectorSound = new LoopingSound("pixlepix.minechem.projector", 20);
        this.projectorSound.setVolume(.2F);
        this.inventory = new ItemStack[getSizeInventory()];
    }

    @Override
    public void updateEntity() {
        if (blueprint != null && !isComplete) {
            projectBlueprint();
            this.projectorSound.play(worldObj, xCoord, yCoord, zCoord);
        }
    }

    private void projectBlueprint() {
        int facing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection direction = MinechemHelper.getDirectionFromFacing(facing);
        LocalPosition position = new LocalPosition(xCoord, yCoord, zCoord, direction);
        position.moveForwards(blueprint.zSize + 1);
        position.moveLeft(Math.floor(blueprint.xSize / 2));
        boolean shouldProjectGhostBlocks = true;
        int totalIncorrectCount = blueprint.getTotalSize();

        for (int x = 0; x < blueprint.xSize; x++) {
            int verticalIncorrectCount = blueprint.getVerticalSliceSize();
            for (int y = 0; y < blueprint.ySize; y++) {
                for (int z = 0; z < blueprint.zSize; z++) {
                    if (shouldProjectGhostBlocks) {
                        BlockStatus blockStatus;
                        if (isManagerBlock(x, y, z))
                            blockStatus = BlockStatus.CORRECT;
                        else
                            blockStatus = projectGhostBlock(x, y, z, position);
                        if (blockStatus == BlockStatus.CORRECT) {
                            verticalIncorrectCount--;
                            totalIncorrectCount--;
                        }
                    } else {
                        destroyGhostBlock(x, y, z, position);
                    }
                }
            }
            if (verticalIncorrectCount != 0)
                shouldProjectGhostBlocks = false;
        }

        if (totalIncorrectCount == 0 && !isComplete) {
            isComplete = true;
            buildStructure(position);
        }
    }

    private void buildStructure(LocalPosition position) {
        Integer[][][] resultStructure = blueprint.getResultStructure();
        HashMap<Integer, BlueprintBlock> blockLookup = blueprint.getBlockLookup();

        TileEntity managerTileEntity = buildManagerBlock(position);

        for (int x = 0; x < blueprint.xSize; x++) {
            for (int y = 0; y < blueprint.ySize; y++) {
                for (int z = 0; z < blueprint.zSize; z++) {
                    if (isManagerBlock(x, y, z))
                        continue;
                    int structureId = resultStructure[y][x][z];
                    setBlock(x, y, z, position, structureId, blockLookup, managerTileEntity);
                }
            }
        }
    }

    private boolean isManagerBlock(int x, int y, int z) {
        return x == blueprint.getManagerPosX() && y == blueprint.getManagerPosY() && z == blueprint.getManagerPosZ();
    }

    private TileEntity buildManagerBlock(LocalPosition position) {
        BlueprintBlock managerBlock = blueprint.getManagerBlock();
        if (managerBlock != null) {
            Pos3 worldPos = position.getLocalPos(blueprint.getManagerPosX(), blueprint.getManagerPosY(), blueprint.getManagerPosZ());
            worldObj.setBlock(worldPos.x, worldPos.y, worldPos.z, managerBlock.block.blockID, managerBlock.metadata, 3);
            if (this.blueprint == MinechemBlueprint.fusion) {
                TileEntityFusion fusion = new TileEntityFusion();
                fusion.worldObj = this.worldObj;
                fusion.xCoord = worldPos.x;
                fusion.yCoord = worldPos.y;
                fusion.zCoord = worldPos.z;
                fusion.blockType = MinechemBlocks.fusion;
                worldObj.setBlockTileEntity(xCoord, yCoord, zCoord, fusion);
            }
            return worldObj.getBlockTileEntity(worldPos.x, worldPos.y, worldPos.z);
        } else {
            return null;
        }
    }

    private void setBlock(int x, int y, int z, LocalPosition position, int structureId, HashMap<Integer, BlueprintBlock> blockLookup,
                          TileEntity managerTileEntity) {
        Pos3 worldPos = position.getLocalPos(x, y, z);
        if (structureId == MinechemBlueprint.wildcard) {
            return;
        }
        if (structureId == air) {
            worldObj.setBlockToAir(worldPos.x, worldPos.y, worldPos.z);
        } else {
            BlueprintBlock blueprintBlock = blockLookup.get(structureId);
            if (blueprintBlock.type == Type.MANAGER)
                return;
            worldObj.setBlock(worldPos.x, worldPos.y, worldPos.z, blueprintBlock.block.blockID, blueprintBlock.metadata, 3);
            if (blueprintBlock.type == Type.PROXY) {
                TileEntity te = worldObj.getBlockTileEntity(worldPos.x, worldPos.y, worldPos.z);
                if (te instanceof TileEntityProxy) {
                    TileEntityProxy proxy = (TileEntityProxy) te;
                }
            }


        }
    }

    private BlockStatus projectGhostBlock(int x, int y, int z, LocalPosition position) {
        Pos3 worldPos = position.getLocalPos(x, y, z);
        Integer structureID = structure[y][x][z];
        int blockID = worldObj.getBlockId(worldPos.x, worldPos.y, worldPos.z);
        int blockMetadata = worldObj.getBlockMetadata(worldPos.x, worldPos.y, worldPos.z);
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
            if (blockID == air) {
                createGhostBlock(worldPos.x, worldPos.y, worldPos.z, structureID);
                return BlockStatus.INCORRECT;
            } else if (blockID == blueprintBlock.block.blockID && (blockMetadata == blueprintBlock.metadata || blueprintBlock.metadata == -1)) {
                return BlockStatus.CORRECT;
            } else {
                return BlockStatus.INCORRECT;
            }
        }
    }

    private void createGhostBlock(int x, int y, int z, int blockID) {
        worldObj.setBlock(x, y, z, MinechemBlocks.ghostBlock.blockID, 0, 3);
        TileEntity tileEntity = worldObj.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityGhostBlock) {
            TileEntityGhostBlock ghostBlock = (TileEntityGhostBlock) tileEntity;
            ghostBlock.setBlueprintAndID(blueprint, blockID);
        }
    }

    public void destroyProjection() {
        if (this.blueprint == null)
            return;
        int facing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection direction = MinechemHelper.getDirectionFromFacing(facing);
        LocalPosition position = new LocalPosition(xCoord, yCoord, zCoord, direction);
        position.moveForwards(blueprint.zSize + 1);
        position.moveLeft(Math.floor(blueprint.xSize / 2));
        for (int x = 0; x < blueprint.xSize; x++) {
            for (int y = 0; y < blueprint.ySize; y++) {
                for (int z = 0; z < blueprint.zSize; z++) {
                    destroyGhostBlock(x, y, z, position);
                }
            }
        }
    }

    private void destroyGhostBlock(int x, int y, int z, LocalPosition position) {
        Pos3 worldPos = position.getLocalPos(x, y, z);
        int blockID = worldObj.getBlockId(worldPos.x, worldPos.y, worldPos.z);
        if (blockID == MinechemBlocks.ghostBlock.blockID) {
            worldObj.setBlockToAir(worldPos.x, worldPos.y, worldPos.z);
        }
    }

    public int getFacing() {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    public void setBlueprint(MinechemBlueprint blueprint) {
        if (blueprint != null) {
            this.blueprint = blueprint;
            this.structure = blueprint.getStructure();
        } else {
            destroyProjection();
            this.blueprint = null;
            this.structure = null;
            this.isComplete = false;
        }
    }

    public MinechemBlueprint takeBlueprint() {
        MinechemBlueprint blueprint = this.blueprint;
        setBlueprint(null);
        return blueprint;
    }

    public boolean hasBlueprint() {
        return this.blueprint != null;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        setBlueprint(null);
        return super.decrStackSize(slot, amount);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        super.setInventorySlotContents(slot, itemstack);
        if (itemstack != null) {
            MinechemBlueprint blueprint = MinechemItems.blueprint.getBlueprint(itemstack);
            setBlueprint(blueprint);
        }
    }

    @Override
    public String getInvName() {
        return "container.blueprintProjector";
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        ItemStack blueprintStack = inventory[0];
        if (blueprintStack != null) {
            NBTTagCompound blueprintNBT = new NBTTagCompound();
            blueprintStack.writeToNBT(blueprintNBT);
            nbtTagCompound.setTag("blueprint", blueprintNBT);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.inventory = new ItemStack[getSizeInventory()];
        this.isComplete = false;
        NBTTagCompound blueprintNBT = (NBTTagCompound) nbtTagCompound.getTag("blueprint");
        if (blueprintNBT != null) {
            ItemStack blueprintStack = ItemStack.loadItemStackFromNBT(blueprintNBT);
            MinechemBlueprint blueprint = MinechemItems.blueprint.getBlueprint(blueprintStack);
            setBlueprint(blueprint);
            this.inventory[0] = blueprintStack;
        }
    }

    public MinechemBlueprint getBlueprint() {
        return this.blueprint;
    }

    // Does inventory has custom name.
    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    // Is item valid.
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return itemstack.itemID == MinechemItems.blueprint.itemID;
    }


    @Override
    void sendUpdatePacket() {
        // TODO Auto-generated method stub

    }

}
