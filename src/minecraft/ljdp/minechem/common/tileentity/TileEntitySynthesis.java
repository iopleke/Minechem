package ljdp.minechem.common.tileentity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import universalelectricity.core.electricity.ElectricityPack;

import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.api.util.Constants;
import ljdp.minechem.api.util.Util;
import ljdp.minechem.client.ModelSynthesizer;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.MinechemPowerProvider;
import ljdp.minechem.common.gates.IMinechemTriggerProvider;
import ljdp.minechem.common.gates.MinechemTriggers;
import ljdp.minechem.common.inventory.BoundedInventory;
import ljdp.minechem.common.inventory.Transactor;
import ljdp.minechem.common.network.PacketHandler;
import ljdp.minechem.common.network.PacketSynthesisUpdate;
import ljdp.minechem.common.recipe.SynthesisRecipeHandler;
import ljdp.minechem.common.utils.MinechemHelper;
import ljdp.minechem.computercraft.IMinechemMachinePeripheral;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.inventory.ISpecialInventory;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.transport.IPipe;

public class TileEntitySynthesis extends MinechemTileEntity implements ISidedInventory, IPowerReceptor, ITriggerProvider, IMinechemTriggerProvider,
        ISpecialInventory, IMinechemMachinePeripheral {
    public static final int[] kOutput = { 0 };
    public static final int[] kRecipe = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    public static final int[] kBottles = { 10, 11, 12, 13 };
    public static final int[] kStorage = { 14, 15, 16, 17, 18, 19, 20, 21, 22 };
    public static final int[] kJournal = { 23 };

    private SynthesisRecipe currentRecipe;
    MinechemPowerProvider powerProvider;
    public ModelSynthesizer model;
	public static final int kSizeOutput = 1;
	public static final int kSizeRecipe  = 9;
	public static final int kSizeBottles = 4;
	public static final int kSizeStorage = 9;
	public static final int kSizeJournal = 1;
	public static final int kStartOutput = 0;
	public static final int kStartRecipe = 1;
	public static final int kStartBottles = 10;
	public static final int kStartStorage = 14;
	public static final int kStartJournal = 23;
    private final BoundedInventory recipeMatrix = new BoundedInventory(this, kRecipe);
    private final BoundedInventory storageInventory = new BoundedInventory(this, kStorage);
    private final BoundedInventory tubeInventory = new BoundedInventory(this, kBottles);
    private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);
    private final BoundedInventory journalInventory = new BoundedInventory(this, kJournal);
    private final Transactor testTubeTransactor = new Transactor(tubeInventory);
    private final Transactor storageTransactor = new Transactor(storageInventory);
    private final Transactor outputTransactor = new Transactor(outputInventory);
    private final Transactor recipeMatrixTransactor = new Transactor(recipeMatrix);
    private final Transactor journalTransactor = new Transactor(journalInventory, 1);

    private static final int MIN_ENERGY_RECIEVED = 30;
    private static final int MAX_ENERGY_RECIEVED = 200;
    private static final int MIN_ACTIVATION_ENERGY = 100;
    private static final int MAX_ENERGY_STORED = 922220;

    private boolean hasFullEnergy;

    public TileEntitySynthesis() {
        inventory = new ItemStack[getSizeInventory()];
        powerProvider = new MinechemPowerProvider(MIN_ENERGY_RECIEVED, MAX_ENERGY_RECIEVED, MIN_ACTIVATION_ENERGY, MAX_ENERGY_STORED);
        powerProvider.configurePowerPerdition(1, Constants.TICKS_PER_SECOND * 2);
        model = new ModelSynthesizer();
        ActionManager.registerTriggerProvider(this);
    }

    @Override
    public int addItem(ItemStack stack, boolean doAdd, ForgeDirection direction) {
        return storageTransactor.add(stack, doAdd);
    }

    public boolean canTakeOutputStack() {
        return inventory[kOutput[0]] != null && hasEnoughPowerForCurrentRecipe() && takeStacksFromStorage(false);
    }

    public void clearRecipeMatrix() {
        for (int slot : kRecipe) {
            inventory[slot] = null;
        }
    }

    @Override
    public void closeChest() {}

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot == kJournal[0])
            clearRecipeMatrix();
        if (this.inventory[slot] != null) {
            ItemStack itemstack;
            if (slot == kOutput[0]) {
                if (takeInputStacks())
                    takeEnergy(currentRecipe);
                else
                    return null;
            }
            if (this.inventory[slot].stackSize <= amount) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(amount);
                if (this.inventory[slot].stackSize == 0)
                    this.inventory[slot] = null;
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void doWork() {}

    @Override
    public ItemStack[] extractItem(boolean doRemove, ForgeDirection direction, int maxItemCount) {
        switch (direction) {
        case NORTH:
        case SOUTH:
        case EAST:
        case WEST:
        case UNKNOWN:
            return extractOutput(doRemove, maxItemCount);
        case UP:
        case DOWN:
            return extractTestTubes(doRemove, maxItemCount);

        }
        return new ItemStack[0];
    }

    public ItemStack[] extractOutput(boolean doRemove, int maxItemCount) {
        if (currentRecipe == null || !takeStacksFromStorage(false) || !canAffordRecipe(currentRecipe))
            return null;
        ItemStack outputStack = currentRecipe.getOutput().copy();
        ItemStack[] output = new ItemStack[] { outputStack };
        if (doRemove) {
            takeEnergy(currentRecipe);
            takeStacksFromStorage(true);
        }
        return output;
    }

    public ItemStack[] extractTestTubes(boolean doRemove, int maxItemCount) {
        return testTubeTransactor.remove(maxItemCount, doRemove);
    }

    public SynthesisRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    public int getFacing() {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    @Override
    public String getInvName() {
        return "container.synthesis";
    }

    @Override
    public LinkedList<ITrigger> getNeighborTriggers(Block block, TileEntity tile) {
        if (tile instanceof TileEntitySynthesis) {
            LinkedList<ITrigger> triggers = new LinkedList<ITrigger>();
            triggers.add(MinechemTriggers.fullEnergy);
            triggers.add(MinechemTriggers.noTestTubes);
            triggers.add(MinechemTriggers.outputJammed);
            return triggers;
        }
        return null;
    }

    @Override
    public LinkedList<ITrigger> getPipeTriggers(IPipe pipe) {
        return null;
    }

    @Override
    public IPowerProvider getPowerProvider() {
        return this.powerProvider;
    }

    public ItemStack[] getRecipeMatrixItems() {
        return recipeMatrix.copyInventoryToArray();
    }

    @Override
    public int getSizeInventory() {
        return 24;
    }

    public boolean hasEnoughPowerForCurrentRecipe() {
        return currentRecipe != null && powerProvider.getEnergyStored() >= currentRecipe.energyCost();
    }

    @Override
    public boolean hasFullEnergy() {
        return hasFullEnergy;
    }

    @Override
    public boolean hasNoTestTubes() {
        boolean hasNoTestTubes = true;
        for (int slot : kBottles) {
            if (this.inventory[slot] != null) {
                hasNoTestTubes = false;
                break;
            }
        }
        return hasNoTestTubes;
    }

    @Override
    public boolean isJammed() {
        int count = 0;
        for (int slot : kBottles) {
            if (inventory[slot] != null) {
                count += inventory[slot].stackSize;
            }
        }
        return count >= (64 * 4);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        double dist = entityPlayer.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D);
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : dist <= 64.0D;
    }

    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        getRecipeResult();
    }

    public void onOuputPickupFromSlot() {
        if (takeInputStacks())
            takeEnergy(currentRecipe);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        NBTTagList inventoryTagList = nbtTagCompound.getTagList("inventory");
        inventory = MinechemHelper.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);
        powerProvider.readFromNBT(nbtTagCompound);
    }

    public void sendUpdatePacket() {
        PacketSynthesisUpdate packet = new PacketSynthesisUpdate(this);
        int dimensionID = worldObj.getWorldInfo().getDimension();
        PacketHandler.getInstance().synthesisUpdateHandler.sendToAllPlayersInDimension(packet, dimensionID);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        super.setInventorySlotContents(slot, itemstack);
        if (slot == kJournal[0] && itemstack != null)
            onPutJournal(itemstack);
    }

    @Override
    public void setPowerProvider(IPowerProvider provider) {
        this.powerProvider = (MinechemPowerProvider) provider;
    }

    public boolean takeStacksFromStorage(boolean doTake) {
        List<ItemStack> ingredients = MinechemHelper.convertChemicalsIntoItemStacks(currentRecipe.getShapelessRecipe());
        ItemStack[] storage = storageInventory.copyInventoryToArray();
        for (ItemStack ingredient : ingredients) {
            if (!takeStackFromStorage(ingredient, storage))
                return false;
        }
        if (doTake) {
            addEmptyBottles(currentRecipe.getIngredientCount());
            storageInventory.setInventoryStacks(storage);
        }
        return true;
    }

    @Override
    public void updateEntity() {
    	super.updateEntity();
        powerProvider.receiveEnergy((float) wattsReceived / 437.5F, ForgeDirection.UP);// FIXME
        powerProvider.update(this);
        if (!worldObj.isRemote && (powerProvider.didEnergyStoredChange() || powerProvider.didEnergyUsageChange()))
            sendUpdatePacket();

        float energyStored = powerProvider.getEnergyStored();
        if (energyStored >= powerProvider.getMaxEnergyStored())
            hasFullEnergy = true;
        if (hasFullEnergy && energyStored < powerProvider.getMaxEnergyStored() / 2)
            hasFullEnergy = false;

        if (powerProvider.getEnergyStored() >= powerProvider.getMaxEnergyStored())
            hasFullEnergy = true;
        if (hasFullEnergy && powerProvider.getEnergyStored() < powerProvider.getMaxEnergyStored() / 2)
            hasFullEnergy = false;

        if (currentRecipe != null && inventory[kOutput[0]] == null) {
            inventory[kOutput[0]] = currentRecipe.getOutput().copy();
        }
    }

    @Override
    public void validate() {
        super.validate();
        getRecipeResult();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
        nbtTagCompound.setTag("inventory", inventoryTagList);
        powerProvider.writeToNBT(nbtTagCompound);
    }

    private void addEmptyBottles(int amount) {
        for (int slot : kBottles) {
            if (inventory[slot] == null) {
                int stackSize = Math.min(amount, getInventoryStackLimit());
                setInventorySlotContents(slot, new ItemStack(MinechemItems.testTube, stackSize));
                amount -= stackSize;
            } else if (inventory[slot].itemID == MinechemItems.testTube.itemID) {
                int stackAddition = getInventoryStackLimit() - inventory[slot].stackSize;
                stackAddition = Math.min(amount, stackAddition);
                inventory[slot].stackSize += stackAddition;
                amount -= stackAddition;
            }
            if (amount <= 0)
                break;
        }
        // Drop the test tubes.
        if (amount > 0) {
            ItemStack tubes = new ItemStack(MinechemItems.testTube, amount);
            MinechemHelper.ejectItemStackIntoWorld(tubes, worldObj, xCoord, yCoord, zCoord);
        }
    }

    private boolean canAffordRecipe(SynthesisRecipe recipe) {
        int energyCost = recipe.energyCost();
        return powerProvider.getEnergyStored() >= energyCost;
    }

    private boolean getRecipeResult() {
        ItemStack[] recipeMatrixItems = getRecipeMatrixItems();
        SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromInput(recipeMatrixItems);
        if (recipe != null) {
            inventory[kOutput[0]] = recipe.getOutput().copy();
            currentRecipe = recipe;
            return true;
        } else {
            inventory[kOutput[0]] = null;
            currentRecipe = null;
            return false;
        }
    }

    private void onPutJournal(ItemStack itemstack) {
        ItemStack activeItem = MinechemItems.journal.getActiveStack(itemstack);
        if (activeItem != null) {
            SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(activeItem);
            setRecipe(recipe);
        }
    }

    private void takeEnergy(SynthesisRecipe recipe) {
        int energyCost = recipe.energyCost();
        powerProvider.useEnergy(energyCost, energyCost, true);
    }

    private boolean takeStackFromStorage(ItemStack ingredient, ItemStack[] storage) {
        int ingredientAmountLeft = ingredient.stackSize;
        for (int slot = 0; slot < storage.length; slot++) {
            ItemStack storageItem = storage[slot];
            if (storageItem != null && Util.stacksAreSameKind(storageItem, ingredient)) {
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

    private boolean takeInputStacks() {
        if (takeStacksFromStorage(false)) {
            takeStacksFromStorage(true);
            return true;
        }
        return false;
    }

    public List<ItemStack> getMaximumOutput() {
        return getOutput(0, true);
    }

    public ItemStack getOutputTemplate() {
        ItemStack template = null;
        ItemStack outputStack = inventory[kOutput[0]];
        if (outputStack != null) {
            template = outputStack.copy();
            if (template.stackSize == 0)
                template.stackSize = 1;
        }
        return template;
    }

    public List<ItemStack> getOutput(int amount, boolean all) {
        if (currentRecipe == null)
            return null;
        ItemStack template = getOutputTemplate();
        List<ItemStack> outputs = new ArrayList<ItemStack>();
        ItemStack initialStack = template.copy();
        initialStack.stackSize = 0;
        outputs.add(initialStack);
        while (canTakeOutputStack() && (amount > 0 || all) && takeInputStacks()) {
            takeEnergy(currentRecipe);
            ItemStack output = outputs.get(outputs.size() - 1);
            if (output.stackSize + template.stackSize > output.getMaxStackSize()) {
                int leftOverStackSize = template.stackSize - (output.getMaxStackSize() - output.stackSize);
                output.stackSize = output.getMaxStackSize();
                if (leftOverStackSize > 0) {
                    ItemStack newOutput = template.copy();
                    newOutput.stackSize = leftOverStackSize;
                    outputs.add(newOutput);
                }
            } else {
                output.stackSize += template.stackSize;
            }
            onInventoryChanged();
            amount--;
        }
        return outputs;
    }

    public void setRecipe(SynthesisRecipe recipe) {
        clearRecipeMatrix();
        if (recipe != null) {
            ItemStack[] ingredients = MinechemHelper.convertChemicalArrayIntoItemStackArray(recipe.getShapedRecipe());
            for (int i = 0; i < Math.min(kRecipe.length, ingredients.length); i++) {
                inventory[kRecipe[i]] = ingredients[i];
            }
            onInventoryChanged();
        }
    }

    @Override
    public ItemStack takeEmptyTestTube() {
        return testTubeTransactor.removeItem(true);
    }

    @Override
    public int putEmptyTestTube(ItemStack testTube) {
        return testTubeTransactor.add(testTube, true);
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
        return storageTransactor.removeItem(true);
    }

    @Override
    public int putInput(ItemStack input) {
        return storageTransactor.add(input, true);
    }

    @Override
    public ItemStack takeFusionStar() {
        return null;
    }

    @Override
    public int putFusionStar(ItemStack fusionStar) {
        return 0;
    }

    @Override
    public ItemStack takeJournal() {
        return journalTransactor.removeItem(true);
    }

    @Override
    public int putJournal(ItemStack journal) {
        return journalTransactor.add(journal, true);
    }

    @Override
    public String getMachineState() {
        if (currentRecipe == null) {
            return "norecipe";
        } else {
            int energyCost = currentRecipe.energyCost();
            if (this.powerProvider.getEnergyStored() >= energyCost)
                return "powered";
            else
                return "unpowered";
        }
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

    public int[] getSizeInventorySide(int side) {
        switch (side) {
        case 0:
        case 1:
            return kBottles;
        case 4:
        case 5:
            return kStorage;
        default:
            return kOutput;
        }
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return true;
    }

    @Override
    public ElectricityPack getRequest() {
        return new ElectricityPack(Math.min((powerProvider.getMaxEnergyStored() - powerProvider.getEnergyStored()), powerProvider.getMaxEnergyReceived())
                * 437.5D / this.getVoltage(), this.getVoltage());
    }

    @Override
    public int powerRequest(ForgeDirection from) {
        if (powerProvider.getEnergyStored() < powerProvider.getMaxEnergyStored())
            return powerProvider.getMaxEnergyReceived();
        else
            return 0;
    }


	@Override
	public
	int getStartInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public
	int getSizeInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 0;
	}
}
