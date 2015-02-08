package minechem.compatibility.lua.events.triggered.world;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventBus;
import minechem.compatibility.lua.events.triggered.TriggerEvent;

public abstract class WorldTriggeredEvent<T extends Event> extends TriggerEvent<T>
{
    public WorldTriggeredEvent(String name, EventBus bus)
    {
        super(name, bus);
    }
}
