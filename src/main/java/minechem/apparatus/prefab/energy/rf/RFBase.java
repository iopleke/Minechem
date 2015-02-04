package minechem.apparatus.prefab.energy.rf;

import minechem.apparatus.prefab.energy.EnergyStorage;
import minechem.apparatus.prefab.tileEntity.BasicTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public abstract class RFBase extends BasicTileEntity
{
    protected EnergyStorage energy;

    public RFBase(int inventorySize)
    {
        super(inventorySize);
        energy = new EnergyStorage();
    }

    public RFBase(int inventorySize, int capacity)
    {
        super(inventorySize);
        energy = new EnergyStorage(capacity);
    }

    public RFBase(int inventorySize, int capacity, int maxTransfer)
    {
        super(inventorySize);
        energy = new EnergyStorage(capacity, maxTransfer);
    }

    public RFBase(int inventorySize, int capacity, int maxInput, int maxOutput)
    {
        super(inventorySize);
        energy = new EnergyStorage(capacity, maxInput, maxOutput);
    }

    public boolean consumeEnergy(int amount)
    {
        return this.energy.consumeEnergy(amount);
    }

    public boolean generateEnergy(int amount)
    {
        return this.energy.generateEnergy(amount);
    }

    public boolean hasEnergy(int amount)
    {
        return this.energy.hasEnergy(amount);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        energy.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        energy.writeToNBT(tagCompound);
    }
}
