package minechem.tileentity.multiblock.fusion;

import minechem.MinechemItemsGeneration;
import minechem.item.blueprint.BlueprintFusion;
import minechem.item.element.ElementEnum;
import minechem.tileentity.multiblock.MultiBlockTileEntity;
import minechem.utils.MinechemHelper;
import minechem.utils.SafeTimeTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FusionTileEntity extends MultiBlockTileEntity implements ISidedInventory
{
    public static boolean canProcess = false;
    public static int fuelSlot = 0;
    public static int fusedResult = 0;
    public static int inputLeft = 1;
    public static int inputRight = 2;
    public static int output = 3;

    public FusionTileEntity()
    {
        this.inventory = new ItemStack[getSizeInventory()];
        setBlueprint(new BlueprintFusion());
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        // @TODO - set up for automation
        return false;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side)
    {
        // @TODO - implement per-slot results
        return false;
    }

    private boolean checkValidFuel()
    {
        return inventory[fuelSlot].itemID == Item.netherStar.itemID || inventory[fuelSlot].itemID == MinechemItemsGeneration.fusionStar.itemID;
    }

    private void fuseInputs()
    {
        if (inventory[output] == null)
        {
            inventory[output] = new ItemStack(MinechemItemsGeneration.element, 1, fusedResult - 1);
        } else if (inventory[output].getItemDamage() == fusedResult - 1)
        {
            inventory[output].stackSize++;
        } else
        {
            canProcess = false;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return null;
    }

    @Override
    public String getInvName()
    {
        return "container.minechemFusion";
    }

    @Override
    public int getSizeInventory()
    {
        return 4;
    }

    private boolean inputsCanBeFused()
    {
        if (inventory[inputLeft] != null && inventory[inputRight] != null)
        {
            fusedResult = inventory[inputLeft].getItemDamage() + inventory[inputRight].getItemDamage() + 2;
            return (fusedResult <= ElementEnum.heaviestMass);
        } else
        {
            return false;
        }
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
        if (slot == fuelSlot)
        {
            if (itemstack.itemID == Item.netherStar.itemID || itemstack.itemID == MinechemItemsGeneration.fusionStar.itemID)
            {
                return true;
            }
        } else if (slot == inputLeft || slot == inputRight)
        {
            if (itemstack.itemID == MinechemItemsGeneration.element.itemID)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return completeStructure;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        fusedResult = nbtTagCompound.getInteger("fusedResult");
        canProcess = nbtTagCompound.getBoolean("canProcess");
        inventory = new ItemStack[getSizeInventory()];
        MinechemHelper.readTagListToItemStackArray(nbtTagCompound.getTagList("inventory"), inventory);
    }

    private void removeInputs()
    {
        decrStackSize(inputLeft, 1);
        decrStackSize(inputRight, 1);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        this.inventory[slot] = itemstack;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!completeStructure)
        {
            return;
        }
        if (!worldObj.isRemote)
        {
            if (inventory[fuelSlot] != null && !canProcess)
            {
                canProcess = checkValidFuel() && inputsCanBeFused();

            }
            if (canProcess)
            {
                fuseInputs();
                useFuel();
                if (canProcess)
                {
                    removeInputs();
                    canProcess = false;
                }

            } else
            {
                fusedResult = 0;
            }
        }
    }

    private void useFuel()
    {
        if (inventory[fuelSlot].itemID == Item.netherStar.itemID)
        {
            this.inventory[fuelSlot] = new ItemStack(MinechemItemsGeneration.fusionStar);
        } else if (inventory[fuelSlot].itemID == MinechemItemsGeneration.fusionStar.itemID)
        {
            inventory[fuelSlot].setItemDamage(inventory[fuelSlot].getItemDamage() + 1);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("fusedResult", fusedResult);
        nbtTagCompound.setBoolean("canProcess", canProcess);
        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
        nbtTagCompound.setTag("inventory", inventoryTagList);
    }
}
