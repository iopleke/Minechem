package universalelectricity.api.core.grid.electric;

import universalelectricity.api.core.grid.INode;

/**
 * @author Calclavia
 */
public interface IElectricNode extends INode
{
	public void applyPower(double wattage);

	public void drawPower(double wattage);

	public double getEnergy(double voltageThreshold);

	public double getVoltage();

	public double getEnergyCapacity();

	public void setEnergyCapacity(double value);

	public double getEmptySpace();

	/**
	 * Resistance in Ohms
	 */
	public double getResistance();

	public void setResistance(double value);

}
