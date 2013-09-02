package ljdp.minechem.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.compatibility.TileEntityUniversalElectrical;
import universalelectricity.prefab.tile.TileEntityElectrical;

public abstract class MinechemTileEntity extends TileEntityUniversalElectrical implements IInventory {
	
	public ItemStack[] inventory;
	public float lastEnergyUsed;
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tagCompound);
	}
	@Override
	public void updateEntity(){
		
	}
	public void setEnergyUsage(float energyUsage) {
		this.lastEnergyUsed=energyUsage/20;
	}
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		this.readFromNBT(pkt.customParam1);
	}
	
	abstract void sendUpdatePacket();
	
	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.inventory[var1];
	}
	public float getEnergyUsage(){
		return this.lastEnergyUsed*20;
	}
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(this.inventory[slot] != null) {
			ItemStack itemstack;
			if(this.inventory[slot].stackSize <= amount) {
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				return itemstack;
			} else {
				itemstack = this.inventory[slot].splitStack(amount);
				if(this.inventory[slot].stackSize == 0)
					this.inventory[slot] = null;
				return itemstack;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.inventory[slot] != null)
        {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
			itemstack.stackSize = this.getInventoryStackLimit();
		this.inventory[slot] = itemstack;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

    @Override
    public float getRequest(ForgeDirection direction) {
        return (float) Math.min((this.getMaxEnergyStored() - this.getEnergyStored()), this.getMaxEnergyReceived()* 437.5D / this.getVoltage());
    }
	//TODO Implement this for packet efficiency
	public boolean didEnergyStoredChange(){
		return true;
	}
	public boolean didEnergyUsageChange(){
		return true;
	}
	
	//Should probably get *actual* values for this
	public float getMaxEnergyReceived(){
		return 100000000;
	}
	

}
