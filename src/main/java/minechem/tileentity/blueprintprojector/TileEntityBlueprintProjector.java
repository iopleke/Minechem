package minechem.tileentity.blueprintprojector;

import java.util.HashMap;

<<<<<<< HEAD
import minechem.MinechemBlockGeneration;
import minechem.MinechemItemGeneration;
=======
import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsGeneration;
>>>>>>> MaxwolfRewrite
import minechem.item.blueprint.BlueprintBlock;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.item.blueprint.BlueprintBlock.Type;
import minechem.sound.LoopingSound;
import minechem.tileentity.fusion.TileEntityFusion;
import minechem.tileentity.ghostblock.EnumBlockStatus;
import minechem.tileentity.ghostblock.TileEntityGhostBlock;
import minechem.tileentity.ghostblock.TileEntityMultiBlock;
import minechem.tileentity.prefab.MinechemTileEntity;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.utils.LocalPosition;
import minechem.utils.MinechemHelper;
import minechem.utils.LocalPosition.Pos3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityBlueprintProjector extends MinechemTileEntity
{
    private static int air;
    MinechemBlueprint blueprint;

    boolean isComplete = false;
    Integer[][][] structure;
    LoopingSound projectorSound;

    public TileEntityBlueprintProjector()
    {
        this.projectorSound = new LoopingSound("pixlepix.minechem.projector", 20);
        this.projectorSound.setVolume(.2F);
        this.inventory = new ItemStack[getSizeInventory()];
    }

    @Override
    public void updateEntity()
    {
        if (blueprint != null && !isComplete)
        {
            projectBlueprint();
            this.projectorSound.play(worldObj, xCoord, yCoord, zCoord);
        }
    }

    private void projectBlueprint()
    {
        int facing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection direction = MinechemHelper.getDirectionFromFacing(facing);
        LocalPosition position = new LocalPosition(xCoord, yCoord, zCoord, direction);
        position.moveForwards(blueprint.zSize + 1);
        position.moveLeft(Math.floor(blueprint.xSize / 2));
        boolean shouldProjectGhostBlocks = true;
        int totalIncorrectCount = blueprint.getTotalSize();

        for (int x = 0; x < blueprint.xSize; x++)
        {
            int verticalIncorrectCount = blueprint.getVerticalSliceSize();
            for (int y = 0; y < blueprint.ySize; y++)
            {
                for (int z = 0; z < blueprint.zSize; z++)
                {
                    if (shouldProjectGhostBlocks)
                    {
                        EnumBlockStatus enumBlockStatus;
                        if (isManagerBlock(x, y, z))
                            enumBlockStatus = EnumBlockStatus.CORRECT;
                        else
                            enumBlockStatus = projectGhostBlock(x, y, z, position);
                        if (enumBlockStatus == EnumBlockStatus.CORRECT)
                        {
                            verticalIncorrectCount--;
                            totalIncorrectCount--;
                        }
                    }
                    else
                    {
                        destroyGhostBlock(x, y, z, position);
                    }
                }
            }
            if (verticalIncorrectCount != 0)
                shouldProjectGhostBlocks = false;
        }

        if (totalIncorrectCount == 0 && (!isComplete || !(worldObj.getBlockTileEntity(blueprint.getManagerPosX(), blueprint.getManagerPosY(), blueprint.getManagerPosZ()) instanceof TileEntityMultiBlock)))
        {
            isComplete = true;
            buildStructure(position);
        }
    }

    private void buildStructure(LocalPosition position)
    {
        Integer[][][] resultStructure = blueprint.getResultStructure();
        HashMap<Integer, BlueprintBlock> blockLookup = blueprint.getBlockLookup();

        TileEntity managerTileEntity = buildManagerBlock(position);

        for (int x = 0; x < blueprint.xSize; x++)
        {
            for (int y = 0; y < blueprint.ySize; y++)
            {
                for (int z = 0; z < blueprint.zSize; z++)
                {
                    if (isManagerBlock(x, y, z))
                        continue;
                    int structureId = resultStructure[y][x][z];
                    setBlock(x, y, z, position, structureId, blockLookup, managerTileEntity);
                }
            }
        }
    }

    private boolean isManagerBlock(int x, int y, int z)
    {
        return x == blueprint.getManagerPosX() && y == blueprint.getManagerPosY() && z == blueprint.getManagerPosZ();
    }

    private TileEntity buildManagerBlock(LocalPosition position)
    {
        BlueprintBlock managerBlock = blueprint.getManagerBlock();
        if (managerBlock != null)
        {
            Pos3 worldPos = position.getLocalPos(blueprint.getManagerPosX(), blueprint.getManagerPosY(), blueprint.getManagerPosZ());
            worldObj.setBlock(worldPos.x, worldPos.y, worldPos.z, managerBlock.block.blockID, managerBlock.metadata, 3);
            if (this.blueprint == MinechemBlueprint.fusion)
            {
                TileEntityFusion fusion = new TileEntityFusion();
                fusion.worldObj = this.worldObj;
                fusion.xCoord = worldPos.x;
                fusion.yCoord = worldPos.y;
                fusion.zCoord = worldPos.z;
<<<<<<< HEAD
                fusion.blockType = MinechemBlockGeneration.fusion;
=======
                fusion.blockType = MinechemBlocksGeneration.fusion;
>>>>>>> MaxwolfRewrite
                worldObj.setBlockTileEntity(xCoord, yCoord, zCoord, fusion);
            }
            return worldObj.getBlockTileEntity(worldPos.x, worldPos.y, worldPos.z);
        }
        else
        {
            return null;
        }
    }

    private void setBlock(int x, int y, int z, LocalPosition position, int structureId, HashMap<Integer, BlueprintBlock> blockLookup, TileEntity managerTileEntity)
    {
        Pos3 worldPos = position.getLocalPos(x, y, z);
        if (structureId == MinechemBlueprint.wildcard)
        {
            return;
        }
        if (structureId == air)
        {
            worldObj.setBlockToAir(worldPos.x, worldPos.y, worldPos.z);
        }
        else
        {
            BlueprintBlock blueprintBlock = blockLookup.get(structureId);
            if (blueprintBlock.type == Type.MANAGER)
                return;
            worldObj.setBlock(worldPos.x, worldPos.y, worldPos.z, blueprintBlock.block.blockID, blueprintBlock.metadata, 3);
            if (blueprintBlock.type == Type.PROXY)
            {
                TileEntity te = worldObj.getBlockTileEntity(worldPos.x, worldPos.y, worldPos.z);
                if (te instanceof TileEntityProxy)
                {
                    TileEntityProxy proxy = (TileEntityProxy) te;
                }
            }

        }
    }

    private EnumBlockStatus projectGhostBlock(int x, int y, int z, LocalPosition position)
    {
        Pos3 worldPos = position.getLocalPos(x, y, z);
        Integer structureID = structure[y][x][z];
        int blockID = worldObj.getBlockId(worldPos.x, worldPos.y, worldPos.z);
        int blockMetadata = worldObj.getBlockMetadata(worldPos.x, worldPos.y, worldPos.z);
        if (structureID == MinechemBlueprint.wildcard)
        {
            return EnumBlockStatus.CORRECT;
        }
        else if (structureID == air)
        {
            if (blockID == air)
            {
                return EnumBlockStatus.CORRECT;
            }
            else
            {
                return EnumBlockStatus.INCORRECT;
            }
        }
        else
        {
            HashMap<Integer, BlueprintBlock> lut = blueprint.getBlockLookup();
            BlueprintBlock blueprintBlock = lut.get(structureID);
            if (blockID == air)
            {
                createGhostBlock(worldPos.x, worldPos.y, worldPos.z, structureID);
                return EnumBlockStatus.INCORRECT;
            }
            else if (blockID == blueprintBlock.block.blockID && (blockMetadata == blueprintBlock.metadata || blueprintBlock.metadata == -1))
            {
                return EnumBlockStatus.CORRECT;
            }
            else
            {
                return EnumBlockStatus.INCORRECT;
            }
        }
    }

    private void createGhostBlock(int x, int y, int z, int blockID)
    {
<<<<<<< HEAD
        worldObj.setBlock(x, y, z, MinechemBlockGeneration.ghostBlock.blockID, 0, 3);
=======
        worldObj.setBlock(x, y, z, MinechemBlocksGeneration.ghostBlock.blockID, 0, 3);
>>>>>>> MaxwolfRewrite
        TileEntity tileEntity = worldObj.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityGhostBlock)
        {
            TileEntityGhostBlock ghostBlock = (TileEntityGhostBlock) tileEntity;
            ghostBlock.setBlueprintAndID(blueprint, blockID);
        }
    }

    public void destroyProjection()
    {
        if (this.blueprint == null)
            return;
        int facing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection direction = MinechemHelper.getDirectionFromFacing(facing);
        LocalPosition position = new LocalPosition(xCoord, yCoord, zCoord, direction);
        position.moveForwards(blueprint.zSize + 1);
        position.moveLeft(Math.floor(blueprint.xSize / 2));
        for (int x = 0; x < blueprint.xSize; x++)
        {
            for (int y = 0; y < blueprint.ySize; y++)
            {
                for (int z = 0; z < blueprint.zSize; z++)
                {
                    destroyGhostBlock(x, y, z, position);
                }
            }
        }
    }

    private void destroyGhostBlock(int x, int y, int z, LocalPosition position)
    {
        Pos3 worldPos = position.getLocalPos(x, y, z);
        int blockID = worldObj.getBlockId(worldPos.x, worldPos.y, worldPos.z);
<<<<<<< HEAD
        if (blockID == MinechemBlockGeneration.ghostBlock.blockID)
=======
        if (blockID == MinechemBlocksGeneration.ghostBlock.blockID)
>>>>>>> MaxwolfRewrite
        {
            worldObj.setBlockToAir(worldPos.x, worldPos.y, worldPos.z);
        }
    }

    public int getFacing()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    public void setBlueprint(MinechemBlueprint blueprint)
    {
        if (blueprint != null)
        {
            this.blueprint = blueprint;
            this.structure = blueprint.getStructure();
        }
        else
        {
            destroyProjection();
            this.blueprint = null;
            this.structure = null;
            this.isComplete = false;
        }
    }

    public MinechemBlueprint takeBlueprint()
    {
        MinechemBlueprint blueprint = this.blueprint;
        setBlueprint(null);
        return blueprint;
    }

    public boolean hasBlueprint()
    {
        return this.blueprint != null;
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        setBlueprint(null);
        return super.decrStackSize(slot, amount);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        super.setInventorySlotContents(slot, itemstack);
        if (itemstack != null)
        {
<<<<<<< HEAD
            MinechemBlueprint blueprint = MinechemItemGeneration.blueprint.getBlueprint(itemstack);
=======
            MinechemBlueprint blueprint = MinechemItemsGeneration.blueprint.getBlueprint(itemstack);
>>>>>>> MaxwolfRewrite
            setBlueprint(blueprint);
        }
    }

    @Override
    public String getInvName()
    {
        return "container.blueprintProjector";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        ItemStack blueprintStack = inventory[0];
        if (blueprintStack != null)
        {
            NBTTagCompound blueprintNBT = new NBTTagCompound();
            blueprintStack.writeToNBT(blueprintNBT);
            nbtTagCompound.setTag("blueprint", blueprintNBT);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.inventory = new ItemStack[getSizeInventory()];
        this.isComplete = false;
        NBTTagCompound blueprintNBT = (NBTTagCompound) nbtTagCompound.getTag("blueprint");
        if (blueprintNBT != null)
        {
            ItemStack blueprintStack = ItemStack.loadItemStackFromNBT(blueprintNBT);
<<<<<<< HEAD
            MinechemBlueprint blueprint = MinechemItemGeneration.blueprint.getBlueprint(blueprintStack);
=======
            MinechemBlueprint blueprint = MinechemItemsGeneration.blueprint.getBlueprint(blueprintStack);
>>>>>>> MaxwolfRewrite
            setBlueprint(blueprint);
            this.inventory[0] = blueprintStack;
        }
    }

    public MinechemBlueprint getBlueprint()
    {
        return this.blueprint;
    }

    // Does inventory has custom name.
    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    // Is item valid.
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
<<<<<<< HEAD
        return itemstack.itemID == MinechemItemGeneration.blueprint.itemID;
=======
        return itemstack.itemID == MinechemItemsGeneration.blueprint.itemID;
>>>>>>> MaxwolfRewrite
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

}
