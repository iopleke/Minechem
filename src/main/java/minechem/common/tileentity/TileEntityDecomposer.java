package minechem.common.tileentity;

import java.util.ArrayList;
import java.util.Iterator;

import minechem.api.core.Chemical;
import minechem.api.recipe.DecomposerRecipe;
import minechem.api.util.Util;
import minechem.client.ModelDecomposer;
import minechem.common.inventory.BoundedInventory;
import minechem.common.inventory.Transactor;
import minechem.common.network.PacketDecomposerUpdate;
import minechem.common.recipe.DecomposerFluidRecipe;
import minechem.common.recipe.DecomposerRecipeHandler;
import minechem.common.utils.MinechemHelper;
import minechem.computercraft.IMinechemMachinePeripheral;
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

public class TileEntityDecomposer extends MinechemTileEntity implements ISidedInventory, IMinechemMachinePeripheral, IFluidHandler
{
    public static final int[] kInput = { 0 };
    public static final int[] kOutput = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    private ArrayList<ItemStack> outputBuffer;
    public final int kInputSlot = 0;
    public final int kOutputSlotStart = 1;
    public final int kOutputSlotEnd = 9;
    public final int kEmptyBottleSlotsSize = 4;
    public final int kOutputSlotsSize = 9;
    public State state = State.kProcessIdle;
    private ItemStack activeStack;
    public ModelDecomposer model;

