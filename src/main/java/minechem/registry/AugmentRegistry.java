package minechem.registry;

import minechem.item.augment.augments.IAugment;

import java.util.HashMap;
import java.util.Map;

public class AugmentRegistry
{
    private static Map<String, IAugment> chemicalAugmentMap = new HashMap<String, IAugment>();

    public static void registerAugment(IAugment augment)
    {
        if (!chemicalAugmentMap.containsKey(augment.getKey()))
        {
            chemicalAugmentMap.put(augment.getKey(),augment);
        }
        else
        {
            throw new IllegalArgumentException("Chemical Augment is already Registered");
        }
    }

    public static boolean unregisterAugment(String name)
    {
        return chemicalAugmentMap.remove(name) != null;
    }

    public static IAugment getAugment(String name)
    {
        return chemicalAugmentMap.get(name);
    }
}
