package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicFluidInventoryTileEntity;
import minechem.chemical.ChemicalBase;
import minechem.item.chemical.ChemicalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ElectrolysisTileEntity extends BasicFluidInventoryTileEntity
{
    private byte tubeCount;

    public ElectrolysisTileEntity()
    {
        super(Compendium.Naming.electrolysis, 2, 3);
    }

    public boolean addItem(ItemStack chemicalItemStack)
    {
        if (chemicalItemStack.getItem() != null && chemicalItemStack.getItem() instanceof ChemicalItem)
        {
            if (this.getStackInSlot(0) == null)
            {
                this.setInventorySlotContents(0, chemicalItemStack);
                return true;
            } else if (this.getStackInSlot(1) == null)
            {
                this.setInventorySlotContents(1, chemicalItemStack);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getInventoryName()
    {
        return Compendium.Naming.electrolysis;
    }

    @Override
    public void updateEntity()
    {
    }

    public boolean fillWithChemicalBase(ChemicalBase chemicalBase)
    {

        if (tubeCount < 2)
        {
            tubeCount++;
        }
        return false;
    }

    public ChemicalItem removeItem()
    {
        if (tubeCount > 0)
        {
            tubeCount--;

            if (this.getStackInSlot(1) != null)
            {
                ChemicalItem chemical = (ChemicalItem) getStackInSlot(1).getItem();
                this.decrStackSize(1, 1);
                return chemical;
            } else if (this.getStackInSlot(0) != null)
            {
                ChemicalItem chemical = (ChemicalItem) getStackInSlot(0).getItem();
                this.decrStackSize(0, 1);
                return chemical;
            }
        }
        return null;
    }

    public byte getTubeCount()
    {
        return tubeCount;
    }

    /**
     * Save data to NBT
     *
     * @param nbttagcompound
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setByte("tubeCount", tubeCount);
    }

    /**
     * Read saved values from NBT
     *
     * @param nbttagcompound
     */
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        tubeCount = nbttagcompound.getByte("tubeCount");
    }
}
