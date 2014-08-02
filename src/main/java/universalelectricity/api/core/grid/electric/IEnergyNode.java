package universalelectricity.api.core.grid.electric;

import universalelectricity.api.core.grid.INode;

public interface IEnergyNode extends INode
{
	/**
	 * @return Gets the power of this node. Note that power by definition is energy per second.
	 */
	public double getPower();

	/**
	 * @return Gets the energy buffered in this node at this instance.
	 */
	public double getEnergy();
}
