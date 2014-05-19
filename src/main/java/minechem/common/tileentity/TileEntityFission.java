package minechem.common.tileentity;

import minechem.api.util.Constants;
import minechem.common.MinechemItems;
import minechem.common.blueprint.BlueprintFission;
import minechem.common.inventory.BoundedInventory;
import minechem.common.inventory.Transactor;
import minechem.common.items.ItemElement;
import minechem.common.utils.MinechemHelper;
import minechem.common.utils.SafeTimeTracker;
import minechem.computercraft.IMinechemMachinePeripheral;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityFission extends TileEntityMultiBlock implements IMinechemMachinePeripheral, ISidedInventory
{

    public static int[] kInput =
    { 0 };
    public static int[] kFuel =
    { 1 };
    public static int[] kOutput =
    { 2 };

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

    public TileEntityFission()
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
            return;
        shouldSendUpdatePacket = false;
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 50 == 0 && inventory[kStartFuel] != null && energyUpdateTracker.markTimeIfDelay(worldObj, Constants.TICKS_PER_SECOND * 2))
        {
            if (inventory[kStartInput] != null && inventory[kStartFuel] != null && inventory[kStartFuel].getItemDamage() == 91 && inventory[kStartFuel].getItem() instanceof ItemElement)
            {
                ItemStack fissionResult = getFissionOutput();
                if (inventory[kOutput[0]] == null || (fissionResult != null && fissionResult.itemID == inventory[kOutput[0]].itemID && fissionResult.getItemDamage() == inventory[kOutput[0]].getItemDamage() && !worldObj.isRemote))
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
        }
        else
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
            return itemInOutput.stackSize < getInventoryStackLimit() && itemInOutput.isItemEqual(fusionResult);
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
                return new ItemStack(MinechemItems.element, 2, newMass - 1);
            }
            else
            {
                return null;
            }
        }
        else
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
    public String getInvName()
    {
        return "container.minechemFission";
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        if (!completeStructure)
            return false;

        return true;
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
        MinechemHelper.readTagListToItemStackArray(nbtTagCompound.getTagList("inventory"), inventory);
    }

    public void setEnergyStored(int amount)
    {
        this.setEnergyStored(amount);
    }

    @Override
    public ItemStack takeOutput()
    {
        return outputTransactor.removeItem(true);
    }

    @Override
    public int putOutput(ItemStack output)
    {
        return outputTransactor.add(output, true);
    }

    @Override
    public ItemStack takeInput()
    {
        return inputTransactor.removeItem(true);
    }

    @Override
    public int putInput(ItemStack input)
    {
        return inputTransactor.add(input, true);
    }

    @Override
    public ItemStack takeJournal()
    {
        return null;
    }

    @Override
    public int putJournal(ItemStack journal)
    {
        return 0;
    }

    @Override
    public String getMachineState()
    {
        // TODO Check for fuel.
        return "powered";

    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {

        if (i != kOutput[0] && itemstack.getItem() instanceof ItemElement)
        {
            return true;
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
    public ItemStack takeFusionStar()
    {
        return null;
    }

    @Override
    public int putFusionStar(ItemStack fusionStar)
    {
        return 0;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int i)
    {
        switch (i)
        {
        case 0:
            return TileEntityFission.kOutput;
        case 1:
            return TileEntityFission.kInput;
        default:
            return TileEntityFission.kFuel;
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
