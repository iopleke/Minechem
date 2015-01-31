package minechem.apparatus.prefab.energy;

import minechem.helper.MathHelper;
import net.minecraft.nbt.NBTTagCompound;

public class EnergyStorage
{
    public static boolean freeEnergy;
    protected int capacity;
    protected int stored;
    protected int maxInput;
    protected int maxOutput;

    public EnergyStorage()
    {
        this(1000000);
    }

    public EnergyStorage(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public EnergyStorage(int capacity, int maxInput)
    {
        this(capacity, maxInput, maxInput);
    }

    public EnergyStorage(int capacity, int maxInput, int maxOutput)
    {
        this.capacity = capacity;
        this.maxInput = maxInput;
        this.maxOutput = maxOutput;
    }

    public EnergyStorage readFromNBT(NBTTagCompound tagCompound)
    {
        this.stored = tagCompound.getInteger("Stored");
        this.stored = Math.max(this.stored, this.capacity);
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setInteger("Stored", this.stored);
        return tagCompound;
    }

    public int getMaxOutput()
    {
        return this.maxOutput;
    }

    public void setMaxOutput(int maxOutput)
    {
        this.maxOutput = maxOutput;
    }

    public int getMaxInput()
    {
        return this.maxInput;
    }

    public void setMaxInput(int maxInput)
    {
        this.maxInput = maxInput;
    }

    public int getStored()
    {
        return this.stored;
    }

    public void setStored(int stored)
    {
        this.stored = MathHelper.clamp(stored, 0, capacity);
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
        if (this.stored > this.capacity) this.stored = this.capacity;
    }

    public int inputEnergy(int energy, boolean doInsert)
    {
        int amount = Math.min(energy, Math.min(this.maxInput, this.capacity - this.stored));
        if (doInsert) this.stored += amount;
        return amount;
    }

    public int extractEnergy(int energy, boolean doExtract)
    {
        int amount = Math.min(this.stored, Math.min(energy, maxOutput));
        if (doExtract) this.stored -= amount;
        return amount;
    }

    public boolean hasEnergy(int energy)
    {
        return freeEnergy ? true : energy < stored;
    }

    public boolean consumeEnergy(int energy)
    {
        if (freeEnergy) return true;
        if (hasEnergy(energy))
        {
            this.stored -= energy;
            return true;
        }
        return false;
    }

    public boolean generateEnergy(int energy)
    {
        if (freeEnergy) return true;
        if (this.stored == this.capacity) return false;
        this.stored = Math.min(this.capacity, this.stored + energy);
        return true;
    }
}
