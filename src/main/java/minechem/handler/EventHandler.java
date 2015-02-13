package minechem.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * Holds all event triggered methods
 */
public class EventHandler
{
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event)
    {
        JournalHandler.saveResearch();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre paramPre)
    {
        IconHandler.registerIcons(paramPre);
    }
}
