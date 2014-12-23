package minechem.oredictionary;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minechem.MinechemRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class OreEventHandler
{

    @SubscribeEvent
    public void onOreEvent(OreDictionary.OreRegisterEvent event)
    {
        String oreName = event.Name;
        for (OreDictionaryHandler handler : MinechemRecipes.getOreDictionaryHandlers())
        {
            if (handler.canHandle(oreName))
            {
                handler.handle(oreName);
                return;
            }
        }
    }

}
