package minechem.apparatus.prefab.tileEntity.storageTypes;

import minechem.reference.NBTTags;
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
        tagCompound.setTag(NBTTags.TIMER, writeToNBT());
        return tagCompound;
    }

    public NBTTagCompound writeToNBT()
    {
        NBTTagCompound timer = new NBTTagCompound();
        timer.setInteger(NBTTags.COUNT, this.counter);
        timer.setInteger(NBTTags.RESET, this.reset);
        return timer;
    }

    public static Timer nbtToTimer(NBTTagCompound compound)
    {
        NBTTagCompound timer = compound;
        if (compound.hasKey(NBTTags.TIMER, NBTTags.TAG_COMPOUND)) timer = compound.getCompoundTag(NBTTags.TIMER);
        if (timer.hasKey(NBTTags.COUNT) && timer.hasKey(NBTTags.RESET))
            return new Timer(timer.getInteger(NBTTags.RESET), timer.getInteger(NBTTags.COUNT));
        return null;
    }

}
