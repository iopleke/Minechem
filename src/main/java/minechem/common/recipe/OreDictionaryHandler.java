package minechem.common.recipe;

import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public interface OreDictionaryHandler {
    boolean canHandle(OreRegisterEvent event);

    void handle(OreRegisterEvent event);
}
