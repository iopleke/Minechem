package minechem.common.tileentity;

import java.util.ArrayList;
import java.util.List;

import minechem.api.recipe.SynthesisRecipe;
import minechem.api.util.Util;
import minechem.client.ModelSynthesizer;
import minechem.common.MinechemItems;
import minechem.common.inventory.BoundedInventory;
import minechem.common.inventory.Transactor;
import minechem.common.network.PacketHandler;
import minechem.common.network.PacketSynthesisUpdate;
import minechem.common.recipe.SynthesisRecipeHandler;
import minechem.common.utils.MinechemHelper;
import minechem.computercraft.IMinechemMachinePeripheral;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntitySynthesis extends MinechemTileEntity implements ISidedInventory, IMinechemMachinePeripheral
{
    public static final int[] kOutput =
    { 0 };
    public static final int[] kRecipe =
    { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    public static final int[] kStorage =
    { 10, 11, 12, 13, 14, 15, 16, 17, 18 };
    public static final int[] kJournal =
    { 19 };
    // Slots that contain *real* items
    // For the purpose of dropping upon break. These are bottles, storage, and
    // journal.
    public static final int[] kRealSlots;

    // Ensure that the list of real slots stays in sync with the above defs.
    static
    {
        ArrayList l = new ArrayList();
        for (int v : kStorage)
        {
            l.add(v);
        }
        for (int v : kJournal)
        {
            l.add(v);
        }
        kRealSlots = new int[l.size()];
        for (int idx = 0; idx < l.size(); idx++)
        {
            // Jump through some autounboxing hoops due to primitive types not
            // being first-class types.
            kRealSlots[idx] = (Integer) l.get(idx);
        }
    }

    private SynthesisRecipe currentRecipe;
    public ModelSynthesizer model;
    public static final int kSizeOutput = 1;
    public static final int kSizeRecipe = 9;
    public static final int kSizeStorage = 9;
    public static final int kSizeJournal = 1;
    public static final int kStartOutput = 0;
    public static final int kStartRecipe = 1;
    public static final int kStartStorage = 10;
    public static final int kStartJournal = 19;
    private final BoundedInventory recipeMatrix = new BoundedInventory(this, kRecipe);
    private final BoundedInventory storageInventory = new BoundedInventory(this, kStorage);
    private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);
    private final BoundedInventory journalInventory = new BoundedInventory(this, kJournal);
    private final Transactor storageTransactor = new Transactor(storageInventory);
    private final Transactor outputTransactor = new Transactor(outputInventory);
    private final Transactor recipeMatrixTransactor = new Transactor(recipeMatrix);
    private final Transactor journalTransactor = new Transactor(journalInventory, 1);

    private static final int MAX_ENERGY_RECIEVED = 200;
    private static final int MAX_ENERGY_STORED = 1022220;

    private boolean hasFullEnergy;

    public TileEntitySynthesis()
    {
        super(MAX_ENERGY_STORED, MAX_ENERGY_RECIEVED);

        inventory = new ItemStack[getSizeInventory()];

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            model = new ModelSynthesizer();
        }
    }

    public boolean canTakeOutputStack()
    {
        return inventory[kOutput[0]] != null && hasEnoughPowerForCurrentRecipe() && takeStacksFromStorage(false);
    }

    public void clearRecipeMatrix()
    {
        for (int slot : kRecipe)
        {
            inventory[slot] = null;
        }
    }

    @Override
    public void closeChest()
    {
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

    /** Returns true iff the given inventory slot is a "ghost" slot used to show the output of the configured recipe. Ghost output items don't really exist and should never be dumped or extracted, except when the recipe is crafted.
     * 
     * @param slotId Slot Id to check.
     * @return true iff the slot is a "ghost" slot for the recipe output. */
    public boolean isGhostOutputSlot(int slotId)
    {
        return valueIn(slotId, kOutput);
    }

    /** Returns true iff the given inventory slot is a "ghost" slot used to show the inputs of a crafting recipe. Items in these slots don't really exist and should never be dumped or extracted.
     * 
     * @param slotId Slot Id to check.
     * @return true iff the slot is a "ghost" slot for the recipe. */
    public boolean isGhostCraftingRecipeSlot(int slotId)
    {
        return valueIn(slotId, kRecipe);
    }

    /** Returns true iff the given inventory slot holds a "ghost" item that doesn't really exist.
     * 
     * @param slotId Slot Id to check.
     * @return true iff the slot holds a "ghost" item. */
    public boolean isGhostSlot(int slotId)
    {
        return isGhostOutputSlot(slotId) || isGhostCraftingRecipeSlot(slotId);
    }

    /** Returns true iff the given inventory slot can hold a real (non-ghost) item, i.e., one that is really stored in the inventory.
     * 
     * @param slotId Slot Id to check.
     * @return true iff the slot can hold a real item. */
    public boolean isRealItemSlot(int slotId)
    {
        return !isGhostSlot(slotId);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (slot == kJournal[0])
            clearRecipeMatrix();
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
                        takeEnergy(currentRecipe);
                        toRemove--;
                    }
                    else
                    {
                        if (amount == toRemove)
                        {
                            return null;
                        }
                        else
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
            }
            else
            {
                itemstack = this.inventory[slot].splitStack(amount);
                if (this.inventory[slot].stackSize == 0)
                    this.inventory[slot] = null;
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack[] extractOutput(boolean doRemove, int maxItemCount)
    {
        if (currentRecipe == null || !takeStacksFromStorage(false) || !canAffordRecipe(currentRecipe))
            return null;
        ItemStack outputStack = currentRecipe.getOutput().copy();
        ItemStack[] output = new ItemStack[]
        { outputStack };
        if (doRemove)
        {
            takeEnergy(currentRecipe);
            takeStacksFromStorage(true);
        }
        return output;
    }

    public SynthesisRecipe getCurrentRecipe()
    {
        return currentRecipe;
    }

    public int getFacing()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    @Override
    public String getInvName()
    {
        return "container.synthesis";
    }

    public ItemStack[] getRecipeMatrixItems()
    {
        return recipeMatrix.copyInventoryToArray();
    }

    @Override
    public int getSizeInventory()
    {
        return 24;
    }

    public boolean hasEnoughPowerForCurrentRecipe()
    {
        return currentRecipe != null && this.getEnergy(ForgeDirection.UNKNOWN) >= currentRecipe.energyCost();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        double dist = entityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D);
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : dist <= 64.0D;
    }

    @Override
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
        getRecipeResult();
    }

    public void onOuputPickupFromSlot()
    {
        if (takeInputStacks())
            takeEnergy(currentRecipe);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        NBTTagList inventoryTagList = nbtTagCompound.getTagList("inventory");
        inventory = MinechemHelper.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);

    }

    @Override
    public void sendUpdatePacket()
    {
        PacketSynthesisUpdate packet = new PacketSynthesisUpdate(this);
        int dimensionID = worldObj.provider.dimensionId;
        PacketHandler.getInstance().synthesisUpdateHandler.sendToAllPlayersInDimension(packet, dimensionID);
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
            onPutJournal(itemstack);
    }

    public boolean takeStacksFromStorage(boolean doTake)
    {
        if (this.currentRecipe == null || !this.hasEnoughPowerForCurrentRecipe())
        {
            return false;
        }
        List<ItemStack> ingredients = MinechemHelper.convertChemicalsIntoItemStacks(currentRecipe.getShapelessRecipe());
        ItemStack[] storage = storageInventory.copyInventoryToArray();
        for (ItemStack ingredient : ingredients)
        {
            if (!takeStackFromStorage(ingredient, storage))
                return false;
        }
        if (doTake)
        {
            storageInventory.setInventoryStacks(storage);
        }
        return true;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!worldObj.isRemote && (this.didEnergyStoredChange() || this.didEnergyUsageChange()))
            sendUpdatePacket();

        float energyStored = this.getEnergy(ForgeDirection.UNKNOWN);
        if (energyStored >= this.getEnergyCapacity(ForgeDirection.UP))
        {
            hasFullEnergy = true;
        }

        if (hasFullEnergy && energyStored < this.getEnergyCapacity(ForgeDirection.UP) / 2)
        {
            hasFullEnergy = false;
        }

        if (this.getEnergy(ForgeDirection.UNKNOWN) >= this.getEnergyCapacity(ForgeDirection.UP))
        {
            hasFullEnergy = true;
        }

        if (hasFullEnergy && this.getEnergy(ForgeDirection.UNKNOWN) < this.getEnergyCapacity(ForgeDirection.UP) / 2)
        {
            hasFullEnergy = false;
        }

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
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
        nbtTagCompound.setTag("inventory", inventoryTagList);
    }

    private boolean canAffordRecipe(SynthesisRecipe recipe)
    {
        int energyCost = recipe.energyCost();
        return this.getEnergy(ForgeDirection.UNKNOWN) >= energyCost;
    }

    private boolean getRecipeResult()
    {
        ItemStack[] recipeMatrixItems = getRecipeMatrixItems();
        SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromInput(recipeMatrixItems);
        if (recipe != null)
        {
            inventory[kOutput[0]] = recipe.getOutput().copy();
            currentRecipe = recipe;
            return true;
        }
        else
        {
            inventory[kOutput[0]] = null;
            currentRecipe = null;
            return false;
        }
    }

    private void onPutJournal(ItemStack itemstack)
    {
        ItemStack activeItem = MinechemItems.journal.getActiveStack(itemstack);
        if (activeItem != null)
        {
            SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(activeItem);
            setRecipe(recipe);
        }
    }

    public long lastRecipeTick = 0;

    private void takeEnergy(SynthesisRecipe recipe)
    {
        int energyCost = recipe.energyCost();
        this.lastRecipeTick = worldObj.getTotalWorldTime();
        this.consumeEnergy(energyCost);
    }

    private boolean takeStackFromStorage(ItemStack ingredient, ItemStack[] storage)
    {
        int ingredientAmountLeft = ingredient.stackSize;
        for (int slot = 0; slot < storage.length; slot++)
        {
            ItemStack storageItem = storage[slot];
            if (storageItem != null && Util.stacksAreSameKind(storageItem, ingredient))
            {
                int amountToTake = Math.min(storageItem.stackSize, ingredientAmountLeft);
                ingredientAmountLeft -= amountToTake;
                storageItem.stackSize -= amountToTake;
                if (storageItem.stackSize <= 0)
                    storage[slot] = null;
                if (ingredientAmountLeft <= 0)
                    break;
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
                template.stackSize = 1;
        }
        return template;
    }

    public List<ItemStack> getOutput(int amount, boolean all)
    {
        if (currentRecipe == null)
            return null;
        ItemStack template = getOutputTemplate();
        List<ItemStack> outputs = new ArrayList<ItemStack>();
        ItemStack initialStack = template.copy();
        initialStack.stackSize = 0;
        outputs.add(initialStack);
        while (canTakeOutputStack() && (amount > 0 || all) && takeInputStacks())
        {
            takeEnergy(currentRecipe);
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
            }
            else
            {
                output.stackSize += template.stackSize;
            }
            onInventoryChanged();
            amount--;
        }
        return outputs;
    }

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
            onInventoryChanged();
        }
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
        return storageTransactor.removeItem(true);
    }

    @Override
    public int putInput(ItemStack input)
    {
        return storageTransactor.add(input, true);
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
    public ItemStack takeJournal()
    {
        return journalTransactor.removeItem(true);
    }

    @Override
    public int putJournal(ItemStack journal)
    {
        return journalTransactor.add(journal, true);
    }

    @Override
    public String getMachineState()
    {
        if (currentRecipe == null)
        {
            return "norecipe";
        }
        else
        {
            int energyCost = currentRecipe.energyCost();
            if (this.getEnergy(ForgeDirection.UNKNOWN) >= energyCost)
                return "powered";
            else
                return "unpowered";
        }
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
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
        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        // This is so hacky
        // I'm honestly ashamed

        if (var1 != 1 && takeStacksFromStorage(false))
        {
            return TileEntitySynthesis.kOutput;
        }
        return TileEntitySynthesis.kStorage;

    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return true;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        if (i == kOutput[0])
        {
            if (takeStacksFromStorage(false))
            {
                return true;
            }
            return false;
        }
        return true;
    }

    public void addStackToInventory(ItemStack newStack)
    {
        for (int i = 0; i < this.storageInventory.getSizeInventory(); i++)
        {
            ItemStack stack = this.storageInventory.getStackInSlot(i);
            if (stack == null)
            {
                this.storageInventory.setInventorySlotContents(i, newStack);
                return;
            }
            if (stack.stackSize < 64 && stack.getItem() == newStack.getItem() && stack.getItemDamage() == newStack.getItemDamage())
            {
                stack.stackSize++;
                return;
            }
        }
        worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 2, zCoord, newStack));
    }
}
