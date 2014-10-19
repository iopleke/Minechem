package minechem.fluid;

import java.awt.Color;
import minechem.MinechemItemsRegistration;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.item.ItemStack;

public class FluidChemical extends MinechemFluid
{

	public final MoleculeEnum molecule;

	public FluidChemical(MoleculeEnum molecule)
	{
		super(molecule.name(), molecule.roomState());
		this.molecule = molecule;
	}

	@Override
	public ItemStack getOutputStack()
	{
		return new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id());
	}

	@Override
	public int getColor()
	{
		int red = (int) (molecule.red * 255);
		int green = (int) (molecule.green * 255);
		int blue = (int) (molecule.blue * 255);
		return new Color(red, green, blue).getRGB();
	}
}
