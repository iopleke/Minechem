package minechem.compatibility.lua.events.checked;

import minechem.apparatus.prefab.peripheral.TilePeripheralBase;
import minechem.compatibility.lua.events.LuaEvent;

public abstract class CheckEvent extends LuaEvent
{
    public CheckEvent(String name)
    {
        super(name);
    }

    public abstract boolean triggerEvent(TilePeripheralBase te);

    public boolean checkEvent(TilePeripheralBase te)
    {
        return true;
    }
}
