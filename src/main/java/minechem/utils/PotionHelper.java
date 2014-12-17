package minechem.utils;

import net.minecraft.potion.Potion;

import java.util.LinkedHashMap;
import java.util.Map;

public class PotionHelper
{
    private static Map<String, Potion> potionMap = new LinkedHashMap<String, Potion>();

    public static Potion getPotionByName(String name)
    {
        if (potionMap.isEmpty()) registerPotions();

        return potionMap.get(name.toLowerCase());
    }

    private static void registerPotions()
    {
        for (Potion potion : Potion.potionTypes)
            if (potion != null) potionMap.put(potion.getName().startsWith("potion.") ? potion.getName().substring(7).toLowerCase() : potion.getName().toLowerCase(), potion);
    }
}
