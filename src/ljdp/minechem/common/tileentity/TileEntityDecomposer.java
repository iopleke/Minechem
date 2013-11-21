package ljdp.minechem.common.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.api.util.Util;
import ljdp.minechem.client.ModelDecomposer;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.gates.IMinechemTriggerProvider;
import ljdp.minechem.common.gates.MinechemTriggers;
import ljdp.minechem.common.inventory.BoundedInventory;
import ljdp.minechem.common.inventory.Transactor;
import ljdp.minechem.common.items.ItemElement;
import ljdp.minechem.common.items.ItemMolecule;
import ljdp.minechem.common.network.PacketDecomposerUpdate;
import ljdp.minechem.common.network.PacketHandler;
import ljdp.minechem.common.recipe.DecomposerRecipeHandler;
import ljdp.minechem.common.utils.MinechemHelper;
import ljdp.minechem.computercraft.IMinechemMachinePeripheral;
import ljdp.minechem.fluid.FluidHelper;
import ljdp.minechem.fluid.IMinechemFluid;
import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.inventory.ISpecialInventory;
import buildcraft.api.transport.IPipe;

public class TileEntityDecomposer extends MinechemTileEntity implements ISidedInventory, ITriggerProvider, IMinechemTriggerProvider,
        ISpecialInventory, IMinechemMachinePeripheral, IFluidHandler {

    public static final int[] kInput = { 0 };
    public static final int[] kOutput = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    public static final int[] kBottles = { 10, 11, 12, 13 };
    private static final float MIN_WORK_PER_SECOND = 1.0F;
    private static final float MAX_WORK_PER_SECOND = 10.0F;
    private ArrayList<ItemStack> outputBuffer;
	public final int kInputSlot = 0;
	public final int kOutputSlotStart    = 1;
	public final int kOutputSlotEnd		= 9;
	public final int kEmptyTestTubeSlotStart = 10;
	public final int kEmptyTestTubeSlotEnd   = 13;
	public final int kEmptyBottleSlotsSize = 4;
	public final int kOutputSlotsSize		= 9;
    public State state = State.kProcessIdle;
    private ItemStack activeStack;
    private float workToDo = 0;
    public ModelDecomposer model;
    private boolean hasFullEnergy;

    private final BoundedInventory testTubeInventory = new BoundedInventory(this, kBottles);
    private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);
    private final BoundedInventory inputInventory = new BoundedInventory(this, kInput);
    private final Transactor testTubeTransactor = new Transactor(testTubeInventory);
    private final Transactor outputTransactor = new Transactor(outputInventory);
    private final Transactor inputTransactor = new Transactor(inputInventory);

    private static final int MIN_ENERGY_RECIEVED = 2;
    private static final int MAX_ENERGY_RECIEVED = 20;
    private static final int MIN_ACTIVATION_ENERGY = 0;
    private static final int MAX_ENERGY_STORED = 10000;

    public enum State {
        kProcessIdle,
        kProcessActive,
        kProcessFinished,
        kProcessJammed,
        kProcessNoBottles
    }

    public TileEntityDecomposer() {
        inventory = new ItemStack[getSizeInventory()];
        outputBuffer = new ArrayList<ItemStack>();

    	if(FMLCommonHandler.instance().getSide()==Side.CLIENT){
    		model = new ModelDecomposer();
    	}
        ActionManager.registerTriggerProvider(this);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        this.doWork();
        if (!worldObj.isRemote && (this.didEnergyStoredChange() || this.didEnergyUsageChange()))
            sendUpdatePacket();

        if (energyStored >= this.getMaxEnergyStored())
            hasFullEnergy = true;
        if (hasFullEnergy && energyStored < this.getMaxEnergyStored() / 2)
            hasFullEnergy = false;

        if ((state == State.kProcessIdle || state == State.kProcessFinished) && canDecomposeInput()) {
            activeStack = null;
            decomposeActiveStack();
            state = State.kProcessActive;
            this.onInventoryChanged();
        } else if (!canTakeEmptyTestTube()) {
            state = State.kProcessNoBottles;
        } else if (state == State.kProcessFinished) {
            activeStack = null;
            state = State.kProcessIdle;
        } else if (state == State.kProcessJammed && canUnjam()) {
            state = State.kProcessActive;
        } else if (state == State.kProcessNoBottles && canTakeEmptyTestTube()) {
            state = State.kProcessActive;
        }
    }

    @Override
    public void sendUpdatePacket() {
        if (worldObj.isRemote)
            return;
        PacketDecomposerUpdate packetDecomposerUpdate = new PacketDecomposerUpdate(this);
        int dimensionID = worldObj.provider.dimensionId;
        PacketHandler.getInstance().decomposerUpdateHandler.sendToAllPlayersInDimension(packetDecomposerUpdate, dimensionID);
    }

    private ItemStack getActiveStack() {
        if (activeStack == null) {
            if (getStackInSlot(kInput[0]) != null) {
                activeStack = decrStackSize(kInput[0], 1);
            } else {
                return null;
            }
        }
        return activeStack;
    }
    //bad code is fun!
    public void doWork() {
        if (state != State.kProcessActive){
        	this.lastEnergyUsed=0;
            return;
        }

        State oldState = state;
        float energyUsed = Math.min(this.energyStored, this.MAX_ENERGY_RECIEVED);
        this.energyStored-=energyUsed;
        this.lastEnergyUsed=energyUsed/20;
        
        workToDo += MinechemHelper.translateValue(energyUsed, this.MIN_ENERGY_RECIEVED, this.MAX_ENERGY_RECIEVED, MIN_WORK_PER_SECOND / 20, MAX_WORK_PER_SECOND / 20);
        this.workToDo*=10;
        if (!worldObj.isRemote) {
            while (workToDo >= 1) {
                workToDo--;
                state = moveBufferItemToOutputSlot();
                if (state != State.kProcessActive)
                    break;
            }
            this.onInventoryChanged();
            if (!state.equals(oldState)) {
                sendUpdatePacket();
            }
        }
    }

    private boolean canDecomposeInput() {
        ItemStack inputStack = getStackInSlot(kInput[0]);
        if (inputStack == null)
            return false;
        DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(inputStack);
        return (recipe != null) && canTakeEmptyTestTube();
    }

    private void decomposeActiveStack() {
        ItemStack inputStack = getActiveStack();
        DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(inputStack);
        ArrayList<Chemical> output = recipe.getOutput();
        if (recipe != null && output != null) {
        	if(inputStack.getItem() instanceof ItemElement || inputStack.getItem() instanceof ItemMolecule){
        		testTubeTransactor.add(new ItemStack(MinechemItems.testTube),true);
        	}
            ArrayList<ItemStack> stacks = MinechemHelper.convertChemicalsIntoItemStacks(output);
            placeStacksInBuffer(stacks);
        }
    }

    private void placeStacksInBuffer(ArrayList<ItemStack> outputStacks) {
        if (outputStacks != null) {
            outputBuffer = outputStacks;
        } else {
            state = State.kProcessFinished;
        }
    }

    private boolean canUnjam() {
        for (int slot : kOutput) {
            if (getStackInSlot(slot) == null)
                return true;
        }
        return false;
    }

    private State moveBufferItemToOutputSlot() {
        for (ItemStack outputStack : outputBuffer) {
            if (!canTakeEmptyTestTube())
                return State.kProcessNoBottles;
            else if (addStackToOutputSlots(outputStack.copy().splitStack(1))) {
                outputStack.splitStack(1);
                if (outputStack.stackSize == 0)
                    outputBuffer.remove(outputStack);
                takeEmptyTestTube();
                return State.kProcessActive;
            } else {
                return State.kProcessJammed;
            }
        }
        return State.kProcessFinished;
    }

    private boolean addStackToOutputSlots(ItemStack itemstack) {
        itemstack.getItem().onCreated(itemstack, this.worldObj, null);
        for (int outputSlot : kOutput) {
            ItemStack stackInSlot = getStackInSlot(outputSlot);
            if (stackInSlot == null) {
                setInventorySlotContents(outputSlot, itemstack);
                return true;
            } else if (Util.stacksAreSameKind(stackInSlot, itemstack) && (stackInSlot.stackSize + itemstack.stackSize) <= getInventoryStackLimit()) {
                stackInSlot.stackSize += itemstack.stackSize;
                return true;
            }
        }
        return false;
    }

    private boolean canTakeEmptyTestTube() {
        ItemStack testTube = testTubeTransactor.removeItem(false);
        return testTube != null;
    }

    @Override
    public int addItem(ItemStack incoming, boolean doAdd, ForgeDirection from) {

        if (incoming != null) {
            if (incoming.itemID == MinechemItems.testTube.itemID) {
                return testTubeTransactor.add(incoming, doAdd);
            } else {
                return inputTransactor.add(incoming, doAdd);
            }
        }
        return 0;
    }

    @Override
    public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
        return outputTransactor.remove(maxItemCount, doRemove);
    }

    @Override
    public int getSizeInventory() {
        return 14;
    }

    @Override
    public String getInvName() {
        return "container.decomposer";
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
        NBTTagList buffer = MinechemHelper.writeItemStackListToTagList(outputBuffer);
        nbtTagCompound.setTag("inventory", inventoryTagList);
        nbtTagCompound.setTag("buffer", buffer);
        if (activeStack != null) {
            NBTTagCompound activeStackCompound = new NBTTagCompound();
            activeStack.writeToNBT(activeStackCompound);
            nbtTagCompound.setTag("activeStack", activeStackCompound);
        }
        nbtTagCompound.setByte("state", (byte) state.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        NBTTagList inventoryTagList = nbtTagCompound.getTagList("inventory");
        NBTTagList buffer = nbtTagCompound.getTagList("buffer");
        outputBuffer = MinechemHelper.readTagListToItemStackList(buffer);
        inventory = MinechemHelper.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);

        if (nbtTagCompound.getTag("activeStack") != null) {
            NBTTagCompound activeStackCompound = (NBTTagCompound) nbtTagCompound.getTag("activeStack");
            activeStack = ItemStack.loadItemStackFromNBT(activeStackCompound);
        }
        state = State.values()[nbtTagCompound.getByte("state")];
    }

    public State getState() {
        return state;
    }

    public void setState(int state) {
        this.state = State.values()[state];
    }



    public boolean isPowered() {
        return (state != State.kProcessJammed && state != State.kProcessNoBottles && (this.getEnergyStored() > this.getMinEnergyNeeded()));
    }

    @Override
    public LinkedList<ITrigger> getPipeTriggers(IPipe pipe) {
        return null;
    }

    @Override
    public LinkedList<ITrigger> getNeighborTriggers(Block block, TileEntity tile) {
        if (tile instanceof TileEntityDecomposer) {
            LinkedList<ITrigger> triggers = new LinkedList<ITrigger>();
            triggers.add(MinechemTriggers.fullEnergy);
            triggers.add(MinechemTriggers.noTestTubes);
            triggers.add(MinechemTriggers.outputJammed);
            return triggers;
        }
        return null;
    }

    @Override
    public boolean hasFullEnergy() {
        return this.hasFullEnergy;
    }

    @Override
    public boolean hasNoTestTubes() {
        return this.state == State.kProcessNoBottles;
    }

    @Override
    public boolean isJammed() {
        return this.state == State.kProcessJammed;
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
    public int putInput(ItemStack input) {
        return inputTransactor.add(input, true);
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
        return null;
    }

    @Override
    public int putJournal(ItemStack journal) {
        return 0;
    }

    @Override
    public int putOutput(ItemStack output) {
        return outputTransactor.add(output, true);
    }

    @Override
    public ItemStack takeInput() {
        return outputTransactor.removeItem(true);
    }

    @Override
    public String getMachineState() {
        if (this.state == State.kProcessJammed) {
            return "outputjammed";
        } else if (this.state == State.kProcessNoBottles) {
            return "needtesttubes";
        } else if (this.state == State.kProcessActive) {
            return "decomposing";
        } else if (this.getEnergyStored() > this.getMinEnergyNeeded()) {
            return "powered";
        } else {
            return "unpowered";
        }
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if (i == kInput[0])
            return true;
        if (itemstack.itemID == MinechemItems.testTube.itemID)
            for (int slot : kBottles)
                if (i == slot)
                    return true;
        return false;
    }
    public int[] getSizeInventorySide(int side) {
        switch (side) {
        case 0:
        case 1:
            return kBottles;
        case 2:
        case 3:
            return kInput;
        default:
            return kOutput;
        }
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return true;
    }





	public float getMinEnergyNeeded() {
		// TODO Auto-generated method stub
		return 100;
	}


	@Override
	public float getProvide(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxEnergyStored() {
		// TODO Auto-generated method stub
		return MAX_ENERGY_STORED;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		
		if(var1==1){
			return this.kInput;
		}
		if(var1==0){
			return this.kOutput;
		}
		return this.kBottles;
		
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	
	
	
	
	

    public HashMap<Fluid,Integer>  partialFluids=new HashMap();
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		Fluid fluid=FluidRegistry.getFluid(resource.fluidID);
		
		if(fluid instanceof IMinechemFluid&&this.inputInventory.getStackInSlot(0)==null){

			IMinechemFluid minechemFluid=(IMinechemFluid) fluid;

			int previousFluid=0;
			int currentFluid=0;
			if(resource.amount<FluidHelper.FLUID_CONSTANT){
				if(!partialFluids.containsKey(fluid)){
					partialFluids.put(fluid, 0);
				}
				previousFluid=partialFluids.get(fluid);
				currentFluid= resource.amount+previousFluid;
				if(currentFluid<FluidHelper.FLUID_CONSTANT){

					partialFluids.put(fluid,currentFluid);
					return resource.amount;
				}
				partialFluids.put(fluid, currentFluid-FluidHelper.FLUID_CONSTANT);
				
				
				
				
			}
			if(doFill){
				this.inputInventory.setInventorySlotContents(0, minechemFluid.getOutputStack());
			}
			return resource.amount;
		}
		return 0;
		
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return this.drain(from, resource.amount,doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		int toDrain=maxDrain;
			for(int i=0;i<this.outputInventory.getSizeInventory();i++){
				ItemStack stack=this.outputInventory.getStackInSlot(i);
				if(stack==null){
					continue;
				}
				Item item=stack.getItem();
				if(item instanceof ItemElement){
					ItemElement itemMolecule=(ItemElement) item;
					Fluid fluid=FluidHelper.elements.get(ItemElement.getElement(stack));
						this.outputInventory.decrStackSize(i, 1);

						return new FluidStack(fluid,FluidHelper.FLUID_CONSTANT);
					
				}
				if(item instanceof ItemMolecule){
					ItemMolecule itemMolecule=(ItemMolecule) item;
					Fluid fluid=FluidHelper.molecule.get(ItemMolecule.getMolecule(stack));
						this.outputInventory.decrStackSize(i, 1);
						return new FluidStack(fluid,FluidHelper.FLUID_CONSTANT);
					
				}
			
		}
		
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid instanceof IMinechemFluid;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return fluid instanceof IMinechemFluid;
	}
	public FluidTankInfo getTankInfo(int i){
		
		ItemStack stack=this.getStackInSlot(i);
		if(stack==null){
			return null;
		}
		Item item=stack.getItem();
		if(item instanceof ItemElement){
			ItemElement itemMolecule=(ItemElement) item;
			Fluid fluid=FluidHelper.elements.get(ItemElement.getElement(stack));
			if(fluid==null){
				return null;
				//This should never happen.
			}
			return new FluidTankInfo(new FluidStack(fluid,stack.stackSize*FluidHelper.FLUID_CONSTANT),6400);
			
			
		}
		
		if(item instanceof ItemMolecule){
			ItemMolecule itemMolecule=(ItemMolecule) item;
			Fluid fluid=FluidHelper.molecule.get(ItemMolecule.getMolecule(stack));
			if(fluid==null){
				return null;
				//This should never happen.
			}
			return new FluidTankInfo(new FluidStack(fluid,stack.stackSize*FluidHelper.FLUID_CONSTANT),6400);
			}
		
		return null;
			
	}
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		ArrayList fluids=new ArrayList();
		//Uses both input and output slots
		for(int i=0;i<this.kEmptyTestTubeSlotStart;i++){
			FluidTankInfo newFluid=this.getTankInfo(i);
			if(newFluid!=null){
				fluids.add(newFluid);
			}
		}
		return (FluidTankInfo[]) fluids.toArray();
	}
	//Hacky code
	//To fix a FZ glitch
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if(slot==this.kOutput[0]){
			ItemStack oldStack=this.inventory[this.kOutput[0]];
			if(oldStack!=null&&itemstack!=null&&oldStack.getItemDamage()==itemstack.getItemDamage()){
				if(oldStack.getItem()==itemstack.getItem()){
					if(oldStack.stackSize>itemstack.stackSize){
						this.decrStackSize(slot, oldStack.stackSize-itemstack.stackSize);
					}
				}
			}
		}
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
			itemstack.stackSize = this.getInventoryStackLimit();
		this.inventory[slot] = itemstack;
	}
}
