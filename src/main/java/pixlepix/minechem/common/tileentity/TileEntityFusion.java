package pixlepix.minechem.common.tileentity;

import buildcraft.api.core.SafeTimeTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.api.util.Constants;
import pixlepix.minechem.common.MinechemItems;
import pixlepix.minechem.common.blueprint.BlueprintFusion;
import pixlepix.minechem.common.inventory.BoundedInventory;
import pixlepix.minechem.common.inventory.Transactor;
import pixlepix.minechem.common.items.ItemElement;
import pixlepix.minechem.common.utils.MinechemHelper;
import pixlepix.minechem.computercraft.IMinechemMachinePeripheral;

public class TileEntityFusion extends TileEntityMultiBlock implements IMinechemMachinePeripheral {

	public static int[] kFusionStar = { 0 };
	public static int[] kInput = { 1, 2 };
	public static int[] kOutput = { 3 };

	private final BoundedInventory inputInventory;
	private final BoundedInventory outputInventory;
	private final BoundedInventory starInventory;
	private Transactor inputTransactor;
	private Transactor outputTransactor;
	private Transactor starTransactor;
	public static int kStartFusionStar = 0;
	public static int kStartInput1 = 1;
	public static int kStartInput2 = 2;
	public static int kStartOutput = 3;
	public static int kSizeInput = 2;
	public static int kSizeOutput = 1;
	public static int kSizeFusionStar = 1;
	int energyStored = 0;
	int maxEnergy = 9;
	int targetEnergy = 0;
	boolean isRecharging = false;
	SafeTimeTracker energyUpdateTracker = new SafeTimeTracker();
	boolean shouldSendUpdatePacket;

