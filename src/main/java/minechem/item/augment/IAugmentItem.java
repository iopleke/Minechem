package minechem.item.augment;

import minechem.item.augment.augments.IAugment;
import net.minecraft.item.ItemStack;

import java.util.Map;

public interface IAugmentItem
{
    /**
     * Get Map of Augments and corresponding levels present on an item
     *
     * @param item
     * @return
     */
    Map<IAugment, Integer> getAugments(ItemStack item);

    /**
     * Set {@link minechem.item.augment.augments.IAugment} on Item
     *
     * @param item      ItemStack to add augment to
     * @param augment   Augment to add
     * @param level     Augment level
     */
    void setAugment(ItemStack item, IAugment augment, int level);

    /**
     * Removes a specified Augment from the ItemStack
     *
     * @param item
     * @param augment
     * @return true if it existed and was removed
     */
    boolean removeAugment(ItemStack item, IAugment augment);

    /**
     * @param item
     * @param augment
     * @return true if item has specified augment
     */
    boolean hasAugment(ItemStack item, IAugment augment);
}
