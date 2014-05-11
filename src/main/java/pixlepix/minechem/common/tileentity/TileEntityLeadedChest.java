package pixlepix.minechem.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import pixlepix.minechem.common.ModMinechem;

public class TileEntityLeadedChest extends TileEntity implements IInventory {

    private final ItemStack[] inventory;
    private final int stackLimit = 64;

    public TileEntityLeadedChest() {
        inventory = new ItemStack[9];
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < ModMinechem.leadedChestSize) {
            return inventory[slot];
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = this.getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize <= amount) {
                this.setInventorySlotContents(slot, null);
            } else {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0) {
                    this.setInventorySlotContents(slot, null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = this.getStackInSlot(slot);
        if (stack != null) {
            this.setInventorySlotContents(slot, null);
        }
        return stack;
    }

    @Override
    public String getInvName() {
        return "container.leadedchest";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return this.stackLimit;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        // water through a sieve
        // ripples through a broken dam
        // anything goes here
        return true;

    }

    @Override
    public void readFromNBT(NBTTagCompound NBTTag) {
        super.readFromNBT(NBTTag);

        NBTTagList tagList = NBTTag.getTagList("Inventory");
        int tagCount = tagList.tagCount();

        for (int i = 0; i < tagCount; i++) {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(NBTTag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound NBTTag) {
        super.writeToNBT(NBTTag);

        NBTTagList tagList = NBTTag.getTagList("Inventory");
        int tagCount = tagList.tagCount();

        for (int i = 0; i < tagCount; i++) {
            ItemStack stack = inventory[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(NBTTag);
                tagList.appendTag(NBTTag);
            }
        }
        NBTTag.setTag("Inventory", tagList);
    }

}
