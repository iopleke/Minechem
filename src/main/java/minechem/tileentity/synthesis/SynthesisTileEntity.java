package minechem.tileentity.synthesis;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

import minechem.MinechemItemsRegistration;
import minechem.Settings;
import minechem.item.molecule.MoleculeEnum;
import minechem.network.MessageHandler;
import minechem.network.message.SynthesisUpdateMessage;
import minechem.tileentity.prefab.BoundedInventory;
import minechem.tileentity.prefab.MinechemTileEntityElectric;
import minechem.utils.Compare;
import minechem.utils.MinechemHelper;
import minechem.utils.Transactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class SynthesisTileEntity extends MinechemTileEntityElectric implements ISidedInventory
{

	/**
	 * Output slot for completed item the machine will create.
	 */
	public static final int[] kOutput =
	{
		0
	};

	/**
	 * Inventory slots that are "ghost" slots used to show the inputs of a crafting recipe from active recipe in Chemist Journal.
	 */
	public static final int[] kRecipe =
	{
		1, 2, 3, 4, 5, 6, 7, 8, 9
	};

	/**
	 * Input slots that make up the crafting grid so players can assemble molecules into needed shapes.
	 */
	public static final int[] kStorage =
	{
		10, 11, 12, 13, 14, 15, 16, 17, 18
	};

	/**
	 * Journal slot number.
	 */
	public static final int[] kJournal =
	{
		19
	};

	/**
	 * Slots that contain *real* items. For the purpose of dropping upon break. These are bottles, storage, and journal.
	 */
	public static int[] kRealSlots;

	/**
	 * Holds the current result for whatever the crafting matrix contains. This can change as the player moves the items around.
	 */
	private SynthesisRecipe currentRecipe;

	/**
	 * Client-side only model that is used to represent the machine to the player.
	 */
	public SynthesisModel model;

	/**
	 * Holds the maximum number of input slots on the crafting matrix. Same as a crafting table in vanilla Minecraft.
	 */
	public static final int kSizeStorage = 9;

	/**
	 * Holds the slot number for the output slot for created item.
	 */
	public static final int kStartOutput = 0;

	/**
	 * Holds the starting slot number for the 'ghost' inventory slots that makeup the recipe from Chemist Journal.
	 */
	public static final int kStartRecipe = 1;

	/**
	 * Starting slot number for actual crafting grid matrix that will create an item from those chemicals.
	 */
	public static final int kStartStorage = 10;

	/**
	 * Slot number for Chemist's Journal which can activate needed synthesis recipe on crafting matrix.
	 */
	public static final int kStartJournal = 19;

	/**
	 * Wrapper for 'ghost' inventory items that show recipe from Chemist Journal.
	 */
	private final BoundedInventory recipeMatrix = new BoundedInventory(this, kRecipe);

	/**
	 * Wrapper for crafting matrix items that make up recipe for synthesis machine.
	 */
	private final BoundedInventory storageInventory = new BoundedInventory(this, kStorage);

	/**
	 * Wrapper for output slot that will hold the end result the machine will produce for the player.
	 */
	private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);

	/**
	 * Wrapper for Chemist's Journal slot that will read the currently active item from the journal to show in 'ghost' recipe slots.
	 */
	private final BoundedInventory journalInventory = new BoundedInventory(this, kJournal);

	/**
	 * Wrapper for moving items in and out of the custom crafting matrix.
	 */
	private final Transactor storageTransactor = new Transactor(storageInventory);

	/**
	 * Wrapper for moving items in and out of the output slot.
	 */
	private final Transactor outputTransactor = new Transactor(outputInventory);

	/**
	 * Wrapper for moving items in and out of the Chemist's Journal slot.
	 */
	private final Transactor journalTransactor = new Transactor(journalInventory, 1);

	public SynthesisTileEntity()
	{
		super(Settings.maxSynthesizerStorage);

		// Creates internal inventory that will represent all of the needed slots that makeup the machine.
		inventory = new ItemStack[getSizeInventory()];

		// Initializes the individual inventory slots and assigns them accordingly.
		ArrayList l = new ArrayList();

		// Creates the slots for 'ghost' items that will show recipe from Chemist's Journal.
		for (int v : kStorage)
		{
			l.add(v);
		}

		// Creates the slot for the chemists journal to be read from.
		for (int v : kJournal)
		{
			l.add(v);
		}

		// Creates the slots that makeup the actual crafting grid items will assemble onto.
		kRealSlots = new int[l.size()];
		for (int idx = 0; idx < l.size(); idx++)
		{
			// Jump through some auto-unboxing hoops due to primitive types not being first-class types.
			kRealSlots[idx] = (Integer) l.get(idx);
		}

		// Creates the model for the device on the client only.
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			// TODO: Replace with model loader and move to client proxy.
			model = new SynthesisModel();
		}
	}

	/**
	 * Determines if the player or automation is allowed to take the item from output slot.
	 */
	public boolean canTakeOutputStack(boolean doTake)
	{
		boolean theState = inventory[kOutput[0]] != null && hasEnoughPowerForCurrentRecipe() && takeStacksFromStorage(doTake);
		return theState;
	}

	/**
	 * Clears the ghost recipe items that are not real and only used to help the player place his own items down.
	 */
	public void clearRecipeMatrix()
	{
		for (int slot : kRecipe)
		{
			inventory[slot] = null;
		}
	}

	private boolean valueIn(int value, int[] arr)
	{
		if (arr == null)
		{
			return false;
		}

		for (int v : arr)
		{
			if (value == v)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the given inventory slot is a "ghost" slot used to show the output of the configured recipe. Ghost output items don't really exist and should never be dumped or extracted,
	 * except when the recipe is crafted.
	 *
	 * @param slotId Slot Id to check.
	 * @return true if the slot is a "ghost" slot for the recipe output.
	 */
	public boolean isGhostOutputSlot(int slotId)
	{
		return valueIn(slotId, kOutput);
	}

	/**
	 * Returns true if the given inventory slot is a "ghost" slot used to show the inputs of a crafting recipe. Items in these slots don't really exist and should never be dumped or extracted.
	 *
	 * @param slotId Slot Id to check.
	 * @return true if the slot is a "ghost" slot for the recipe.
	 */
	public boolean isGhostCraftingRecipeSlot(int slotId)
	{
		return valueIn(slotId, kRecipe);
	}

	/**
	 * Returns true if the given inventory slot holds a "ghost" item that doesn't really exist.
	 *
	 * @param slotId Slot Id to check.
	 * @return true if the slot holds a "ghost" item.
	 */
	public boolean isGhostSlot(int slotId)
	{
		return isGhostOutputSlot(slotId) || isGhostCraftingRecipeSlot(slotId);
	}

	/**
	 * Returns true if the given inventory slot can hold a real (non-ghost) item, i.e., one that is really stored in the inventory.
	 *
	 * @param slotId Slot Id to check.
	 * @return true if the slot can hold a real item.
	 */
	public boolean isRealItemSlot(int slotId)
	{
		return !isGhostSlot(slotId);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if (slot == kJournal[0])
		{
			clearRecipeMatrix();
		}

		if (this.inventory[slot] != null)
		{
			ItemStack itemstack;
			if (slot == kOutput[0])
			{
				int toRemove = amount;
				ItemStack result = getStackInSlot(slot).copy();
				while (toRemove > 0)
				{

					if (takeInputStacks())
					{
						toRemove -= 1;
					} else
					{
						result.stackSize = amount - toRemove;
						return result;
					}

					if (toRemove < 1)
					{
						result.stackSize = amount;
						return result;
					}
				}
				return null;
			}
			else if (this.inventory[slot].stackSize <= amount)
			{
				itemstack = this.inventory[slot];
				this.inventory[slot] = null;
				return itemstack;
			} else
			{
				itemstack = this.inventory[slot].splitStack(amount);
				if (this.inventory[slot].stackSize == 0)
				{
					this.inventory[slot] = null;
				}
				return itemstack;
			}
		}
		return null;
	}

	/**
	 * Determines if there is any 'real' output to be given based on what is left in the internal buffer.
	 */
	public ItemStack[] extractOutput(boolean doRemove, int maxItemCount)
	{
		// Stops execution if no recipe, empty output buffer, or no power or not enough items.
		if (currentRecipe == null || !takeStacksFromStorage(false) || !canAffordRecipe(currentRecipe))
		{
			return null;
		}

		// Make a copy of the item that will be given to the player.
		ItemStack outputStack = currentRecipe.getOutput().copy();
		ItemStack[] output = new ItemStack[]
		{
			outputStack
		};

		// Actually removes the items from the output buffer.
		if (doRemove)
		{
			takeStacksFromStorage(true);
		}

		// Item that will be given to the player.
		return output;
	}

	/**
	 * Returns the current recipe for real items that the player has inserted into the machines crafting matrix.
	 */
	public SynthesisRecipe getCurrentRecipe()
	{
		return currentRecipe;
	}

	/**
	 * Get an ordinal number representing the direction the block is facing based on metadata.
	 */
	public int getFacing()
	{
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	@Override
	public String getInventoryName()
	{
		return "container.synthesis";
	}

	/**
	 * Returns ItemStack array of ghost items that makeup the recipe for whatever is the active recipe in the chemists journal in that slot.
	 */
	public ItemStack[] getRecipeMatrixItems()
	{
		return recipeMatrix.copyInventoryToArray();
	}

	@Override
	public int getSizeInventory()
	{
		return 20;
	}

	/**
	 * Determines if there is enough power to allow the player to take the item from the output slot.
	 */
	public boolean hasEnoughPowerForCurrentRecipe()
	{
		if (!Settings.powerUseEnabled) return true;
		if (this.currentRecipe != null)
		{
			return canAffordRecipe(this.currentRecipe);
		}
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityPlayer)
	{
		double dist = entityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D);
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : dist <= 64.0D;
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
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList inventoryTagList = nbt.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
		inventory = MinechemHelper.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);
	}

	@Override
	public int getEnergyNeeded()
	{
		if (this.currentRecipe != null && Settings.powerUseEnabled)
		{
			return this.currentRecipe.energyCost();
		}
		return 0;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		if (slot == kOutput[0] && getStackInSlot(slot) != null)
		{
			if (itemstack == null)
			{
				this.decrStackSize(slot, 1);
				return;
			}
			if (getStackInSlot(slot).getItem() == itemstack.getItem())
			{
				this.decrStackSize(slot, itemstack.stackSize);
				return;
			}
		}

		super.setInventorySlotContents(slot, itemstack);
		if (slot == kJournal[0] && itemstack != null)
		{
			onPutJournal(itemstack);
		}
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	/**
	 * Determines if there are items in the internal buffer which can be moved into the output slots. Allows the action of moving them to be stopped with doTake being false.
	 */
	public boolean takeStacksFromStorage(boolean doTake)
	{
		// Don't allow the machine to perform synthesis when no recipe or power.
		if (this.currentRecipe == null || !this.hasEnoughPowerForCurrentRecipe())
		{
			return false;
		}

		// One of the most important features in Minechem is the ability to recombine decomposed molecules and elements into items again.
		ItemStack[] ingredients = MinechemHelper.convertChemicalArrayIntoItemStackArray(currentRecipe.getShapelessRecipe());
		ItemStack[] storage = storageInventory.copyInventoryToArray();
		for (ItemStack ingredient : ingredients)
		{
			if (!takeStackFromStorage(ingredient, storage))
			{
				return false;
			}
		}

		if (doTake)
		{
			storageInventory.setInventoryStacks(storage);

			// Consume the required amount of energy that was the cost of the item being created.
			this.useEnergy(this.currentRecipe.energyCost());
		}

		return true;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (!worldObj.isRemote)
		{
			SynthesisUpdateMessage message = new SynthesisUpdateMessage(this);
			MessageHandler.INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, Settings.UpdateRadius));
		}
		// Forces the output slot to only take a single item preventing stacking.
		if (currentRecipe != null && inventory[kOutput[0]] == null)
		{
			inventory[kOutput[0]] = currentRecipe.getOutput().copy();
		} else
		{
			this.validate();
		}
	}

	@Override
	public void validate()
	{
		super.validate();
		getRecipeResult();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
		nbt.setTag("inventory", inventoryTagList);
	}

	/**
	 * Determines if there is enough energy in the machines internal reserve to allow the creation of this item.
	 */
	public boolean canAffordRecipe(SynthesisRecipe recipe)
	{
		return this.getEnergyStored() >= recipe.energyCost();
	}

	/**
	 * Returns the current recipe result for whatever is in the crafting matrix.
	 */
	private boolean getRecipeResult()
	{
		ItemStack[] recipeMatrixItems = getRecipeMatrixItems();
		SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromInput(recipeMatrixItems);

		if (recipe != null)
		{
			inventory[kOutput[0]] = recipe.getOutput().copy();
			currentRecipe = recipe;
			return true;
		} else
		{
			inventory[kOutput[0]] = null;
			currentRecipe = null;
			return false;
		}
	}

	/**
	 * Called when the player places his chemists journal into the slot for it and sets ghost items to selected item recipe if active.
	 */
	private void onPutJournal(ItemStack itemstack)
	{
		ItemStack activeItem = MinechemItemsRegistration.journal.getActiveStack(itemstack);
		if (activeItem != null)
		{
			SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(activeItem);
			if (recipe!=null)
				setRecipe(recipe);
		}
	}

	private boolean takeStackFromStorage(ItemStack ingredient, ItemStack[] storage)
	{
		if (ingredient==null) return true;
		int ingredientAmountLeft = ingredient.stackSize;
		for (int slot = 0; slot < storage.length; slot++)
		{
			ItemStack storageItem = storage[slot];
			if (storageItem != null && Compare.stacksAreSameKind(storageItem, ingredient))
			{
				int amountToTake = Math.min(storageItem.stackSize, ingredientAmountLeft);
				ingredientAmountLeft -= amountToTake;
				storageItem.stackSize -= amountToTake;

				if (storageItem.stackSize <= 0)
				{
					storage[slot] = null;
				}

				if (ingredientAmountLeft <= 0)
				{
					break;
				}
			}
		}
		return ingredientAmountLeft == 0;
	}

	private boolean takeInputStacks()
	{
		if (takeStacksFromStorage(false))
		{
			takeStacksFromStorage(true);
			return true;
		}

		return false;
	}

	public ItemStack getOutputTemplate()
	{
		ItemStack template = null;
		ItemStack outputStack = inventory[kOutput[0]];
		if (outputStack != null)
		{
			template = outputStack.copy();
			if (template.stackSize == 0)
			{
				template.stackSize = 1;
			}
		}
		return template;
	}

	public List<ItemStack> getOutput(int amount)
	{
		if (currentRecipe == null)
		{
			return null;
		}

		ItemStack template = getOutputTemplate();
		List<ItemStack> outputs = new ArrayList<ItemStack>();
		ItemStack initialStack = template.copy();
		initialStack.stackSize = 0;
		outputs.add(initialStack);

		while (canTakeOutputStack(false) && (amount > 0) && takeInputStacks())
		{
			ItemStack output = outputs.get(outputs.size() - 1);
			if (output.stackSize + template.stackSize > output.getMaxStackSize())
			{
				int leftOverStackSize = template.stackSize - (output.getMaxStackSize() - output.stackSize);
				output.stackSize = output.getMaxStackSize();
				if (leftOverStackSize > 0)
				{
					ItemStack newOutput = template.copy();
					newOutput.stackSize = leftOverStackSize;
					outputs.add(newOutput);
				}
			} else
			{
				output.stackSize += template.stackSize;
			}

			this.markDirty();
			amount--;
		}

		return outputs;
	}

	/**
	 * Sets ghost items that will make the crafting recipe from currently selected item in chemists journal if located in that slot.
	 */
	public void setRecipe(SynthesisRecipe recipe)
	{
		clearRecipeMatrix();

		if (recipe != null)
		{
			ItemStack[] ingredients = MinechemHelper.convertChemicalArrayIntoItemStackArray(recipe.getShapedRecipe());
			for (int i = 0; i < Math.min(kRecipe.length, ingredients.length); i++)
			{
				inventory[kRecipe[i]] = ingredients[i];
			}

			//onInventoryChanged();
		}
	}

	public int[] getSizeInventorySide(int side)
	{
		switch (side)
		{
			case 1:
				return kStorage;
			default:
				return kOutput;
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		// Strangely every item is always valid in the crafting matrix according to this, even though slot code prevents anything but elements of molecules.
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1)
	{
		if (var1 != 1 && takeStacksFromStorage(false))
		{
			return SynthesisTileEntity.kOutput;
		}

		return SynthesisTileEntity.kStorage;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side)
	{
		return Settings.AllowAutomation && (itemstack.getItem() == MinechemItemsRegistration.element || itemstack.getItem() == MinechemItemsRegistration.molecule);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side)
	{
		return Settings.AllowAutomation && canTakeOutputStack(false) && side == 0;
	}

	public String getState() {
		return canTakeOutputStack(false)?"Active":inventory[kOutput[0]] == null?"No Recipe": !hasEnoughPowerForCurrentRecipe()?"No Power":"Not Enough Ingredients";
	}
}
