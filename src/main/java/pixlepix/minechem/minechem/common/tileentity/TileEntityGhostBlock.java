package pixlepix.minechem.minechem.common.tileentity;

import pixlepix.minechem.minechem.common.blueprint.BlueprintBlock;
import pixlepix.minechem.minechem.common.blueprint.MinechemBlueprint;
import pixlepix.minechem.minechem.common.network.PacketGhostBlock;
import pixlepix.minechem.minechem.common.network.PacketHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityGhostBlock extends MinechemTileEntity {

    private MinechemBlueprint blueprint;
    private int blockID;

    public void setBlueprintAndID(MinechemBlueprint blueprint, int blockID) {
        setBlueprint(blueprint);
        setBlockID(blockID);
        this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, blueprint.getBlockLookup().get(this.blockID).metadata, 3);
        if (worldObj != null && !worldObj.isRemote)
            sendUpdatePacket();
    }

    public void setBlueprint(MinechemBlueprint blueprint) {
        this.blueprint = blueprint;
    }

    public MinechemBlueprint getBlueprint() {
        return this.blueprint;
    }

    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    public int getBlockID() {
        return this.blockID;
    }

    public ItemStack getBlockAsItemStack() {
        BlueprintBlock blueprintBlock = this.blueprint.getBlockLookup().get(this.blockID);
        return new ItemStack(blueprintBlock.block, 1, blueprintBlock.metadata);
    }

    @Override
    public void sendUpdatePacket() {
        PacketGhostBlock packet = new PacketGhostBlock(this);
        int dimensionID = worldObj.provider.dimensionId;
        PacketHandler.getInstance().ghostBlockUpdateHandler.sendToAllPlayersInDimension(packet, dimensionID);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("blueprintID", blueprint.id);
        nbtTagCompound.setInteger("blockID", blockID);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.blockID = nbtTagCompound.getInteger("blockID");
        int blueprintID = nbtTagCompound.getInteger("blueprintID");
        this.blueprint = MinechemBlueprint.blueprints.get(blueprintID);
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public String getInvName() {
        return null;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}


}
