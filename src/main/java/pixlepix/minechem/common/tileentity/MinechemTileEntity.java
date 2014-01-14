package pixlepix.minechem.common.tileentity;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public abstract class MinechemTileEntity extends TileEntity implements IInventory, IEnergyHandler {

    public ItemStack[] inventory;
    public float lastEnergyUsed;

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setFloat("lastEnergyUsed", this.lastEnergyUsed);
        nbt.setInteger("energy", this.energyStored);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.lastEnergyUsed = nbt.getFloat("lastEnergyUsed");
        this.energyStored = nbt.getInteger("energy");
    }

    public float oldPower = -1;

    @Override
    public void updateEntity() {

        super.updateEntity();


    }

    public boolean didEnergyStoredChange() {
        return true;
    }

    public boolean didEnergyUsageChange() {
        return true;
    }

    public void setEnergyUsage(float energyUsage) {
        this.lastEnergyUsed = energyUsage;
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        this.readFromNBT(pkt.data);
    }

    abstract void sendUpdatePacket();

    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.inventory[var1];
    }

    public float getRequest() {
        return this.lastEnergyUsed;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;
            if (this.inventory[slot].stackSize <= amount) {

                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(amount);
                if (this.inventory[slot].stackSize == 0)
                    this.inventory[slot] = null;
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        } else {
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
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    public int energyStored;

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        int receive = Math.min(maxReceive, getMaxEnergyStored(from) - energyStored);
        if (!simulate) {
            this.setEnergyUsage(receive);
            this.energyStored += receive;
        }
        return receive;
    }

    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    public int getEnergyStored() {
        return this.energyStored;
    }

    public boolean canInterface(ForgeDirection from) {
        return true;
    }

    public int getEnergyStored(ForgeDirection from) {
        return this.energyStored;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return 600000;
    }


}
