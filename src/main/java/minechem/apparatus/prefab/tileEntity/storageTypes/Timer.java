package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.Compendium;
import net.minecraft.nbt.NBTTagCompound;

public class Timer
{
    private int reset;
    private int counter;

    public Timer(int reset)
    {
        this.reset = reset;
    }

    private Timer(int reset, int count)
    {
        this.reset = reset;
        this.counter = count;
    }

    public boolean update()
    {
        if (counter++ == reset)
        {
            counter = 0;
            return true;
        }
        return false;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setTag(Compendium.NBTTags.timer, writeToNBT());
        return tagCompound;
    }

    public NBTTagCompound writeToNBT()
    {
        NBTTagCompound timer = new NBTTagCompound();
        timer.setInteger(Compendium.NBTTags.count, this.counter);
        timer.setInteger(Compendium.NBTTags.reset, this.reset);
        return timer;
    }

    public static Timer nbtToTimer(NBTTagCompound compound)
    {
        NBTTagCompound timer = compound;
        if (compound.hasKey(Compendium.NBTTags.timer, Compendium.NBTTags.tagCompound))
        {
            timer = compound.getCompoundTag(Compendium.NBTTags.timer);
        }
        if (timer.hasKey(Compendium.NBTTags.count) && timer.hasKey(Compendium.NBTTags.reset))
        {
            return new Timer(timer.getInteger(Compendium.NBTTags.reset), timer.getInteger(Compendium.NBTTags.count));
        }
        return null;
    }

}