	public TileEntityFusion() {
		inventory = new ItemStack[getSizeInventory()];
		inputInventory = new BoundedInventory(this, kInput);
		outputInventory = new BoundedInventory(this, kOutput);
		starInventory = new BoundedInventory(this, kFusionStar);
		inputTransactor = new Transactor(inputInventory);
		outputTransactor = new Transactor(outputInventory);
		starTransactor = new Transactor(starInventory, 1);
		this.inventory = new ItemStack[this.getSizeInventory()];
		setBlueprint(new BlueprintFusion());
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!completeStructure)
			return;
		shouldSendUpdatePacket = false;
		if (!worldObj.isRemote && inventory[kStartFusionStar] != null
				&& energyUpdateTracker.markTimeIfDelay(worldObj, Constants.TICKS_PER_SECOND * 2)) {
			if (!isRecharging)
				targetEnergy = takeEnergyFromStar(inventory[kStartFusionStar], true);
			if (targetEnergy > 0)
				isRecharging = true;
			if (isRecharging) {
				recharge();
			} else {
				ItemStack fusionResult = getFusionOutput();
				while (fusionResult != null && canFuse(fusionResult)) {
					if (!worldObj.isRemote) {
						addToOutput(fusionResult);
						removeInputs();
					}
					energyStored -= getEnergyCost(fusionResult);
					fusionResult = getFusionOutput();
					shouldSendUpdatePacket = true;
				}
			}
		}
		if (shouldSendUpdatePacket && !worldObj.isRemote)
			sendUpdatePacket();
	}

	private void addToOutput(ItemStack fusionResult) {
		if (inventory[kOutput[0]] == null) {
			ItemStack output = fusionResult.copy();
			inventory[kOutput[0]] = output;
		} else {
			inventory[kOutput[0]].stackSize++;
		}
	}

	private void removeInputs() {
		decrStackSize(kInput[0], 1);
		decrStackSize(kInput[1], 1);
	}

	private boolean canFuse(ItemStack fusionResult) {
		ItemStack itemInOutput = inventory[kOutput[0]];
		if (itemInOutput != null)
			return itemInOutput.stackSize < getInventoryStackLimit() && itemInOutput.isItemEqual(fusionResult) && energyStored >= getEnergyCost(fusionResult);
		else
			return energyStored >= getEnergyCost(fusionResult);
	}

	private ItemStack getFusionOutput() {
		if (hasInputs()) {
			int mass1 = inventory[kInput[0]].getItemDamage() + 1;
			int mass2 = inventory[kInput[1]].getItemDamage() + 1;
			int massSum = mass1 + mass2;
			if (massSum <= EnumElement.heaviestMass) {
				return new ItemStack(MinechemItems.element, 1, massSum - 1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private int getEnergyCost(ItemStack itemstack) {
		int mass = itemstack.getItemDamage();
		int cost = (int) MinechemHelper.translateValue(mass + 1, 1, EnumElement.heaviestMass, 1, this.maxEnergy);
		return cost / 100;
	}

	private boolean hasInputs() {
		return inventory[kInput[0]] != null && inventory[kInput[1]] != null;
	}

	private void recharge() {
		if (energyStored < targetEnergy) {
			energyStored++;
			shouldSendUpdatePacket = true;
		} else {
			isRecharging = false;
			targetEnergy = 0;
		}
	}

	private int takeEnergyFromStar(ItemStack fusionStar, boolean doTake) {
		int energyCapacityAvailable = maxEnergy - energyStored;
		int fusionStarDamage = fusionStar.getItemDamage();
		int energyInStar = fusionStar.getMaxDamage() - fusionStarDamage;
		if (energyCapacityAvailable == 0) {
			return 0;
		} else if (energyInStar > energyCapacityAvailable) {
			if (doTake) {
				fusionStarDamage += energyCapacityAvailable;
				fusionStar.setItemDamage(fusionStarDamage);
			}
			return maxEnergy;
		} else {
			if (doTake) {
				destroyFusionStar(fusionStar);
			}
			return energyStored + energyInStar;
		}
	}

	private void destroyFusionStar(ItemStack fusionStar) {
		this.inventory[kFusionStar[0]] = null;
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (slot == 0 && itemstack != null && itemstack.itemID == Item.netherStar.itemID) {
			System.out.println("Turning nether star into fusion star");
			this.inventory[slot] = new ItemStack(MinechemItems.fusionStar);
		} else {
			this.inventory[slot] = itemstack;
		}
	}

	@Override
	public String getInvName() {
		return "container.minechemFusion";
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
		if (!completeStructure)
			return false;

		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setInteger("fusionenergyStored", energyStored);
		nbtTagCompound.setInteger("targetEnergy", targetEnergy);
		nbtTagCompound.setBoolean("isRecharging", isRecharging);
		NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
		nbtTagCompound.setTag("inventory", inventoryTagList);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		energyStored = nbtTagCompound.getInteger("fusionenergyStored");
		targetEnergy = nbtTagCompound.getInteger("targetEnergy");
		isRecharging = nbtTagCompound.getBoolean("isRecharging");
		inventory = new ItemStack[getSizeInventory()];
		MinechemHelper.readTagListToItemStackArray(nbtTagCompound.getTagList("inventory"), inventory);
	}

	public void setEnergyStored(int amount) {
		this.energyStored = amount;
	}

	public int getMaxEnergy() {
		return this.maxEnergy;
	}

	@Override
	public ItemStack takeOutput() {
		return outputTransactor.removeItem(true);
	}

	@Override
	public int putOutput(ItemStack output) {
		return outputTransactor.add(output, true);
	}

	@Override
	public ItemStack takeInput() {
		return inputTransactor.removeItem(true);
	}

	@Override
	public int putInput(ItemStack input) {
		return inputTransactor.add(input, true);
	}

	@Override
	public ItemStack takeFusionStar() {
		return starTransactor.removeItem(true);
	}

	@Override
	public int putFusionStar(ItemStack fusionStar) {
		return starTransactor.add(fusionStar, true);
	}

	@Override
	public ItemStack takeJournal() {
		return null;
	}

	@Override
	public int putJournal(ItemStack journal) {
		return 0;
	}

	@Override
	public String getMachineState() {
		if (starInventory.getStackInSlot(0) == null) {
			return "needfusionstar";
		} else if (isRecharging) {
			return "recharging";
		} else {
			return "powered";
		}
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i == kFusionStar[0]) {
			if (itemstack.itemID == Item.netherStar.itemID || itemstack.itemID == MinechemItems.fusionStar.itemID) {
				return true;
			}
		}
		for (int slot : kInput) {
			if (i == slot && itemstack.getItem() instanceof ItemElement) {
				return true;
			}
		}
		return false;
	}

	public int[] getSizeInventorySide(int side) {
		switch (side) {
			case 0:
			case 1:
				return kInput;
			default:
				return kOutput;
		}
	}

	@Override
	void sendUpdatePacket() {
		// TODO Auto-generated method stub

	}

	public int getFusionEnergyStored() {
		if (this.inventory[0] != null) {
			return this.inventory[0].getMaxDamage() - this.inventory[0].getItemDamage();
		}
		return 0;
	}

}
