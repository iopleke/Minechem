package minechem.registry;

import java.util.HashMap;
import java.util.Map;
import minechem.item.augment.augments.IAugment;

public class AugmentRegistry
{
    private static Map<String, IAugment> chemicalAugmentMap = new HashMap<String, IAugment>();

    /**
     * Returns the augment for an unlocalized item name
     *
     * @param name unlocalized item name
     * @return
     */
    public static IAugment getAugment(String name)
    {
        return chemicalAugmentMap.get(name);
    }

    /**
     * Register an augment
     *
     * @param augment
     */
    public static void registerAugment(IAugment augment)
    {
        if (!chemicalAugmentMap.containsKey(augment.getKey()))
        {
            chemicalAugmentMap.put(augment.getKey(), augment);
        } else
        {
            throw new IllegalArgumentException("Chemical Augment is already Registered");
        }
    }

    /**
     * Remove an augment from the registry
     *
     * @param name unlocalized item name
     * @return was the augment successfully removed
     */
    public static boolean unregisterAugment(String name)
    {
        return chemicalAugmentMap.remove(name) != null;
    }
}
