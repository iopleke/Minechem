package ljdp.minechem.common.tileentity;

import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.api.util.Constants;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.blueprint.BlueprintFusion;
import ljdp.minechem.common.inventory.BoundedInventory;
import ljdp.minechem.common.inventory.Transactor;
import ljdp.minechem.common.items.ItemElement;
import ljdp.minechem.common.utils.MinechemHelper;
import ljdp.minechem.computercraft.IMinechemMachinePeripheral;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.SafeTimeTracker;

public class TileEntityFission extends TileEntityMultiBlock implements  IMinechemMachinePeripheral {

    public static int[] kInput = { 0 };
    public static int[] kFuel = { 1};
    public static int[] kOutput = { 2};

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

    public TileEntityFission() {
    	inventory = new ItemStack[getSizeInventory()];
        inputInventory = new BoundedInventory(this, kInput);
        outputInventory = new BoundedInventory(this, kOutput);
        fuelInventory = new BoundedInventory(this, kFuel);
        inputTransactor = new Transactor(inputInventory);
        outputTransactor = new Transactor(outputInventory);
        fuelTransactor = new Transactor(fuelInventory, 1);
        this.inventory = new ItemStack[this.getSizeInventory()];
        setBlueprint(new BlueprintFusion());
    }

    @Override
	public void updateEntity() {
		super.updateEntity();
		if(!completeStructure)
			return;
		shouldSendUpdatePacket = false;
		if(!worldObj.isRemote && worldObj.getTotalWorldTime()%50==0 && inventory[kStartFuel] != null 
				&& energyUpdateTracker.markTimeIfDelay(worldObj, Constants.TICKS_PER_SECOND * 2))
		{
		
				ItemStack fissionResult = getFissionOutput();
					if(!worldObj.isRemote) {
						addToOutput(fissionResult);
						removeInputs();
					}
					fissionResult = getFissionOutput();
					shouldSendUpdatePacket = true;
				
			}
		
		if(shouldSendUpdatePacket && !worldObj.isRemote)
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
    }

    private boolean canFuse(ItemStack fusionResult) {
        ItemStack itemInOutput = inventory[kOutput[0]];
        if (itemInOutput != null)
            return itemInOutput.stackSize < getInventoryStackLimit() && itemInOutput.isItemEqual(fusionResult);
        return true;
    }

    private ItemStack getFissionOutput() {
        if (hasInputs()) {
            int mass = inventory[kInput[0]].getItemDamage() + 1;
            int newMass=mass/2;
            if (newMass >1) {
                return new ItemStack(MinechemItems.element, 1, newMass - 1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    private boolean hasInputs() {
        return inventory[kInput[0]] != null && inventory[kInput[1]] != null;
    }

    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        
        this.inventory[slot] = itemstack;
        
    }

    @Override
    public String getInvName() {
        return "container.minechemFission";
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
        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
        nbtTagCompound.setTag("inventory", inventoryTagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        inventory = new ItemStack[getSizeInventory()];
        MinechemHelper.readTagListToItemStackArray(nbtTagCompound.getTagList("inventory"), inventory);
    }


    public void setEnergyStored(int amount) {
        this.energyStored = amount;
    }

    @Override
    public ItemStack takeEmptyTestTube() {
        return null;
    }

    @Override
    public int putEmptyTestTube(ItemStack testTube) {
        return 0;
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
    public ItemStack takeJournal() {
        return null;
    }

    @Override
    public int putJournal(ItemStack journal) {
        return 0;
    }

    @Override
    public String getMachineState() {
    		//TODO Check for fuel
            return "powered";
        
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        
        for (int slot : kInput) {
            if(i==slot&&itemstack.getItem() instanceof ItemElement){
                return true;
            }
        }
        return false;
    }

    public int[] getSizeInventorySide(int side) {
        switch (side) {
        case 0:
        	return kOutput;
        case 1:
            return kInput;
        default:
            return kFuel;
        }
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return false;
    }


	@Override
	void sendUpdatePacket() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public float getProvide(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxEnergyStored() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	//Horrible design here
	//These are abstract methods
	//Of TileEntityMultiBlock
	
	@Override
	public ItemStack takeFusionStar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int putFusionStar(ItemStack fusionStar) {
		// TODO Auto-generated method stub
		return 0;
	}



}