    private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);
    private final BoundedInventory inputInventory = new BoundedInventory(this, kInput);
    private final Transactor outputTransactor = new Transactor(outputInventory);
    private final Transactor inputTransactor = new Transactor(inputInventory);

    private static final long MAX_ENERGY_RECIEVED = 20;
    private static final long MAX_ENERGY_STORED = 10000;

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

    public enum State
    {
        kProcessIdle, kProcessActive, kProcessFinished, kProcessJammed
    }

    public TileEntityDecomposer()
    {
        super(MAX_ENERGY_STORED, MAX_ENERGY_RECIEVED);

        inventory = new ItemStack[getSizeInventory()];
        outputBuffer = new ArrayList<ItemStack>();

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            model = new ModelDecomposer();
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!worldObj.isRemote && this.inputInventory.getStackInSlot(0) == null && state == State.kProcessIdle)
        {
            for (int i = 0; i < fluids.size(); i++)
            {
                FluidStack input = fluids.get(i);
                for (DecomposerRecipe recipeToTest : DecomposerRecipe.recipes)
                {
                    if (recipeToTest instanceof DecomposerFluidRecipe && input.isFluidEqual(((DecomposerFluidRecipe) recipeToTest).inputFluid))
                    {
                        if (fluids.get(i).amount > ((DecomposerFluidRecipe) recipeToTest).inputFluid.amount)
                        {
                            this.setInventorySlotContents(this.kInputSlot, new ItemStack(((DecomposerFluidRecipe) recipeToTest).inputFluid.getFluid().getBlockID(), 1, 0));
                            fluids.get(i).amount -= ((DecomposerFluidRecipe) recipeToTest).inputFluid.amount;
                        }
                    }

                }
            }
        }

        this.doWork();
        if (!worldObj.isRemote)
        {
            sendUpdatePacket();
        }

        if ((state == State.kProcessIdle || state == State.kProcessFinished) && canDecomposeInput())
        {
            activeStack = null;
            decomposeActiveStack();
            state = State.kProcessActive;
            this.onInventoryChanged();
        }
        else if (state == State.kProcessFinished)
        {
            activeStack = null;
            state = State.kProcessIdle;
        }
        else if (state == State.kProcessJammed && canUnjam())
        {
            state = State.kProcessActive;
        }
    }

    @Override
    public void sendUpdatePacket()
    {
        if (worldObj.isRemote)
        {
            return;
        }

        PacketDecomposerUpdate packetDecomposerUpdate = new PacketDecomposerUpdate(this);
        int dimensionID = worldObj.provider.dimensionId;
        PacketDispatcher.sendPacketToAllInDimension(packetDecomposerUpdate.makePacket(), dimensionID);
    }

    private ItemStack getActiveStack()
    {
        if (activeStack == null)
        {
            if (getStackInSlot(kInput[0]) != null)
            {
                activeStack = decrStackSize(kInput[0], 1);
            }
            else
            {
                return null;
            }
        }
        return activeStack;
    }

    public void doWork()
    {
        if (state != State.kProcessActive)
        {
            return;
        }

        if (!worldObj.isRemote)
        {
            while (this.isPowered())
            {
                // Consume energy to create the needed base elements.
                this.consume();
                
                state = moveBufferItemToOutputSlot();
                if (state != State.kProcessActive)
                {
                    break;
                }
            }

            this.onInventoryChanged();
            sendUpdatePacket();
        }
    }

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

    private void decomposeActiveStack()
    {
        try
        {
            ItemStack inputStack = getActiveStack();
            DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(inputStack);

            if (recipe != null)
            {
                ArrayList<Chemical> output = recipe.getOutput();
                if (output != null)
                {
                    ArrayList<ItemStack> stacks = MinechemHelper.convertChemicalsIntoItemStacks(output);
                    placeStacksInBuffer(stacks);
                }
            }
        }
        catch (Exception e)
        {
            // softly falls the rain
            // but the forest does not see
            // silently this fails
        }

    }

    private void placeStacksInBuffer(ArrayList<ItemStack> outputStacks)
    {
        if (outputStacks != null)
        {
            outputBuffer = outputStacks;
        }
        else
        {
            state = State.kProcessFinished;
        }
    }

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

    private State moveBufferItemToOutputSlot()
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
            }
            else
            {
                return State.kProcessJammed;
            }
        }
        return State.kProcessFinished;
    }

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
            }
            else if (Util.stacksAreSameKind(stackInSlot, itemstack) && (stackInSlot.stackSize + itemstack.stackSize) <= getInventoryStackLimit())
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
        return 14;
    }

    @Override
    public String getInvName()
    {
        return "container.decomposer";
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
        NBTTagList buffer = MinechemHelper.writeItemStackListToTagList(outputBuffer);
        nbtTagCompound.setTag("inventory", inventoryTagList);
        nbtTagCompound.setTag("buffer", buffer);
        if (activeStack != null)
        {
            NBTTagCompound activeStackCompound = new NBTTagCompound();
            activeStack.writeToNBT(activeStackCompound);
            nbtTagCompound.setTag("activeStack", activeStackCompound);
        }
        nbtTagCompound.setByte("state", (byte) state.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        NBTTagList inventoryTagList = nbtTagCompound.getTagList("inventory");
        NBTTagList buffer = nbtTagCompound.getTagList("buffer");
        outputBuffer = MinechemHelper.readTagListToItemStackList(buffer);
        inventory = MinechemHelper.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);

        if (nbtTagCompound.getTag("activeStack") != null)
        {
            NBTTagCompound activeStackCompound = (NBTTagCompound) nbtTagCompound.getTag("activeStack");
            activeStack = ItemStack.loadItemStackFromNBT(activeStackCompound);
        }
        state = State.values()[nbtTagCompound.getByte("state")];
    }

    public State getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = State.values()[state];
    }

    @Override
    public ItemStack takeOutput()
    {
        return outputTransactor.removeItem(true);
    }

    @Override
    public int putInput(ItemStack input)
    {
        return inputTransactor.add(input, true);
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
        return null;
    }

    @Override
    public int putJournal(ItemStack journal)
    {
        return 0;
    }

    @Override
    public int putOutput(ItemStack output)
    {
        return outputTransactor.add(output, true);
    }

    @Override
    public ItemStack takeInput()
    {
        return outputTransactor.removeItem(true);
    }

    @Override
    public String getMachineState()
    {
        if (this.state == State.kProcessJammed)
        {
            return "outputjammed";
        }
        else if (this.state == State.kProcessActive)
        {
            return "decomposing";
        }
        else if (!this.isPowered())
        {
            return "powered";
        }
        else
        {
            return "unpowered";
        }
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
            return true;
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
            return TileEntityDecomposer.kInput;
        }

        return TileEntityDecomposer.kOutput;

    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j)
    {
        return true;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j)
    {
        return true;
    }

    // Hacky code
    // To fix a FZ glitch
    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        if (slot == TileEntityDecomposer.kOutput[0])
        {
            ItemStack oldStack = this.inventory[TileEntityDecomposer.kOutput[0]];
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
            itemstack.stackSize = this.getInventoryStackLimit();
        this.inventory[slot] = itemstack;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }
}