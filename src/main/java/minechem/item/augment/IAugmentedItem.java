package minechem.item.augment;

import java.util.Map;
import minechem.item.augment.augments.IAugment;
import net.minecraft.item.ItemStack;

public interface IAugmentedItem
{
    /**
     * Get Map of Augments and corresponding levels present on an item
     *
     * @param item
     * @return
     */
    Map<IAugment, Integer> getAugments(ItemStack item);

    /**
     * @param item
     * @param augment
     * @return true if item has specified augment
     */
    boolean hasAugment(ItemStack item, IAugment augment);

    boolean canHaveAugment(ItemStack item, IAugment augment);

    /**
     * Removes a specified Augment from the ItemStack
     *
     * @param item
     * @param augment
     * @return true if it existed and was removed
     */
    boolean removeAugment(ItemStack item, IAugment augment);

    /**
     * Set {@link minechem.item.augment.augments.IAugment} on Item
     *
     * @param item    ItemStack to add augment to
     * @param augmentItem Augment to add
     */
    boolean setAugment(ItemStack item, ItemStack augmentItem);

    boolean setAugmentLevel(ItemStack item, IAugment augment, int level);

    int getAugmentLevel(ItemStack item, IAugment augment);
}
