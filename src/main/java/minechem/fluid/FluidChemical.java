package minechem.fluid;

import minechem.MinechemItemsRegistration;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.awt.*;

public class FluidChemical extends Fluid implements IMinechemFluid
{

    public MoleculeEnum molecule;

    public FluidChemical(MoleculeEnum molecule)
    {
        super(molecule.name());
        this.molecule = molecule;
        setDensity(10); // How tick the fluid is, affects movement inside the liquid.
        setViscosity(1000); // How fast the fluid flows.
        FluidRegistry.registerFluid(this);
    }

    @Override
    public ItemStack getOutputStack()
    {
        return new ItemStack(MinechemItemsRegistration.molecule, 1, molecule.id());
    }

    @Override
    public int getColor()
    {
        int red = (int) (molecule.red * 256);

        int green = (int) (molecule.green * 256);
        int blue = (int) (molecule.blue * 256);
        return new Color(red, green, blue, 120).getRGB();
    }

}
