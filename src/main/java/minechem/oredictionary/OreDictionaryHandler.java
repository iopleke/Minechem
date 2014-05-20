package minechem.oredictionary;

import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public interface OreDictionaryHandler
{
    boolean canHandle(OreRegisterEvent event);

    void handle(OreRegisterEvent event);
}
