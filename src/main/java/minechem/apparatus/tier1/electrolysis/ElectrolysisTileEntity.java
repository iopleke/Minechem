package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicFluidInventoryTileEntity;
import minechem.chemical.ChemicalBase;
import minechem.item.chemical.ChemicalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ElectrolysisTileEntity extends BasicFluidInventoryTileEntity
{
    private byte LEFTSIDE = 0;
    private byte RIGHTSIDE = 1;
    private boolean leftTube;
    private boolean rightTube;

    public ElectrolysisTileEntity()
    {
        super(Compendium.Naming.electrolysis, 2, 3);
    }

    public byte addItem(ItemStack chemicalItemStack)
    {
        if (chemicalItemStack.getItem() != null && chemicalItemStack.getItem() instanceof ChemicalItem)
        {
            if (this.getStackInSlot(0) == null)
            {
                this.setInventorySlotContents(0, chemicalItemStack);
                return 0;
            } else if (this.getStackInSlot(1) == null)
            {
                this.setInventorySlotContents(1, chemicalItemStack);
                return 1;
            }
        }
        return -1;
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

    /**
     * Fill a specific side of the TileEntity with a ChemicalBase
     *
     * @param chemicalBase
     * @param side         0 is left, 1 is right
     */
    public void fillWithChemicalBase(ChemicalBase chemicalBase, byte side)
    {

        if (side == LEFTSIDE)
        {
            leftTube = true;
        }
        if (side == RIGHTSIDE)
        {
            rightTube = true;
        }
    }

    /**
     * Remove a ChemicalItem from a side
     *
     * @param side 0 is left, 1 is right
     * @return
     */
    public ChemicalItem removeItem(int side)
    {
        if (side == LEFTSIDE)
        {

            if (this.getStackInSlot(1) != null)
            {
                ChemicalItem chemical = (ChemicalItem) getStackInSlot(1).getItem();
                this.decrStackSize(1, 1);
                leftTube = false;
                return chemical;
            }
        }
        if (side == RIGHTSIDE)
        {
            if (this.getStackInSlot(0) != null)
            {
                ChemicalItem chemical = (ChemicalItem) getStackInSlot(0).getItem();
                this.decrStackSize(0, 1);
                rightTube = false;
                return chemical;
            }
        }
        return null;
    }

    public ChemicalItem getLeftTube()
    {
        ItemStack itemStack = decrStackSize(LEFTSIDE, 1);
        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof ChemicalItem)
            {
                return (ChemicalItem) itemStack.getItem();
            }
        }
        return null;
    }

    public ChemicalItem getRightTube()
    {
        ItemStack itemStack = decrStackSize(RIGHTSIDE, 1);
        if (itemStack != null)
        {
            if (itemStack.getItem() instanceof ChemicalItem)
            {
                return (ChemicalItem) itemStack.getItem();
            }
        }
        return null;
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
    }
}
