package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicFluidInventoryTileEntity;
import minechem.chemical.ChemicalBase;
import minechem.item.chemical.ChemicalItem;
import net.minecraft.nbt.NBTTagCompound;

public class ElectrolysisTileEntity extends BasicFluidInventoryTileEntity
{
    private byte tubeCount;

    public ElectrolysisTileEntity()
    {
        super(Compendium.Naming.electrolysis, 2, 3);
        tubeCount = 0;
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
        tubeCount++;
        if (tubeCount > 2)
        {
            tubeCount = 0;
        }
        return false;
    }

    public ChemicalItem removeItem()
    {
        if (tubeCount > 0)
        {
            tubeCount--;
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
