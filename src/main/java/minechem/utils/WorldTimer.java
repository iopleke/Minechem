package minechem.utils;

import net.minecraft.world.World;

public class WorldTimer
{

    private int delay;
    private long lastTime = 0;

    public WorldTimer(int i)
    {
        delay = i;
    }

    public boolean update(World world)
    {
        if (world == null)
        {
            return false;
        }
        long thisTime = world.getTotalWorldTime();
        if (thisTime < lastTime)
        {
            lastTime = thisTime;
            return false;
        } else if (lastTime + delay <= thisTime)
        {
            lastTime = thisTime;
            return true;
        }
        return false;
    }
}
