package cofh.api.item;

import net.minecraft.item.ItemStack;

/**
 * Implement this interface on Item classes that are themselves inventories.
 * 
 * There's no real interaction here - the point of this is to correctly identify these items and prevent nesting.
 * 
 * @author King Lemming
 * 
 */
public interface IInventoryContainerItem {

	/**
	 * Add something to the inventory of this container item. This returns what is remaining of the original stack - a null return means that the entire stack
	 * was accepted!
	 * 
	 * @param container
	 *            ItemStack with the inventory.
	 * @param item
	 *            ItemStack to add to the inventory.
	 * @param simulate
	 *            If TRUE, the insertion will only be simulated.
	 * @return An ItemStack representing how much is remaining after the item was inserted (or would have been, if simulated) into the container inventory.
	 */
	ItemStack insertItem(ItemStack container, ItemStack item, boolean simulate);

	/**
	 * Extract something from the inventory of this container item. This returns the resulting stack - a null return means that nothing was extracted!
	 * 
	 * @param container
	 *            ItemStack with the inventory.
	 * @param item
	 *            ItemStack to extract from the inventory.
	 * @param simulate
	 *            If TRUE, the extraction will only be simulated.
	 * @return An ItemStack representing how much was extracted (or would have been, if simulated) from the container inventory.
	 */
	ItemStack extractItem(ItemStack container, ItemStack item, boolean simulate);

	/**
	 * Get the contents of the container item's inventory. These ItemStacks correspond to slots and can be null!
	 */
	ItemStack[] getInventoryContents(ItemStack container);

	/**
	 * Get the size of this inventory of this container item.
	 */
	int getSizeInventory(ItemStack container);

}
