package ljdp.minechem.common.tileentity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ljdp.minechem.api.recipe.BluePrinterRecipe;
import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.api.util.Constants;
import ljdp.minechem.api.util.Util;
import ljdp.minechem.client.ModelPrinter;
import ljdp.minechem.common.MinechemPowerProvider;
import ljdp.minechem.common.gates.IMinechemTriggerProvider;
import ljdp.minechem.common.gates.MinechemTriggers;
import ljdp.minechem.common.inventory.BoundedInventory;
import ljdp.minechem.common.inventory.Transactor;
import ljdp.minechem.common.network.PacketDecomposerUpdate;
import ljdp.minechem.common.network.PacketHandler;
import ljdp.minechem.common.network.PacketPrinterUpdate;
import ljdp.minechem.common.recipe.BluePrinterrecipeHandler;
import ljdp.minechem.common.tileentity.TileEntityDecomposer.State;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import universalelectricity.core.electricity.ElectricityPack;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.inventory.ISpecialInventory;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.transport.IPipe;

public class TileEntityBluePrintPrinter extends MinechemTileEntity implements ISidedInventory, IPowerReceptor, ITriggerProvider, IMinechemTriggerProvider,
        ISpecialInventory{
    public static final int[] kOutput = { 0 };
    public static final int[] kRecipe = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    public static final int[] kStorage = { 14, 15, 16, 17, 18, 19, 20, 21, 22 };

    private BluePrinterRecipe currentRecipe;
    MinechemPowerProvider powerProvider;
    public ModelPrinter model;
	public static final int kSizeOutput = 1;
	public static final int kSizeRecipe  = 9;
	public static final int kSizeStorage = 9;
	public static final int kStartOutput = 0;
	public static final int kStartRecipe = 1;
	public static final int kStartStorage = 14;
	public static final int kStartJournal = 23;
    private final BoundedInventory recipeMatrix = new BoundedInventory(this, kRecipe);
    private final BoundedInventory storageInventory = new BoundedInventory(this, kStorage);
    private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);
    private final Transactor storageTransactor = new Transactor(storageInventory);
    private final Transactor outputTransactor = new Transactor(outputInventory);
    private final Transactor recipeMatrixTransactor = new Transactor(recipeMatrix);

    private static final int MIN_ENERGY_RECIEVED = 30;
    private static final int MAX_ENERGY_RECIEVED = 200;
    private static final int MIN_ACTIVATION_ENERGY = 100;
    private static final int MAX_ENERGY_STORED = 922220;

    private boolean hasFullEnergy;

    public TileEntityBluePrintPrinter() {
        inventory = new ItemStack[getSizeInventory()];
        powerProvider = new MinechemPowerProvider(MIN_ENERGY_RECIEVED, MAX_ENERGY_RECIEVED, MIN_ACTIVATION_ENERGY, MAX_ENERGY_STORED);
        powerProvider.configurePowerPerdition(1, Constants.TICKS_PER_SECOND * 2);
        model = new ModelPrinter();
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


    public BluePrinterRecipe getCurrentRecipe() {
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
        if (tile instanceof TileEntityBluePrintPrinter) {
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

    

    private boolean canAffordRecipe(BluePrinterRecipe currentRecipe2) {
        int energyCost = currentRecipe2.energyCost();
        return powerProvider.getEnergyStored() >= energyCost;
    }

    private boolean getRecipeResult() {
        ItemStack[] recipeMatrixItems = getRecipeMatrixItems();
        BluePrinterRecipe recipe = BluePrinterrecipeHandler.instance.getRecipeFromInput(recipeMatrixItems);
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


    private void takeEnergy(BluePrinterRecipe currentRecipe2) {
        int energyCost = currentRecipe2.energyCost();
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
		
		return 0;
	}

	@Override
	public
	int getSizeInventorySide(ForgeDirection side) {
		
		return 0;
	}

	@Override
	public boolean hasNoTestTubes() {
		
		return false;
	}


	@Override
	public boolean isJammed() {
		
		return false;
	}

	@Override
	void sendUpdatePacket() {
		 if (worldObj.isRemote)
	            return;
	        PacketPrinterUpdate packetDecomposerUpdate = new PacketPrinterUpdate(this);
	        int dimensionID = worldObj.getWorldInfo().getDimension();
	        PacketHandler.getInstance().printerUpdateHandler.sendToAllPlayersInDimension(packetDecomposerUpdate, dimensionID);
		
	}
}
