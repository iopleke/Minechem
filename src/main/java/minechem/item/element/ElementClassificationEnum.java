package minechem.item.element;

import minechem.item.IDescriptiveName;
import net.minecraft.util.StatCollector;

public enum ElementClassificationEnum implements IDescriptiveName
{
	nonmetal("Non-metal"), inertGas("Inert gas"), halogen("Halogen"), alkaliMetal("Alkali metal"), alkalineEarthMetal("Alkaline earth metal"), semimetallic("Metalloid"), // Yes this is the proper name!
	otherMetal("Other metal"), transitionMetal("Transition metal"), lanthanide("Lanthanide"), actinide("Actinide");

	private final String descriptiveName;

	ElementClassificationEnum(String descriptiveName)
	{
		this.descriptiveName = descriptiveName;
	}

	public String className()
	{
		return descriptiveName;
	}

	@Override
	public String descriptiveName()
	{
		String localizedName = StatCollector.translateToLocal("element.classification." + descriptiveName);
		if (!localizedName.isEmpty() || !localizedName.equals("element.classification." + descriptiveName))
		{
			return localizedName;
		}
		return descriptiveName;
	}
}
