package minechem.tileentity.multiblock.fission;

import cpw.mods.fml.common.network.NetworkRegistry;
import minechem.MinechemItemsRegistration;
import minechem.Settings;
import minechem.item.blueprint.BlueprintFission;
import minechem.item.element.ElementItem;
import minechem.network.MessageHandler;
import minechem.network.message.FissionUpdateMessage;
import minechem.tileentity.multiblock.MultiBlockTileEntity;
import minechem.tileentity.prefab.BoundedInventory;
import minechem.utils.MinechemUtil;
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
	public static int[] kOutput =
	{
		2
	};

	private final BoundedInventory inputInventory;
	private final BoundedInventory outputInventory;
	private Transactor inputTransactor;
	private Transactor outputTransactor;
	public static int kStartInput = 0;
	public static int kStartOutput = 2;
	public static int kSizeInput = 1;
	public static int kSizeOutput = 1;
	SafeTimeTracker energyUpdateTracker = new SafeTimeTracker();
	boolean shouldSendUpdatePacket;

	public FissionTileEntity()
	{
		super(Settings.maxFissionStorage);
		inventory = new ItemStack[getSizeInventory()];
		inputInventory = new BoundedInventory(this, kInput);
		outputInventory = new BoundedInventory(this, kOutput);
		inputTransactor = new Transactor(inputInventory);
		outputTransactor = new Transactor(outputInventory);
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
		if (!worldObj.isRemote)
		{
			if (inventory[kStartInput] != null)
			{
				if (inputIsFissionable())
				{
					if (useEnergy(getEnergyNeeded()))
					{
						ItemStack fissionResult = getFissionOutput();
						addToOutput(fissionResult);
						removeInputs();
					}
				}
			}
			FissionUpdateMessage message = new FissionUpdateMessage(this);
			MessageHandler.INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, Settings.UpdateRadius));
		}
	}

	public boolean inputIsFissionable()
	{
		ItemStack fissionResult = getFissionOutput();
		if (fissionResult != null)
		{
			if (inventory[kOutput[0]] == null) return true;
			boolean sameItem = fissionResult.getItem() == inventory[kOutput[0]].getItem() && fissionResult.getItemDamage() == inventory[kOutput[0]].getItemDamage();
			return inventory[kOutput[0]].stackSize < 64 && sameItem;
		}
		return false;
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
		if (inventory[kInput[0]] != null && inventory[kInput[0]].getItem() instanceof ElementItem && inventory[kInput[0]].getItemDamage() > 0)
		{
			int mass = ElementItem.getElement(inventory[kInput[0]]).atomicNumber();
			int newMass = mass / 2;
			if (newMass > 0)
			{
				return new ItemStack(MinechemItemsRegistration.element, 2, newMass);
			} else
			{
				return null;
			}
		} else
		{
			return null;
		}
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
		NBTTagList inventoryTagList = MinechemUtil.writeItemStackArrayToTagList(inventory);
		nbtTagCompound.setTag("inventory", inventoryTagList);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		inventory = new ItemStack[getSizeInventory()];
		MinechemUtil.readTagListToItemStackArray(nbtTagCompound.getTagList("inventory", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND), inventory);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		if (slot != 2 && itemstack.getItem() instanceof ElementItem)
		{
			if (slot == 1 && itemstack.getItemDamage() == 91)
			{
				return true;
			}
			if (slot == 0)
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
			default:
				return kInput;
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int i)
	{
		switch (i)
		{
			case 0:
				return FissionTileEntity.kOutput;
			default:
				return FissionTileEntity.kInput;
		}
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int i2)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int i2)
	{
		return false;
	}

	@Override
	public int getEnergyNeeded()
	{
		if (inventory[0] != null)
		{
			return (inventory[0].getItemDamage()) * Settings.fissionMultiplier;
		}
		return 0;
	}
}
