package minechem.item;

import net.minecraft.util.StatCollector;

public enum ChemicalRoomStateEnum implements IDescriptiveName
{
	liquid("Liquid", false, 1000, 8), solid("Solid", false, 1200, 1), gas("Gaseous", true, 400, 8);

	private final boolean isGas;
	private final int viscosity;
	private final String descriptiveName;
	private final int quanta;

	ChemicalRoomStateEnum(String descriptiveName, boolean isGas, int viscosity, int quanta)
	{
		this.isGas = isGas;
		this.viscosity = viscosity;
		this.descriptiveName = descriptiveName;
		this.quanta = quanta;
	}

	public boolean isGas()
	{
		return isGas;
	}

	public int getViscosity()
	{
		return viscosity;
	}

	public int getQuanta()
	{
		return quanta;
	}

	@Override
	public String descriptiveName()
	{
		String localizedName = StatCollector.translateToLocal("element.classification." + descriptiveName);
		if (!localizedName.isEmpty() || localizedName != "element.classification." + descriptiveName)
		{
			return localizedName;
		}
		return descriptiveName;
	}
}
