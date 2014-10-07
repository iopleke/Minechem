package minechem.fluid;

import minechem.MinechemItemsRegistration;
import minechem.item.ChemicalRoomStateEnum;
import minechem.item.molecule.MoleculeEnum;
import minechem.utils.MinechemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import java.awt.*;

public class FluidChemical extends Fluid implements IMinechemFluid
{

    public MoleculeEnum molecule;

    public FluidChemical(MoleculeEnum molecule)
    {
        super(molecule.name());
        this.molecule = molecule;
        ChemicalRoomStateEnum roomState=molecule.roomState;
        setDensity(roomState.isGas() ? -10 : 10); // How tick the fluid is, affects movement inside the liquid.
        setViscosity(roomState.getViscosity()); // How fast the fluid flows.
        setGaseous(roomState.isGas());
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
        int red = (int) (molecule.red * 255);

        int green = (int) (molecule.green * 255);
        int blue = (int) (molecule.blue * 255);
        return new Color(red, green, blue).getRGB();
    }

    @Override
    public String getLocalizedName(FluidStack stack)
    {
        return MinechemHelper.getLocalString("element.property.liquid") + " " + molecule.descriptiveName();
    }
}
