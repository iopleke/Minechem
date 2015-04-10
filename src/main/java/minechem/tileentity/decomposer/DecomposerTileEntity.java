package minechem.tileentity.decomposer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import minechem.Settings;
import minechem.api.IDecomposerControl;
import minechem.network.MessageHandler;
import minechem.network.message.DecomposerDumpFluidMessage;
import minechem.network.message.DecomposerUpdateMessage;
import minechem.potion.PotionChemical;
import minechem.tileentity.prefab.BoundedInventory;
import minechem.tileentity.prefab.MinechemTileEntityElectric;
import minechem.utils.Compare;
import minechem.utils.MinechemUtil;
import minechem.utils.Transactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class DecomposerTileEntity extends MinechemTileEntityElectric implements ISidedInventory, IFluidHandler
{
    /**
     * Input Slots
     */
    public static final int[] inputSlots =
    {
        0
    };

    /**
     * Output Slots
     */
    public static final int[] outputSlots =
    {
        1, 2, 3, 4, 5, 6, 7, 8, 9,
        10, 11, 12, 13, 14, 15, 16, 17, 18
    };

    /**
     * Holds a reference to the itemstack that is being held in the input slot.
     */
    private ItemStack activeStack;

    /**
     * Holds a reference to all known fluids that are stored inside of the machine currently and being decomposed.
     */
    public FluidStack tank = null;
    public int capacity = 5000; // holds 5 buckets

    /**
     * Wrapper for input inventory functions.
     */
    private final BoundedInventory inputInventory = new BoundedInventory(this, inputSlots);

    /**
     * Wrapper for interacting with input slots.
     */
    private final Transactor inputTransactor = new Transactor(inputInventory);

    /**
     * Number of input slot 1.
     */
    public final int kInputSlot = 0;

    /**
     * Number of ending output slot.
     */
    public final int kOutputSlotEnd = 18;

    /**
     * Number of starting output slot.
     */
    public final int kOutputSlotStart = 1;

    /**
     * Instance of our model for the decomposer.
     */
    public DecomposerModel model;

    /**
     * Items waiting to be unloaded into output slots from decomposition process.
     */
    private ArrayList<ItemStack> outputBuffer;

    /**
     * Wrapper for output inventory functions.
     */
    private final BoundedInventory outputInventory = new BoundedInventory(this, outputSlots);

    /**
     * Wrapper for interacting with output slots.
     */
    private final Transactor outputTransactor = new Transactor(outputInventory);

    /**
     * Holds the current state of our machine. Valid States: IDLE, ACTIVE, FINISHED, JAMMED
     */
    public State state = State.idle;
    public State oldState = State.idle;

    // Used for updates
    public boolean bufferChanged = false;
    public boolean tankUpdate = false;
    private static ItemStack cheatTankStack = new ItemStack(Blocks.air);

    public DecomposerTileEntity()
    {
        super(Settings.maxDecomposerStorage);

        // Sets up internal input and output slots used by the server to keep track of items inside of the machine for processing.
        inventory = new ItemStack[getSizeInventory()];
        outputBuffer = new ArrayList<ItemStack>();

        // Loads the model for the decomposer if we are on the client.
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            // TODO: Replace with model loader and move to client proxy.
            model = new DecomposerModel();
        }
    }

    /**
     * Moves items from the output buffer and into the actual output slots of the machine.
     */
    private boolean addStackToOutputSlots(ItemStack itemstack)
    {
        for (int outputSlot : outputSlots)
        {
            ItemStack stackInSlot = getStackInSlot(outputSlot);
            if (stackInSlot != null && Compare.stacksAreSameKind(stackInSlot, itemstack) && (stackInSlot.stackSize + itemstack.stackSize) <= getInventoryStackLimit())
            {
                stackInSlot.stackSize += itemstack.stackSize;
                return true;
            }
        }
        itemstack.getItem().onCreated(itemstack, this.worldObj, null);
        for (int outputSlot : outputSlots)
        {
            ItemStack stackInSlot = getStackInSlot(outputSlot);

            if (stackInSlot == null)
            {
                setInventorySlotContents(outputSlot, itemstack);
                return true;
            }

        }
        return false;
    }

    /**
     * Determines if it is possible to decompose the current itemStack in the input slot.
     */
    private boolean canDecomposeInput()
    {
        ItemStack inputStack = getStackInSlot(inputSlots[0]);
        DecomposerRecipe recipe = null;
        if (inputStack != null)
        {
            recipe = DecomposerRecipe.get(inputStack);
        } else if (tank != null)
        {
            recipe = DecomposerRecipe.get(tank);
            if (recipe != null && ((DecomposerFluidRecipe) recipe).inputFluid.amount > tank.amount)
            {
                return false;
            }
        }
        return (recipe != null);
    }

    private boolean energyToDecompose()
    {
        return this.getEnergyStored() >= Settings.costDecomposition || !Settings.powerUseEnabled;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return true;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return itemstack != null && DecomposerRecipeHandler.instance.getRecipe(itemstack) != null;
    }

    @Override
    public void closeInventory()
    {

    }

    /**
     * One of the most important functions in Minechem is the ability to decompose items into base molecules.
     */
    private void decomposeActiveStack()
    {
        try
        {
            ItemStack inputStack = getActiveStack();
            DecomposerRecipe recipe;
            if (inputStack == cheatTankStack)
            {
                recipe = DecomposerRecipe.get(tank);
            } else
            {
                recipe = DecomposerRecipe.get(inputStack);
            }

            if (recipe != null && this.useEnergy(getEnergyNeeded()))
            {
                ArrayList<PotionChemical> output = recipe.getOutput();
                if (output != null)
                {
                    ArrayList<ItemStack> stacks = MinechemUtil.convertChemicalsIntoItemStacks(getBrokenOutput(output, getDecompositionMultiplier(inputStack)));
                    placeStacksInBuffer(stacks);
                }
            }
        } catch (Exception e)
        {
            // softly falls the rain
            // but the forest does not see
            // silently this fails
        }

    }

    private double getDecompositionMultiplier(ItemStack stack)
    {
        if (stack.getItem() instanceof IDecomposerControl)
        {
            return ((IDecomposerControl) stack.getItem()).getDecomposerMultiplier(stack);
        } else if (!stack.hasTagCompound())
        {
            return 1.0D;
        } else if (stack.getTagCompound().hasKey("damage", 3))
        {
            return 1 - ((double) stack.getTagCompound().getInteger("damage")) / 100D;
        } else if (stack.getTagCompound().hasKey("broken", 1))
        {
            return stack.getTagCompound().getBoolean("broken") ? 0.0D : 1.0D;
        }
        return 1.0D;
    }

    private ArrayList<PotionChemical> getBrokenOutput(ArrayList<PotionChemical> output, double mult)
    {
        if (mult == 1)
        {
            return output;
        }
        if (mult <= 0)
        {
            return new ArrayList<PotionChemical>();
        }
        ArrayList<PotionChemical> result = new ArrayList<PotionChemical>();
        for (PotionChemical chemical : output)
        {
            PotionChemical addChemical = chemical.copy();
            addChemical.amount *= mult;
            result.add(addChemical);
        }
        return result;
    }

    /**
     * Returns JAMMED state if there are items in the output buffer but output slots are full. Returns ACTIVE state if there is room in output slots for output buffer items. Returns FINISHED if output
     * buffer is empty and output slots have all items [that was decomposed].
     */
    private State determineOperationalState()
    {
        for (ItemStack outputStack : outputBuffer)
        {
            if (addStackToOutputSlots(outputStack.copy().splitStack(1)))
            {
                outputStack.splitStack(1);
                if (outputStack.stackSize == 0)
                {
                    outputBuffer.remove(outputStack);
                }
                bufferChanged = true;
                return State.active;
            } else
            {
                return State.jammed;
            }
        }
        return State.finished;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || tank == null || resource.amount <= 0 || !tank.isFluidEqual(resource))
        {
            return null;
        }

        if (!doDrain)
        {
            return new FluidStack(tank.getFluid(), Math.min(tank.amount, resource.amount));
        }

        int drained = Math.min(tank.amount, resource.amount);
        tankUpdate = true;

        tank.amount -= drained;
        if (tank.amount == 0) tank = null;
        return new FluidStack(resource.getFluid(), drained);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        if (tank == null || maxDrain <= 0)
        {
            return null;
        }

        if (!doDrain)
        {
            return new FluidStack(tank.getFluid(), Math.min(tank.amount, maxDrain));
        }

        int drained = Math.min(tank.amount, maxDrain);
        tankUpdate = true;

        tank.amount -= drained;
        FluidStack result = new FluidStack(tank.getFluid(), drained);
        if (tank.amount==0) tank=null;
        return result;
    }

    /**
     * Expands the internal fluid storage array to keep as many different fluids as needed in order to decompose them properly.
     */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (tank!=null && tank.amount==0) tank=null;
        if (resource == null || (tank != null && !tank.isFluidEqual(resource)))
        {
            return 0;
        }

        if (!doFill)
        {
            if (tank == null)
            {
                return Math.min(capacity, resource.amount);
            }

            if (!tank.isFluidEqual(resource))
            {
                return 0;
            }

            return Math.min(capacity - tank.amount, resource.amount);
        }

        int maxFill;
        tankUpdate = true;

        if (tank == null)
        {
            maxFill = Math.min(capacity, resource.amount);
            tank = new FluidStack(resource.getFluid(), maxFill);
            return maxFill;
        }

        if (!tank.isFluidEqual(resource))
        {
            return 0;
        }

        maxFill = Math.min(capacity - tank.amount, resource.amount);
        tank.amount += maxFill;
        return maxFill;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {

        if (var1 == 1)
        {
            return DecomposerTileEntity.inputSlots;
        }

        return DecomposerTileEntity.outputSlots;

    }

    private ItemStack getActiveStack()
    {
        if (activeStack == null)
        {
            if (getStackInSlot(inputSlots[0]) != null)
            {
                activeStack = decrStackSize(inputSlots[0], 1);
                bufferChanged = true;
            } else if (tank != null)
            {
                DecomposerFluidRecipe fluidRecipe = (DecomposerFluidRecipe) DecomposerRecipe.get(tank);
                if (fluidRecipe != null)
                {
                    if (tank.amount >= fluidRecipe.inputFluid.amount)
                    {
                        tank.amount -= fluidRecipe.inputFluid.amount;
                        tankUpdate = true;
                        activeStack = cheatTankStack;
                        return cheatTankStack;
                    }
                }
            } else
            {
                return null;
            }
        }
        return activeStack;
    }

    @Override
    public String getInventoryName()
    {
        return "container.decomposer";
    }

    @Override
    public int getSizeInventory()
    {
        return 19;
    }

    public int[] getSizeInventorySide(int side)
    {
        switch (side)
        {
            case 1:
                return inputSlots;
            default:
                return outputSlots;
        }
    }

    /**
     * Returns the current working state of the machine as an object.
     */
    public State getState()
    {
        return state;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        FluidTankInfo[] result = new FluidTankInfo[1];
        result[0] = new FluidTankInfo(tank, capacity);
        return result;
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    /**
     * Determines if there is a valid recipe to decompose a given fluid in our internal fluid array storage.
     *
     * @param fluid Fluid to be checked
     * @return boolean
     */
    public boolean isFluidValidForDecomposer(Fluid fluid)
    {

        //Minechem.LOGGER.info(fluid.toString());
        return DecomposerRecipe.get(new FluidStack(fluid, 1)) != null;

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        if (i == inputSlots[0])
        {
            DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(itemstack);

            if (recipe != null)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void openInventory()
    {

    }

    /**
     * Accepts an array list of itemStacks that should be outputted into the machines output slot over time and with electric power.
     */
    private void placeStacksInBuffer(ArrayList<ItemStack> outputStacks)
    {
        if (outputStacks != null)
        {
            outputBuffer = outputStacks;
        } else
        {
            state = State.finished;
        }
        pushQueue();
    }

    /**
     * Combines stacks when possible
     */
    private void pushQueue()
    {
        for (int i = this.inventory.length - 1; i > 0; i--)
        {
            if (i == 1 || this.inventory[i] == null)
            {
                continue;
            }
            for (int j = 1; j < i; j++)
            {
                if (this.inventory[j] == null)
                {
                    setInventorySlotContents(j, this.inventory[i]);
                    setInventorySlotContents(i, null);
                    break;
                } else if (Compare.stacksAreSameKind(this.inventory[j], this.inventory[i]))
                {
                    int spaceLeft = this.inventory[j].getMaxStackSize() - this.inventory[j].stackSize;
                    if (spaceLeft >= this.inventory[i].stackSize)
                    {
                        this.inventory[j].stackSize += this.inventory[i].stackSize;
                        this.inventory[i] = null;
                    } else
                    {
                        this.inventory[j].stackSize += spaceLeft;
                        this.inventory[i].stackSize -= spaceLeft;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        NBTTagList inventoryTagList = nbt.getTagList("inventory", Constants.NBT.TAG_COMPOUND);

        NBTTagList buffer = nbt.getTagList("buffer", Constants.NBT.TAG_COMPOUND);

        outputBuffer = MinechemUtil.readTagListToItemStackList(buffer);
        inventory = MinechemUtil.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);

        if (nbt.getTag("activeStack") != null)
        {
            NBTTagCompound activeStackCompound = (NBTTagCompound) nbt.getTag("activeStack");
            activeStack = ItemStack.loadItemStackFromNBT(activeStackCompound);
        }

        if (nbt.hasKey("tank"))
        {
            tank = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("tank"));
        } else
        {
            tank = null;
        }

        state = State.values()[nbt.getByte("state")];
    }

    @Override
    public int getEnergyNeeded()
    {
        return Settings.powerUseEnabled ? Settings.costDecomposition : 0;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        if (slot == DecomposerTileEntity.outputSlots[0])
        {
            ItemStack oldStack = this.inventory[DecomposerTileEntity.outputSlots[0]];
            if (oldStack != null && itemstack != null && oldStack.getItemDamage() == itemstack.getItemDamage())
            {
                if (oldStack.getItem() == itemstack.getItem())
                {
                    if (oldStack.stackSize > itemstack.stackSize)
                    {
                        this.decrStackSize(slot, oldStack.stackSize - itemstack.stackSize);
                    }
                }
            }
        }

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

        this.inventory[slot] = itemstack;
    }

    /**
     * Sets the current state of the machine using an integer to reference an enumeration.
     */
    public void setState(int state)
    {
        this.state = State.values()[state];
    }

    @Override
    public void updateEntity()
    {
        // Trigger updates in classes below us.
        super.updateEntity();

        // Prevents any code below this line from running on clients.
        if (worldObj.isRemote)
        {
            return;
        }

        // Determines the current state of the machine.
        state = determineOperationalState();
        if ((state == State.idle || state == State.finished) && energyToDecompose() && canDecomposeInput())
        {
            // Determines if machine has nothing to process or finished processing and has ability to decompose items in the input slot.
            activeStack = null;
            decomposeActiveStack();
            state = State.active;
            //this.onInventoryChanged();
        } else if (state == State.finished)
        {
            // Prepares the machine for another pass if we have recently finished.
            activeStack = null;
            state = State.idle;
        }
        if (bufferChanged || state != oldState)
        {
            this.markDirty();
            bufferChanged = false;
        }
        updateStateHandler();
    }

    public void updateStateHandler()
    {
        if (state != oldState || oldEnergyStored != getEnergyStored() || tankUpdate)
        {
            oldState = state;
            oldEnergyStored = getEnergyStored();

            tankUpdate = false;
            // Notify minecraft that the inventory items in this machine have changed.
            DecomposerUpdateMessage message = new DecomposerUpdateMessage(this);
            MessageHandler.INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, Settings.UpdateRadius));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        NBTTagList inventoryTagList = MinechemUtil.writeItemStackArrayToTagList(inventory);

        NBTTagList buffer = MinechemUtil.writeItemStackListToTagList(outputBuffer);

        nbt.setTag("inventory", inventoryTagList);

        nbt.setTag("buffer", buffer);

        if (activeStack != null)
        {
            NBTTagCompound activeStackCompound = new NBTTagCompound();
            activeStack.writeToNBT(activeStackCompound);
            nbt.setTag("activeStack", activeStackCompound);
        }

        if (tank != null)
        {
            NBTTagCompound tankTag = new NBTTagCompound();
            tank.writeToNBT(tankTag);
            nbt.setTag("tank", tankTag);
        }

        nbt.setByte("state", (byte) state.ordinal());
    }

    @Override
    public Packet getDescriptionPacket()
    {
        writeToNBT(new NBTTagCompound());
        return MessageHandler.INSTANCE.getPacketFrom(new DecomposerUpdateMessage(this));
    }

    public String getStateString()
    {
        return (activeStack == null || DecomposerRecipe.get(activeStack) == null) ? "No Recipe" : energyToDecompose() ? "Active" : "No Power";
    }

    public void dumpFluid()
    {
        this.tank = null;
        if (worldObj.isRemote)
        {
            MessageHandler.INSTANCE.sendToServer(new DecomposerDumpFluidMessage(this));
        }
    }

    /**
     * Enumeration of states that the decomposer can be in. Allows for easier understanding of code and interaction with user.
     */
    public enum State
    {
        idle, active, finished, jammed
    }
}
