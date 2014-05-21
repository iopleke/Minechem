package minechem.tileentity.multiblock.ghostblock;

import cpw.mods.fml.common.network.PacketDispatcher;
import minechem.Settings;
import minechem.item.blueprint.BlueprintBlock;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.tileentity.prefab.MinechemTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GhostBlockTileEntity extends MinechemTileEntity
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
            GhostBlockPacket packet = new GhostBlockPacket(this);
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
        try
        {
            BlueprintBlock blueprintBlock = this.blueprint.getBlockLookup().get(this.blockID);
            if (blueprintBlock != null)
            {
                return new ItemStack(blueprintBlock.block, 1, blueprintBlock.metadata);
            }
        } catch (Exception e)
        {
            // this code has now failed
            // it cannot be recovered
            // snowflake on hot iron
            if (Settings.DebugMode)
            {
                System.out.println("Block generated an exception at: x" + this.xCoord + " y" + this.yCoord + " z" + this.zCoord);
            }
        }
        return null;
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
