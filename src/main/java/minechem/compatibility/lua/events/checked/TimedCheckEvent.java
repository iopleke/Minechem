package minechem.compatibility.lua.events.checked;

import minechem.apparatus.prefab.peripheral.TilePeripheralBase;
import minechem.apparatus.prefab.tileEntity.storageTypes.Timer;

public abstract class TimedCheckEvent extends CheckEvent
{
    private Timer timer;

    public TimedCheckEvent(String name, Timer timer)
    {
        super(name);
        this.timer = timer;
    }

    @Override
    public boolean checkEvent(TilePeripheralBase te)
    {
        return timer.update();
    }
}
