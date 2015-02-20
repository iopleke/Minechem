package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.tileEntity.BasicFluidInventoryTileEntity;
import minechem.item.chemical.ChemicalItem;
import net.minecraft.nbt.NBTTagCompound;

public class ElectrolysisTileEntity extends BasicFluidInventoryTileEntity
{
    public byte tubeCount;

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

    public boolean fillWithItem(ChemicalItem item)
    {
        tubeCount++;
        if (tubeCount > 2)
        {
            tubeCount = 0;
        }
        return false;
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
        NBTTagCompound tubeCountNBT = new NBTTagCompound();
        // This doesn't currently seem to be working...
        tubeCountNBT.setByte("tubeCount", tubeCount);
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
        // This doesn't currently seem to be working...
        tubeCount = nbttagcompound.getByte("tubeCount");
    }
}
