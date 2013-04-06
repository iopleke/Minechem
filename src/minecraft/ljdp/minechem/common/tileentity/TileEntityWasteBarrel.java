package ljdp.minechem.common.tileentity;

import ljdp.minechem.common.MinechemItems;
import buildcraft.api.inventory.ISpecialInventory;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;

public class TileEntityWasteBarrel extends TileEntity implements ISpecialInventory, ITankContainer {

    public int currentCapacity = 0;
    public int maxCapacity = 1000;
    private DataWatcher dataWatcher = new DataWatcher();
    private int capacityWatcherID = 1;

    public TileEntityWasteBarrel() {
        dataWatcher.addObject(capacityWatcherID, new Integer(currentCapacity));
    }

    public float getCurrentCapacity() {
        return dataWatcher.getWatchableObjectInt(capacityWatcherID);
    }

    private void addToCapacity(int amount) {
        currentCapacity += amount;
        syncCapacity();
        if (currentCapacity > maxCapacity) {
            overflow();
        }
        System.out.println("Waste Capacity: " + currentCapacity);
    }

    private void syncCapacity() {
        dataWatcher.updateObject(capacityWatcherID, currentCapacity);
    }

    private void overflow() {

    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int var1, int var2) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {}

    @Override
    public String getInvName() {
        return "container.ChemicalWasteBarrel";
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openChest() {}

    @Override
    public void closeChest() {}

    @Override
    public int addItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
        if (from == ForgeDirection.UP && (stack.itemID == MinechemItems.element.itemID || stack.itemID == MinechemItems.molecule.itemID)) {
            if (doAdd)
                addToCapacity(stack.stackSize);
            return stack.stackSize;
        }
        return 0;
    }

    @Override
    public ItemStack[] extractItem(boolean doRemove, ForgeDirection from, int maxItemCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ILiquidTank[] getTanks(ForgeDirection direction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

}
