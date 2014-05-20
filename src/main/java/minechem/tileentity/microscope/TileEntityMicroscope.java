package minechem.tileentity.microscope;

import minechem.MinechemItemsGeneration;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerRecipeHandler;
import minechem.tileentity.prefab.MinechemTileEntity;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.tileentity.synthesis.SynthesisRecipeHandler;
import minechem.utils.BoundedInventory;
import minechem.utils.Transactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMicroscope extends MinechemTileEntity implements IInventory
{
    public static int[] kInput = { 0 };
    public static int[] kJournal = { 1 };

    public boolean isShaped = true;

    private final BoundedInventory inputInvetory = new BoundedInventory(this, kInput);
    private final BoundedInventory journalInventory = new BoundedInventory(this, kJournal);
    private Transactor inputTransactor = new Transactor(inputInvetory, 1);
    private Transactor journalTransactor = new Transactor(journalInventory, 1);

    public TileEntityMicroscope()
    {
        inventory = new ItemStack[getSizeInventory()];
    }

    public void onInspectItemStack(ItemStack itemstack)
    {
        SynthesisRecipe synthesisRecipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(itemstack);
        DecomposerRecipe decomposerRecipe = DecomposerRecipeHandler.instance.getRecipe(itemstack);
        if (inventory[1] != null && (synthesisRecipe != null || decomposerRecipe != null))
        {
            MinechemItemsGeneration.journal.addItemStackToJournal(itemstack, inventory[1], worldObj);
        }
    }

    @Override
    public int getSizeInventory()
    {
        return 11;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        ItemStack itemstack = inventory[slot];
        return itemstack;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (slot >= 0 && slot < inventory.length)
        {
            ItemStack itemstack = inventory[slot];
            inventory[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        inventory[slot] = itemStack;
        if (slot == 0 && itemStack != null && !worldObj.isRemote)
            onInspectItemStack(itemStack);
        if (slot == 1 && itemStack != null && inventory[0] != null && !worldObj.isRemote)
            onInspectItemStack(inventory[0]);
    }

    @Override
    public String getInvName()
    {
        return "container.microscope";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        double dist = entityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D);
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : dist <= 64.0D;
    }

    @Override
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    public int getFacing()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        ItemStack inpectingStack = inventory[0];
        if (inpectingStack != null)
        {
            NBTTagCompound inspectingStackTag = inpectingStack.writeToNBT(new NBTTagCompound());
            nbtTagCompound.setTag("inspectingStack", inspectingStackTag);
        }
        ItemStack journal = inventory[1];
        if (journal != null)
        {
            NBTTagCompound journalTag = journal.writeToNBT(new NBTTagCompound());
            nbtTagCompound.setTag("journal", journalTag);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        NBTTagCompound inspectingStackTag = nbtTagCompound.getCompoundTag("inspectingStack");
        NBTTagCompound journalTag = nbtTagCompound.getCompoundTag("journal");
        ItemStack inspectingStack = ItemStack.loadItemStackFromNBT(inspectingStackTag);
        ItemStack journalStack = ItemStack.loadItemStackFromNBT(journalTag);
        inventory[0] = inspectingStack;
        inventory[1] = journalStack;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        if (i == kInput[0])
            return true;
        if (i == kJournal[0] && itemstack.itemID == MinechemItemsGeneration.journal.itemID)
            return true;
        return false;
    }
}
