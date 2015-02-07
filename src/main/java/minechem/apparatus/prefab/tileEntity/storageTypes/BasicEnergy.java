package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.helper.MathHelper;
import net.minecraft.nbt.NBTTagCompound;

public class BasicEnergy
{
    public static boolean freeEnergy;
    protected int capacity;
    protected int maxInput;
    protected int maxOutput;
    protected int stored;

    public BasicEnergy()
    {
        this(1000000);
    }

    public BasicEnergy(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public BasicEnergy(int capacity, int maxInput)
    {
        this(capacity, maxInput, maxInput);
    }

    public BasicEnergy(int capacity, int maxInput, int maxOutput)
    {
        this.capacity = capacity;
        this.maxInput = maxInput;
        this.maxOutput = maxOutput;
    }

    public boolean consumeEnergy(int energy)
    {
        if (freeEnergy)
        {
            return true;
        }
        if (hasEnergy(energy))
        {
            this.stored -= energy;
            return true;
        }
        return false;
    }

    public int extractEnergy(int energy, boolean doExtract)
    {
        int amount = Math.min(this.stored, Math.min(energy, maxOutput));
        if (doExtract)
        {
            this.stored -= amount;
        }
        return amount;
    }

    public boolean generateEnergy(int energy)
    {
        if (freeEnergy)
        {
            return true;
        }
        if (this.stored == this.capacity)
        {
            return false;
        }
        this.stored = Math.min(this.capacity, this.stored + energy);
        return true;
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    public int getMaxInput()
    {
        return this.maxInput;
    }

    public int getMaxOutput()
    {
        return this.maxOutput;
    }

    public int getStored()
    {
        return this.stored;
    }

    public boolean hasEnergy(int energy)
    {
        return freeEnergy ? true : energy < stored;
    }

    public int inputEnergy(int energy, boolean doInsert)
    {
        int amount = Math.min(energy, Math.min(this.maxInput, this.capacity - this.stored));
        if (doInsert)
        {
            this.stored += amount;
        }
        return amount;
    }

    public BasicEnergy readFromNBT(NBTTagCompound tagCompound)
    {
        this.stored = tagCompound.getInteger("Stored");
        this.stored = Math.max(this.stored, this.capacity);
        return this;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
        if (this.stored > this.capacity)
        {
            this.stored = this.capacity;
        }
    }

    public void setMaxInput(int maxInput)
    {
        this.maxInput = maxInput;
    }

    public void setMaxOutput(int maxOutput)
    {
        this.maxOutput = maxOutput;
    }

    public void setStored(int stored)
    {
        this.stored = MathHelper.clamp(stored, 0, capacity);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setInteger("Stored", this.stored);
        return tagCompound;
    }
}
