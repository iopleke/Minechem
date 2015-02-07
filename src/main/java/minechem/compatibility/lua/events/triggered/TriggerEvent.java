package minechem.compatibility.lua.events.triggered;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minechem.compatibility.lua.events.LuaEvent;

public abstract class TriggerEvent<T extends Event> extends LuaEvent
{
    public TriggerEvent(String name, EventBus bus)
    {
        super(name);
        bus.register(this);
    }

    @SubscribeEvent
    public void eventListener(T event)
    {
        if (applies(event)) announce(event);
    }

    public abstract void announce(T event);

    public abstract boolean applies(T event);

    public abstract Object[] constructMessage(T event);
}
