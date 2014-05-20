package minechem.tileentity.multiblock.ghostblock;

import minechem.item.blueprint.BlueprintBlock;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.tileentity.prefab.MinechemTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GhostTileEntityBlock extends MinechemTileEntity
{
    private MinechemBlueprint blueprint;
    private int blockID;

    public void setBlueprintAndID(MinechemBlueprint blueprint, int blockID)
    {
        setBlueprint(blueprint);
        setBlockID(blockID);
        this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blueprint.getBlockLookup().get(this.blockID).metadata, 3);
        if (worldObj != null && !worldObj.isRemote)
        {
            GhostPacketBlock packet = new GhostPacketBlock(this);
            int dimensionID = worldObj.provider.dimensionId;
            PacketDispatcher.sendPacketToAllInDimension(packet.makePacket(), dimensionID);
        }
    }

    public void setBlueprint(MinechemBlueprint blueprint)
    {
        this.blueprint = blueprint;
    }

    public MinechemBlueprint getBlueprint()
    {
        return this.blueprint;
    }

    public void setBlockID(int blockID)
    {
        this.blockID = blockID;
    }

    public int getBlockID()
    {
        return this.blockID;
    }

    public ItemStack getBlockAsItemStack()
    {
        BlueprintBlock blueprintBlock = this.blueprint.getBlockLookup().get(this.blockID);
        return new ItemStack(blueprintBlock.block, 1, blueprintBlock.metadata);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        if (blueprint != null)
        {
            nbtTagCompound.setInteger("blueprintID", blueprint.id);
        }
        
        nbtTagCompound.setInteger("blockID", blockID);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        this.blockID = nbtTagCompound.getInteger("blockID");
        int blueprintID = nbtTagCompound.getInteger("blueprintID");
        this.blueprint = MinechemBlueprint.blueprints.get(blueprintID);
    }

    @Override
    public int getSizeInventory()
    {
        return 0;
    }

    @Override
    public String getInvName()
    {
        return null;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return false;
    }

}
