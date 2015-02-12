package minechem.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class WorldHandler
{
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event)
    {
        JournalHandler.saveResearch();
    }
}
