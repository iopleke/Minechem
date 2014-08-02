package universalelectricity.api.core.grid;

import net.minecraft.nbt.NBTTagCompound;

public interface INode
{
	/**
	 * Called to reconstruct and reset all connections for this node/
	 */
	public void reconstruct();

	/**
	 * This destroys the node, removing it from the grid and also destroying all references to it.
	 */
	public void deconstruct();

	/**
	 * Clears all the node's cache.
	 */
	public void recache();

	/**
	 * Called to update the node.
	 *
	 * @param deltaTime - Time in seconds that has passed compared to the last update time.
	 */
	public void update(double deltaTime);

	/**
	 * Must be called to load the node's data.
	 */
	public void load(NBTTagCompound nbt);

	/**
	 * Must be called to save the node's data.
	 */
	public void save(NBTTagCompound nbt);
}
