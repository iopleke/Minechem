package pixlepix.minechem.common.tileentity;

import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.inventory.ISpecialInventory;
import buildcraft.api.transport.IPipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import pixlepix.minechem.api.core.Chemical;
import pixlepix.minechem.api.recipe.DecomposerRecipe;
import pixlepix.minechem.api.util.Util;
import pixlepix.minechem.client.ModelDecomposer;
import pixlepix.minechem.common.gates.IMinechemTriggerProvider;
import pixlepix.minechem.common.gates.MinechemTriggers;
import pixlepix.minechem.common.inventory.BoundedInventory;
import pixlepix.minechem.common.inventory.Transactor;
import pixlepix.minechem.common.network.PacketDecomposerUpdate;
import pixlepix.minechem.common.network.PacketHandler;
import pixlepix.minechem.common.recipe.DecomposerFluidRecipe;
import pixlepix.minechem.common.recipe.DecomposerRecipeHandler;
import pixlepix.minechem.common.utils.MinechemHelper;
import pixlepix.minechem.computercraft.IMinechemMachinePeripheral;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class TileEntityDecomposer extends MinechemTileEntity implements ISidedInventory, ITriggerProvider, IMinechemTriggerProvider,
		ISpecialInventory, IMinechemMachinePeripheral, IFluidHandler {

	public static final int[] kInput = { 0 };
	public static final int[] kOutput = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private static final float MIN_WORK_PER_SECOND = 1.0F;
	private static final float MAX_WORK_PER_SECOND = 10.0F;
	private ArrayList<ItemStack> outputBuffer;
	public final int kInputSlot = 0;
	public final int kOutputSlotStart = 1;
	public final int kOutputSlotEnd = 9;
	public final int kEmptyBottleSlotsSize = 4;
	public final int kOutputSlotsSize = 9;
	public State state = State.kProcessIdle;
	private ItemStack activeStack;
	private float workToDo = 0;
	public ModelDecomposer model;
	private boolean hasFullEnergy;

	private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);
	private final BoundedInventory inputInventory = new BoundedInventory(this, kInput);
	private final Transactor outputTransactor = new Transactor(outputInventory);
	private final Transactor inputTransactor = new Transactor(inputInventory);

	private static final int MIN_ENERGY_RECIEVED = 2;
	private static final int MAX_ENERGY_RECIEVED = 20;
	private static final int MIN_ACTIVATION_ENERGY = 0;
	private static final int MAX_ENERGY_STORED = 10000;

	ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		Iterator iter = fluids.iterator();

		int maxFill = resource.amount;

		if (!doFill) {
			return resource.amount;
		}

		while (iter.hasNext() && resource.amount > 0) {
			FluidStack fluid = (FluidStack) iter.next();

			if (fluid.isFluidEqual(resource)) {
				int amount = Math.min(resource.amount, fluid.amount);

				resource.amount -= amount;
				fluid.amount += amount;

			}
		}
		if (resource.amount > 0) {
			fluids.add(resource);
		}

		return maxFill;

	}

	public boolean isFluidValidForDecomposer(Fluid fluid) {
		for (DecomposerRecipe recipe : DecomposerRecipe.recipes) {
			if (recipe instanceof DecomposerFluidRecipe) {
				DecomposerFluidRecipe fluidRecipe = (DecomposerFluidRecipe) recipe;

				if (fluidRecipe.inputFluid.equals(fluid)) {
					return true;
				}

			}
		}
		return false;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
		//return isFluidValidForDecomposer(fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidTankInfo[] result = new FluidTankInfo[fluids.size() + 1];

		for (int i = 0; i < fluids.size(); i++) {
			result[i] = new FluidTankInfo(fluids.get(i), 100000);
		}
		result[result.length - 1] = new FluidTankInfo(null, 10000);
		return result;
	}

	public enum State {
		kProcessIdle,
		kProcessActive,
		kProcessFinished,
		kProcessJammed
	}

	public TileEntityDecomposer() {
		inventory = new ItemStack[getSizeInventory()];
		outputBuffer = new ArrayList<ItemStack>();

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			model = new ModelDecomposer();
		}
		ActionManager.registerTriggerProvider(this);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote && this.inputInventory.getStackInSlot(0) == null && state == State.kProcessIdle) {
			for (int i = 0; i < fluids.size(); i++) {
				FluidStack input = fluids.get(i);
				for (DecomposerRecipe recipeToTest : DecomposerRecipe.recipes) {
					if (recipeToTest instanceof DecomposerFluidRecipe && input.isFluidEqual(((DecomposerFluidRecipe) recipeToTest).inputFluid)) {

						if (fluids.get(i).amount > ((DecomposerFluidRecipe) recipeToTest).inputFluid.amount) {
							this.setInventorySlotContents(this.kInputSlot, new ItemStack(((DecomposerFluidRecipe) recipeToTest).inputFluid.getFluid().getBlockID(), 1, 0));
							fluids.get(i).amount -= ((DecomposerFluidRecipe) recipeToTest).inputFluid.amount;
						}

					}

				}
			}
		}
		this.doWork();
		if (!worldObj.isRemote && (this.didEnergyStoredChange() || this.didEnergyUsageChange()))
			sendUpdatePacket();

		if (energyStored >= this.getMaxEnergyStored(ForgeDirection.UP))
			hasFullEnergy = true;
		if (hasFullEnergy && energyStored < this.getMaxEnergyStored(ForgeDirection.UP) / 2)
			hasFullEnergy = false;

		if ((state == State.kProcessIdle || state == State.kProcessFinished) && canDecomposeInput()) {
			activeStack = null;
			decomposeActiveStack();
			state = State.kProcessActive;
			this.onInventoryChanged();
		} else if (state == State.kProcessFinished) {
			activeStack = null;
			state = State.kProcessIdle;
		} else if (state == State.kProcessJammed && canUnjam()) {
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
		if (state != State.kProcessActive) {
			this.lastEnergyUsed = 0;
			return;
		}

		State oldState = state;
		float energyUsed = Math.min(this.energyStored, this.MAX_ENERGY_RECIEVED);
		this.energyStored -= energyUsed;
		this.lastEnergyUsed = energyUsed / 20;

		workToDo += MinechemHelper.translateValue(energyUsed, this.MIN_ENERGY_RECIEVED, this.MAX_ENERGY_RECIEVED, MIN_WORK_PER_SECOND / 20, MAX_WORK_PER_SECOND / 20);
		this.workToDo *= 10;
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
		if (inputStack == null) {
			return false;
		}
		DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(inputStack);

		return (recipe != null);
	}

	private void decomposeActiveStack() {
		ItemStack inputStack = getActiveStack();
		DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(inputStack);
		ArrayList<Chemical> output = recipe.getOutput();
		if (recipe != null && output != null) {

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
			if (addStackToOutputSlots(outputStack.copy().splitStack(1))) {
				outputStack.splitStack(1);
				if (outputStack.stackSize == 0)
					outputBuffer.remove(outputStack);
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

	@Override
	public int addItem(ItemStack incoming, boolean doAdd, ForgeDirection from) {

		if (incoming != null) {

			return inputTransactor.add(incoming, doAdd);

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
		return (state != State.kProcessJammed && (this.getEnergyStored() > this.getMinEnergyNeeded()));
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
	public boolean isJammed() {
		return this.state == State.kProcessJammed;
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
		return false;
	}

	public int[] getSizeInventorySide(int side) {
		switch (side) {
			case 1:
				return kInput;
			default:
				return kOutput;
		}
	}

	public float getMinEnergyNeeded() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {

		if (var1 == 1) {
			return this.kInput;
		}

		return this.kOutput;

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

	//Hacky code
	//To fix a FZ glitch
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		if (slot == this.kOutput[0]) {
			ItemStack oldStack = this.inventory[this.kOutput[0]];
			if (oldStack != null && itemstack != null && oldStack.getItemDamage() == itemstack.getItemDamage()) {
				if (oldStack.getItem() == itemstack.getItem()) {
					if (oldStack.stackSize > itemstack.stackSize) {
						this.decrStackSize(slot, oldStack.stackSize - itemstack.stackSize);
					}
				}
			}
		}
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
			itemstack.stackSize = this.getInventoryStackLimit();
		this.inventory[slot] = itemstack;
	}
}
