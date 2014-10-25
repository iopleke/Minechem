package minechem.api;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * To be implemented on any container that should stop elements or molecules from decaying
 *
 * @author way2muchnoise
 */
public interface INoDecay
{
	/**
	 * @return A list of the itemStacks that will not decay
	 */
	public List<ItemStack> getStorageInventory();

	/**
	 * @return A list of itemStacks that will decay
	 */
	public List<ItemStack> getPlayerInventory();
}
