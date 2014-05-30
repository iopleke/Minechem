package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Iterator;

import minechem.ModMinechem;
import minechem.Settings;
import minechem.potion.PotionChemical;
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
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class DecomposerTileEntity extends MinechemTileEntity implements ISidedInventory, IFluidHandler
{
    /**
     * Input Slots
     */
    public static final int[] kInput =
    {
        0
    };

    /**
     * Output Slots
     */
    public static final int[] kOutput =
    {
        1, 2, 3, 4, 5, 6, 7, 8, 9
    };

    /**
     * Items waiting to be unloaded into output slots from decomposition process.
     */
    private ArrayList<ItemStack> outputBuffer;

    /**
     * Number of input slot 1.
     */
    public final int kInputSlot = 0;

    /**
     * Number of starting output slot.
     */
    public final int kOutputSlotStart = 1;

    /**
     * Number of ending output slot.
     */
    public final int kOutputSlotEnd = 9;

    /**
     * Holds the current state of our machine. Valid States: IDLE, ACTIVE, FINISHED, JAMMED
     */
    public State state = State.kProcessIdle;

    /**
     * Holds a reference to the itemstack that is being held in the input slot.
     */
    private ItemStack activeStack;

    /**
     * Instance of our model for the decomposer.
     */
    public DecomposerModel model;

    /**
     * Wrapper for output inventory functions.
     */
    private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);

    /**
     * Wrapper for input inventory functions.
     */
    private final BoundedInventory inputInventory = new BoundedInventory(this, kInput);

    /**
     * Wrapper for interacting with output slots.
     */
    private final Transactor outputTransactor = new Transactor(outputInventory);

    /**
     * Wrapper for interacting with input slots.
     */
    private final Transactor inputTransactor = new Transactor(inputInventory);

    /**
     * Determines amount of energy we are allowed to input into the machine with a given update.
     */
    private static final long MAX_ENERGY_RECIEVED = 20;

    /**
     * Determines total amount of energy that this machine can store.
     */
    private static final long MAX_ENERGY_STORED = 10000;

    /**
     * Holds a reference to all known fluids that are stored inside of the machine currently and being decomposed.
     */
    ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return null;
    }

    /**
     * Expands the internal fluid storage array to keep as many different fluids as needed in order to decompose them properly.
     */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        Iterator iter = fluids.iterator();
        int maxFill = resource.amount;

        if (!doFill)
        {
            return resource.amount;
        }

        while (iter.hasNext() && resource.amount > 0)
        {
            FluidStack fluid = (FluidStack) iter.next();

            if (fluid.isFluidEqual(resource))
            {
                int amount = Math.min(resource.amount, fluid.amount);

                resource.amount -= amount;
                fluid.amount += amount;
            }
        }

        if (resource.amount > 0)
        {
            fluids.add(resource);
        }

        return maxFill;
    }

    /**
     * Determines if there is a valid recipe to decompose a given fluid in our internal fluid array storage.
     */
    public boolean isFluidValidForDecomposer(Fluid fluid)
    {
        for (DecomposerRecipe recipe : DecomposerRecipe.recipes)
        {
            if (recipe instanceof DecomposerFluidRecipe)
            {
                DecomposerFluidRecipe fluidRecipe = (DecomposerFluidRecipe) recipe;

                if (fluidRecipe.inputFluid.equals(fluid))
                {
                    return true;
                }

            }
        }

        return false;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        FluidTankInfo[] result = new FluidTankInfo[fluids.size() + 1];

        for (int i = 0; i < fluids.size(); i++)
        {
            result[i] = new FluidTankInfo(fluids.get(i), 100000);
        }

        result[result.length - 1] = new FluidTankInfo(null, 10000);
        return result;
    }

    /**
     * Enumeration of states that the decomposer can be in. Allows for easier understanding of code and interaction with user.
     */
    public enum State
    {
        kProcessIdle, kProcessActive, kProcessFinished, kProcessJammed
    }

    public DecomposerTileEntity()
    {
        // Sets the total amount of energy that can be stored and the amount we can receive in a single network update.
        super(MAX_ENERGY_STORED, MAX_ENERGY_RECIEVED);

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

        // Determine if we can decompose the object in the input slot while in a powered and idle state.
        if (this.inputInventory.getStackInSlot(0) == null && state == State.kProcessIdle)
        {
            for (int i = 0; i < fluids.size(); i++)
            {
                FluidStack input = fluids.get(i);
                for (DecomposerRecipe recipeToTest : DecomposerRecipe.recipes)
                {
                    // If there is fluid being pumped into the decomposer we will decompose that to if it matches something in our recipe list.
                    if (recipeToTest instanceof DecomposerFluidRecipe && input.isFluidEqual(((DecomposerFluidRecipe) recipeToTest).inputFluid))
                    {
                        // Recipes for fluid conversion take a certain amount of millibuckets (1000mB == Volume of bucket).
                        if (fluids.get(i).amount > ((DecomposerFluidRecipe) recipeToTest).inputFluid.amount)
                        {
                            // The decomposed itemStack that makes up what was once a fluid if there is enough of it to be converted into decomposed item version.
                            this.setInventorySlotContents(this.kInputSlot, new ItemStack(((DecomposerFluidRecipe) recipeToTest).inputFluid.getFluid().getBlockID(), 1, 0));
                            fluids.get(i).amount -= ((DecomposerFluidRecipe) recipeToTest).inputFluid.amount;
                        }
                    }
                }
            }
        }

        // Sends a packet to clients around the machine.
        PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, Settings.UpdateRadius, worldObj.provider.dimensionId, new DecomposerPacketUpdate(this).makePacket());

        // Determine if we should change our state to active.
        if (state == state.kProcessIdle && this.isPowered() && canDecomposeInput())
        {
            state = State.kProcessActive;
        } else if (state == State.kProcessJammed && canUnjam() && this.isPowered())
        {
            // Reactivates the machine once output slots have been cleared.
            // Note: It is possible for some items and blocks to decompose into more molecules than there are output slots!
            state = State.kProcessActive;
        }

        // If the machines state is currently not set to active then stop operation.
        if (state != State.kProcessActive)
        {
            return;
        }

        // Determines the current state of the machine.
        state = determineOperationalState();
        if (state == State.kProcessActive && this.isPowered())
        {
            // Consume energy to create the needed base elements.
            this.consumeEnergy(1);
        } else if ((state == State.kProcessIdle || state == State.kProcessFinished) && canDecomposeInput() && this.isPowered())
        {
            // Determines if machine has nothing to process or finished processing and has ability to decompose items in the input slot.
            activeStack = null;
            decomposeActiveStack();
            state = State.kProcessActive;
            this.onInventoryChanged();
        } else if (state == State.kProcessFinished)
        {
            // Prepares the machine for another pass if we have recently finished.
            activeStack = null;
            state = State.kProcessIdle;
        }

        // Notify minecraft that the inventory items in this machine have changed.
        this.onInventoryChanged();
    }

    private ItemStack getActiveStack()
    {
        if (activeStack == null)
        {
            if (getStackInSlot(kInput[0]) != null)
            {
                activeStack = decrStackSize(kInput[0], 1);
            } else
            {
                return null;
            }
        }
        return activeStack;
    }

    /**
     * Determines if it is possible to decompose the current itemStack in the input slot.
     */
    private boolean canDecomposeInput()
    {
        ItemStack inputStack = getStackInSlot(kInput[0]);
        if (inputStack == null)
        {
            return false;
        }

        DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(inputStack);
        return (recipe != null);
    }

    /**
     * One of the most important functions in MineChem is the ability to decompose items into base molecules.
     */
    private void decomposeActiveStack()
    {
        try
        {
            ItemStack inputStack = getActiveStack();
            DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(inputStack);

            if (recipe != null)
            {
                ArrayList<PotionChemical> output = recipe.getOutput();
                if (output != null)
                {
                    ArrayList<ItemStack> stacks = MinechemHelper.convertChemicalsIntoItemStacks(output);
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
            state = State.kProcessFinished;
        }
    }

    /**
     * Determines if there are any open slots in the output slots by looping through them.
     */
    private boolean canUnjam()
    {
        for (int slot : kOutput)
        {
            if (getStackInSlot(slot) == null)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns JAMMED state if there are items in the output buffer but output slots are full. Returns ACTIVE state if there is room in output slots for output buffer items. Returns FINISHED if output buffer is empty and output slots have all items [that was decomposed].
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

                return State.kProcessActive;
            } else
            {
                return State.kProcessJammed;
            }
        }
        return State.kProcessFinished;
    }

    /**
     * Moves items from the output buffer and into the actual output slots of the machine.
     */
    private boolean addStackToOutputSlots(ItemStack itemstack)
    {
        itemstack.getItem().onCreated(itemstack, this.worldObj, null);
        for (int outputSlot : kOutput)
        {
            ItemStack stackInSlot = getStackInSlot(outputSlot);
            if (stackInSlot == null)
            {
                setInventorySlotContents(outputSlot, itemstack);
                return true;
            } else if (Compare.stacksAreSameKind(stackInSlot, itemstack) && (stackInSlot.stackSize + itemstack.stackSize) <= getInventoryStackLimit())
            {
                stackInSlot.stackSize += itemstack.stackSize;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getSizeInventory()
    {
        return 10;
    }

    @Override
    public String getInvName()
    {
        return "container.decomposer";
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);

        NBTTagList buffer = MinechemHelper.writeItemStackListToTagList(outputBuffer);

        nbt.setTag("inventory", inventoryTagList);

        nbt.setTag("buffer", buffer);

        if (activeStack != null)
        {
            NBTTagCompound activeStackCompound = new NBTTagCompound();
            activeStack.writeToNBT(activeStackCompound);
            nbt.setTag("activeStack", activeStackCompound);
        }

        nbt.setByte("state", (byte) state.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        NBTTagList inventoryTagList = nbt.getTagList("inventory");

        NBTTagList buffer = nbt.getTagList("buffer");

        outputBuffer = MinechemHelper.readTagListToItemStackList(buffer);
        inventory = MinechemHelper.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);

        if (nbt.getTag("activeStack") != null)
        {
            NBTTagCompound activeStackCompound = (NBTTagCompound) nbt.getTag("activeStack");
            activeStack = ItemStack.loadItemStackFromNBT(activeStackCompound);
        }
        state = State.values()[nbt.getByte("state")];
    }

    /**
     * Returns the current working state of the machine as an object.
     */
    public State getState()
    {
        return state;
    }

    /**
     * Sets the current state of the machine using an integer to reference an enumeration.
     */
    public void setState(int state)
    {
        this.state = State.values()[state];
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        if (i == kInput[0])
        {
            return true;
        }

        return false;
    }

    public int[] getSizeInventorySide(int side)
    {
        switch (side)
        {
            case 1:
                return kInput;
            default:
                return kOutput;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {

        if (var1 == 1)
        {
            return DecomposerTileEntity.kInput;
        }

        return DecomposerTileEntity.kOutput;

    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return itemstack != null && !Compare.isStackAnElement(itemstack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return true;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        if (slot == DecomposerTileEntity.kOutput[0])
        {
            ItemStack oldStack = this.inventory[DecomposerTileEntity.kOutput[0]];
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

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }
}
