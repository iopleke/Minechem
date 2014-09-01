package minechem.tileentity.synthesis;

import java.util.ArrayList;
import java.util.List;

import minechem.MinechemItemsGeneration;
import minechem.Minechem;
import minechem.Settings;
import minechem.network.packet.SynthesisPacketUpdate;
import minechem.tileentity.prefab.BoundedInventory;
import minechem.tileentity.prefab.MinechemTileEntity;
import minechem.utils.MinechemHelper;
import minechem.utils.Transactor;
import minechem.utils.Compare;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import universalelectricity.api.core.grid.INode;

public class SynthesisTileEntity extends MinechemTileEntity implements ISidedInventory
{
    /**
     * Amount of power the machine will accept in a single update.
     */
    private static final int POWER_INPUT = 200;

    /**
     * Maximum amount of power the machine can accept in total.
     */
    private static final int MAX_POWER = 1022220;

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
        // Establishes maximum power and total amount of power that can be accepted per update.
        super(MAX_POWER, POWER_INPUT);

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
    public boolean canTakeOutputStack()
    {
        return inventory[kOutput[0]] != null && hasEnoughPowerForCurrentRecipe() && takeStacksFromStorage(false);
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
     * Returns true if the given inventory slot is a "ghost" slot used to show the output of the configured recipe. Ghost output items don't really exist and should never be dumped or extracted, except when the recipe is crafted.
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
                while (toRemove > 0)
                {
                    if (takeInputStacks())
                    {
                        toRemove--;
                    } else
                    {
                        if (amount == toRemove)
                        {
                            return null;
                        } else
                        {
                            ItemStack result = getStackInSlot(slot).copy();
                            result.stackSize = (amount - toRemove);
                            return result;
                        }
                    }
                }
            }
            if (this.inventory[slot].stackSize <= amount)
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
        } else
        {
            return null;
        }
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
        return currentRecipe != null && this.getEnergy(ForgeDirection.UNKNOWN) >= currentRecipe.energyCost();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        double dist = entityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D);
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : dist <= 64.0D;
    }

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

//	@Override
//    public void onInventoryChanged()
//    {
//        super.onInventoryChanged();
//        getRecipeResult();
//    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList inventoryTagList = nbt.getTagList("inventory", Constants.NBT.TAG_LIST);
        inventory = MinechemHelper.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        if (slot == kOutput[0] && getStackInSlot(slot) != null)
        {
            if (itemstack == null)
            {
                this.decrStackSize(slot, getStackInSlot(slot).stackSize);
                return;
            }
            if (getStackInSlot(slot).getItem() == itemstack.getItem())
            {
                if (getStackInSlot(slot).stackSize > itemstack.stackSize)
                {
                    this.decrStackSize(slot, getStackInSlot(slot).stackSize - itemstack.stackSize);
                    return;
                }
            }
        }

        super.setInventorySlotContents(slot, itemstack);
        if (slot == kJournal[0] && itemstack != null)
        {
            onPutJournal(itemstack);
        }
    }

	@Override
	public boolean hasCustomInventoryName() {
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
        List<ItemStack> ingredients = MinechemHelper.convertChemicalsIntoItemStacks(currentRecipe.getShapelessRecipe());
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
            this.consumeEnergy(currentRecipe.energyCost());
        }

        return true;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!worldObj.isRemote)
        {
            SynthesisPacketUpdate synthesisPacketUpdate = new SynthesisPacketUpdate(this);
            int dimensionID = worldObj.provider.dimensionId;
	        //TODO: Work on packet system
            Minechem.network.sendPacketAllAround(worldObj, this.xCoord, this.yCoord, this.zCoord, Settings.UpdateRadius, synthesisPacketUpdate);
        }

        // Forces the output slot to only take a single item preventing stacking.
        if (currentRecipe != null && inventory[kOutput[0]] == null)
        {
            inventory[kOutput[0]] = currentRecipe.getOutput().copy();
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
        int energyCost = recipe.energyCost();
        return this.getEnergy(ForgeDirection.UNKNOWN) >= energyCost;
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
        ItemStack activeItem = MinechemItemsGeneration.journal.getActiveStack(itemstack);
        if (activeItem != null)
        {
            SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(activeItem);
            setRecipe(recipe);
        }
    }

    private boolean takeStackFromStorage(ItemStack ingredient, ItemStack[] storage)
    {
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

    public List<ItemStack> getMaximumOutput()
    {
        return getOutput(0, true);
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

    public List<ItemStack> getOutput(int amount, boolean all)
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

        while (canTakeOutputStack() && (amount > 0 || all) && takeInputStacks())
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

            //onInventoryChanged();
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
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return true;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        if (Settings.AllowAutomation)
        {
            if (takeStacksFromStorage(false))
            {
                return true;
            }
        }
        return false;
    }

	@Override
	public <N extends INode> N getNode(Class<N> nodeType, ForgeDirection from) {
		return null;
	}
}
