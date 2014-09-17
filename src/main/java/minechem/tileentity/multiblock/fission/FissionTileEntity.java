package minechem.tileentity.multiblock.fission;

import minechem.MinechemItemsRegistration;
import minechem.item.blueprint.BlueprintFission;
import minechem.item.element.ElementItem;
import minechem.tileentity.multiblock.MultiBlockTileEntity;
import minechem.tileentity.prefab.BoundedInventory;
import minechem.utils.Constants;
import minechem.utils.MinechemHelper;
import minechem.utils.SafeTimeTracker;
import minechem.utils.Transactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FissionTileEntity extends MultiBlockTileEntity implements ISidedInventory
{

    public static int[] kInput =
    {
        0
    };
    public static int[] kFuel =
    {
        1
    };
    public static int[] kOutput =
    {
        2
    };

    private final BoundedInventory inputInventory;
    private final BoundedInventory outputInventory;
    private final BoundedInventory fuelInventory;
    private Transactor inputTransactor;
    private Transactor outputTransactor;
    private Transactor fuelTransactor;
    public static int kStartInput = 0;
    public static int kStartFuel = 1;
    public static int kStartOutput = 2;
    public static int kSizeInput = 1;
    public static int kSizeFuel = 1;
    public static int kSizeOutput = 1;
    SafeTimeTracker energyUpdateTracker = new SafeTimeTracker();
    boolean shouldSendUpdatePacket;

    public FissionTileEntity()
    {
        inventory = new ItemStack[getSizeInventory()];
        inputInventory = new BoundedInventory(this, kInput);
        outputInventory = new BoundedInventory(this, kOutput);
        fuelInventory = new BoundedInventory(this, kFuel);
        inputTransactor = new Transactor(inputInventory);
        outputTransactor = new Transactor(outputInventory);
        fuelTransactor = new Transactor(fuelInventory, 1);
        this.inventory = new ItemStack[this.getSizeInventory()];
        setBlueprint(new BlueprintFission());
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!completeStructure)
        {
            return;
        }
        shouldSendUpdatePacket = false;
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 50 == 0 && inventory[kStartFuel] != null && energyUpdateTracker.markTimeIfDelay(worldObj, Constants.TICKS_PER_SECOND * 2))
        {
            if (inventory[kStartInput] != null && inventory[kStartFuel] != null && inventory[kStartFuel].getItemDamage() == 91 && inventory[kStartFuel].getItem() instanceof ElementItem)
            {
                ItemStack fissionResult = getFissionOutput();
                if (inventory[kOutput[0]] == null || (inventory[kOutput[0]].stackSize < 64 && fissionResult != null && fissionResult.getItem() == inventory[kOutput[0]].getItem() && fissionResult.getItemDamage() == inventory[kOutput[0]].getItemDamage() && !worldObj.isRemote))
                {
                    addToOutput(fissionResult);
                    removeInputs();
                }
                fissionResult = getFissionOutput();
                shouldSendUpdatePacket = true;
            }
        }

        if (shouldSendUpdatePacket && !worldObj.isRemote)
        {
            // TODO: Write update packet for energy information for client.
        }
    }

    private void addToOutput(ItemStack fusionResult)
    {
        if (fusionResult == null)
        {
            return;
        }

        if (inventory[kOutput[0]] == null)
        {
            ItemStack output = fusionResult.copy();
            inventory[kOutput[0]] = output;
        } else
        {
            inventory[kOutput[0]].stackSize += 2;
        }
    }

    private void removeInputs()
    {
        decrStackSize(kInput[0], 1);

        decrStackSize(kFuel[0], 1);
    }

    private boolean canFuse(ItemStack fusionResult)
    {
        ItemStack itemInOutput = inventory[kOutput[0]];
        if (itemInOutput != null)
        {
            return itemInOutput.stackSize < getInventoryStackLimit() && itemInOutput.isItemEqual(fusionResult);
        }
        return true;
    }

    private ItemStack getFissionOutput()
    {
        if (hasInputs())
        {
            int mass = inventory[kInput[0]].getItemDamage() + 1;
            int newMass = mass / 2;
            if (newMass > 1)
            {
                return new ItemStack(MinechemItemsRegistration.element, 2, newMass - 1);
            } else
            {
                return null;
            }
        } else
        {
            return null;
        }
    }

    private boolean hasInputs()
    {
        return inventory[kInput[0]] != null;
    }

    @Override
    public int getSizeInventory()
    {
        return 3;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {

        this.inventory[slot] = itemstack;

    }

    @Override
    public String getInventoryName()
    {
        return "container.minechemFission";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        if (!completeStructure)
        {
            return false;
        }

        return true;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
        nbtTagCompound.setTag("inventory", inventoryTagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        inventory = new ItemStack[getSizeInventory()];
        MinechemHelper.readTagListToItemStackArray(nbtTagCompound.getTagList("inventory", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND), inventory);
    }

    public void setEnergyStored(int amount)
    {
        this.setEnergyStored(amount);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
    	if (slot != 2 && itemstack.getItem() instanceof ElementItem)
        {
    		if(slot==1 && itemstack.getItemDamage()==91)
    		{
    			return true;
    		}
    		if(slot==0)
    		{
    			return true;
    		}
        }
        return false;
    }

    public int[] getSizeInventorySide(int side)
    {
        switch (side)
        {
            case 0:
                return kOutput;
            case 1:
                return kInput;
            default:
                return kFuel;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int i)
    {
        switch (i)
        {
            case 0:
                return FissionTileEntity.kOutput;
            case 1:
                return FissionTileEntity.kInput;
            default:
                return FissionTileEntity.kFuel;
        }
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, int i2)
    {
        return true;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, int i2)
    {
        return true;
    }
}
