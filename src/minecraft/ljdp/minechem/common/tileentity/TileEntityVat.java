package ljdp.minechem.common.tileentity;

import java.util.ArrayList;
import java.util.List;

import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.util.Util;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.inventory.BoundedInventory;
import ljdp.minechem.common.inventory.Transactor;
import ljdp.minechem.common.utils.MinechemHelper;
import ljdp.minechem.computercraft.IMinechemMachinePeripheral;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.inventory.ISpecialInventory;

public class TileEntityVat extends MinechemTileEntity implements ISpecialInventory, ISidedInventory, IMinechemMachinePeripheral {

    public static final int[] kInput = { 0 };
    public static final int[] kTestTubeIn = { 1 };
    public static final int[] kTestTubeOut = { 2 };
    public static final int[] kOutput = { 3 };

    public static final int MAX_CHEMICAL_AMOUNT = 64 * 256;
    public Chemical chemical;
    public int amountOfChemical = 0;
    public int amountOfTestTube = 0;
    private int maxChemicalAmount = MAX_CHEMICAL_AMOUNT;
    public boolean isSpoiled = false;
	public static final int kStartInput 		= 0;
	public static final int kStartTestTubeIn	= 1;
	public static final int kStartTestTubeOut	= 2;
	public static final int kStartOutput 		= 3;
    private final BoundedInventory inputInventory = new BoundedInventory(this, kInput);
    private final BoundedInventory tubeInInventory = new BoundedInventory(this, kTestTubeIn);
    private final BoundedInventory tubeOutInventory = new BoundedInventory(this, kTestTubeOut);
    private final BoundedInventory outputInventory = new BoundedInventory(this, kOutput);
    private Transactor outputTransactor = new Transactor(outputInventory);
    private Transactor inputTransactor = new Transactor(inputInventory);
    private Transactor tubeInTransactor = new Transactor(tubeInInventory);
    private Transactor tubeOutTransactor = new Transactor(tubeOutInventory);
    private boolean workNeeded = false;

    public TileEntityVat() {
        this.inventory = new ItemStack[getSizeInventory()];
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        // if(workNeeded) {
        boolean did1 = moveInputToStorage();
        boolean did2 = moveStorageToTestTubeIn();
        boolean did3 = moveStorageToOutput();
        if (!did1 && !did2 && !did3)
            workNeeded = false;
        // }
    }

    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        workNeeded = true;
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public String getInvName() {
        return "container.chemicalVat";
    }

    @Override
    public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
        return inputTransactor.add(stack, doAdd);
    }

    @Override
    public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
        if (doRemove) {
            List<ItemStack> outputs = new ArrayList<ItemStack>();
            for (int i = 0; i < maxItemCount; i++) {
                ItemStack output = takeOutput();
                if (output != null)
                    outputs.add(output);
                else
                    break;
            }
            return outputs.toArray(new ItemStack[outputs.size()]);
        } else {
            return outputTransactor.remove(maxItemCount, false);
        }

    }

    @Override
    public ItemStack takeEmptyTestTube() {
        if (amountOfTestTube > 0) {
            amountOfTestTube--;
            return new ItemStack(MinechemItems.testTube, 1);
        }
        return null;
    }

    @Override
    public int putEmptyTestTube(ItemStack testTube) {
        if (amountOfTestTube < maxChemicalAmount) {
            int amountToAdd = Math.min(maxChemicalAmount - amountOfTestTube, testTube.stackSize);
            amountOfTestTube++;
            testTube.stackSize -= amountToAdd;
        }
        return testTube.stackSize;
    }

    private boolean moveInputToStorage() {
        ItemStack inputStack = decrStackSize(kInput[0], 1);
        if (inputStack == null || !Util.isStackAChemical(inputStack))
            return false;
        if (amountOfChemical < maxChemicalAmount && amountOfTestTube < maxChemicalAmount) {
            Chemical inputChemical = MinechemHelper.itemStackToChemical(inputStack);
            if (chemical == null)
                chemical = inputChemical;
            else if (!chemical.sameAs(inputChemical))
                isSpoiled = true;
            amountOfChemical++;
            amountOfTestTube++;
            return true;
        }
        return false;
    }

    private boolean moveStorageToTestTubeIn() {
        if (amountOfTestTube == 0)
            return false;
        ItemStack testTube = getStackInSlot(kTestTubeIn[0]);
        if (testTube == null) {
            setInventorySlotContents(kTestTubeIn[0], new ItemStack(MinechemItems.testTube));
            amountOfTestTube--;
            return true;
        } else if (testTube.stackSize < 64) {
            testTube.stackSize++;
            amountOfTestTube--;
            return true;
        }
        return false;
    }

    private boolean moveStorageToOutput() {
        ItemStack testTube = tubeOutTransactor.removeItem(true);
        if (testTube != null && chemical != null && amountOfChemical > 0 && amountOfTestTube > 0 && !isSpoiled) {
            ItemStack outputStack = MinechemHelper.chemicalToItemStack(chemical, 1);
            int added = putOutput(outputStack);
            if (added == outputStack.stackSize) {
                amountOfChemical--;
                return true;
            } else {
                tubeOutTransactor.add(testTube, true);
            }
        }
        return false;
    }

    @Override
    public ItemStack takeOutput() {
        ItemStack outputStack = getStackInSlot(kOutput[0]);
        if (outputStack != null || (outputStack == null && moveStorageToOutput())) { return decrStackSize(kOutput[0], 1); }
        return null;
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
    public String getMachineState() {
        return null;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("amountOfChemical", amountOfChemical);
        nbt.setInteger("amountOfTestTube", amountOfTestTube);
        NBTTagList inventoryTagList = MinechemHelper.writeItemStackArrayToTagList(inventory);
        nbt.setTag("inventory", inventoryTagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.amountOfChemical = nbt.getInteger("amountOfChemical");
        this.amountOfTestTube = nbt.getInteger("amountOfTestTube");
        NBTTagList inventoryTagList = nbt.getTagList("inventory");
        this.inventory = MinechemHelper.readTagListToItemStackArray(inventoryTagList, new ItemStack[getSizeInventory()]);
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public int[] getSizeInventorySide(int side) {
        switch (side) {
        case 1:
            return kInput;
        default:
            return kOutput;
        }
    }

    @Override
    public boolean canConnect(ForgeDirection direction) {
        return false;
    }

    @Override
    public boolean func_102007_a(int i, ItemStack itemstack, int j) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean func_102008_b(int i, ItemStack itemstack, int j) {
        // TODO Auto-generated method stub
        return false;
    }

	@Override
	void sendUpdatePacket() {
		// TODO Auto-generated method stub
		
	}

}
