package minechem.apparatus.prefab.energy.rf;

import minechem.apparatus.prefab.tileEntity.storageTypes.BasicEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class RFBase extends TileEntity
{
    protected BasicEnergy energy;

    public RFBase()
    {
        energy = new BasicEnergy();
    }

    public RFBase(int capacity)
    {
        energy = new BasicEnergy(capacity);
    }

    public RFBase(int capacity, int maxTransfer)
    {
        energy = new BasicEnergy(capacity, maxTransfer);
    }

    public RFBase(int capacity, int maxInput, int maxOutput)
    {
        energy = new BasicEnergy(capacity, maxInput, maxOutput);
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
