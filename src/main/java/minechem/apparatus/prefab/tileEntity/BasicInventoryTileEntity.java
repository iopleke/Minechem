package minechem.apparatus.prefab.tileEntity;

import minechem.apparatus.prefab.tileEntity.storageTypes.BasicInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

/**
 * Defines basic properties for TileEntities
 */
public abstract class BasicInventoryTileEntity extends TileEntity implements IInventory
{
    private BasicInventory inventory;

    public BasicInventoryTileEntity(int inventorySize)
    {
        inventory = new BasicInventory(inventorySize);
    }

    @Override
    public void closeInventory()
    {
    }

    /**
     * Decrease the stack in a given slot by a given amount
     *
     * @param slot   the inventory slot
     * @param amount the amount to decrease the stack by
     * @return ItemStack the stack from the slot
     */
    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        return inventory.decrStackSize(slot, amount);
    }

    /**
     * Get the inventory name
     *
     * @return String the unlocalized inventory name
     */
    @Override
    public String getInventoryName()
    {
        return inventory.getInventoryName();
    }

    /**
     * Get the stack size limit for the inventory, override if necessary
     *
     * @return 64
     */
    @Override
    public int getInventoryStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    /**
     * Get the size of the inventory
     *
     * @return int size of the inventory object
     */
    @Override
    public int getSizeInventory()
    {
        return inventory.getSizeInventory();
    }

    /**
     * Get the item from a specific slot
     *
     * @param slot slot to get the item from
     * @return the itemstack from the slot
     */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    /**
     * Get the stack in a given slot on GUI close
     *
     * @param slot the slot to get from
     * @return ItemStack the stack from the slot
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    /**
     * Check if the inventory has a custom name
     *
     * @return false
     */
    @Override
    public boolean hasCustomInventoryName()
    {
        return inventory.hasCustomInventoryName();
    }

    /**
     * Can an item be put into the slot
     *
     * @param slot  slot to check
     * @param stack itemstack to check
     * @return true
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return inventory.isItemValidForSlot(slot, stack);
    }

    /**
     * Check if the player can use the inventory
     *
     * @param entityPlayer the player object
     * @return boolean based on distance and tileEntity status
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return inventory.isUseableByPlayer(entityPlayer, xCoord, yCoord, zCoord);
    }

    @Override
    public void markDirty()
    {
    }

    @Override
    public void openInventory()
    {
    }

    /**
     * Read saved values from NBT
     *
     * @param nbttagcompound
     */
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);

        NBTTagList nbttaglist = nbttagcompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < inventory.getInventory().length; i++)
        {
            inventory.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i)));
        }

    }

    /**
     * Set the inventory slot to a given itemstack
     *
     * @param slot  which slot should the itemstack go into
     * @param stack the stack to put into the slot
     */
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public abstract void updateEntity();

    /**
     * Save data to NBT
     *
     * @param nbttagcompound
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (ItemStack stack : inventory.getInventory())
        {
            if (stack != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                stack.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            } else
            {
                nbttaglist.appendTag(new NBTTagCompound());
            }
        }
        nbttagcompound.setTag(inventory.getInventoryName(), nbttaglist);
    }
}
