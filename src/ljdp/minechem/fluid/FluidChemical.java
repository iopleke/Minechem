package ljdp.minechem.fluid;

import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.common.MinechemItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidChemical extends Fluid implements IMinechemFluid{

	public EnumMolecule molecule;
	public FluidChemical(EnumMolecule molecule) {
		super("Minechem Chemical: "+molecule.descriptiveName());
		this.molecule=molecule;
		setDensity(10); // How tick the fluid is, affects movement inside the liquid.
		setViscosity(1000); // How fast the fluid flows.
		FluidRegistry.registerFluid(this);
	}
	@Override
	public ItemStack getOutputStack() {
		return new ItemStack(MinechemItems.molecule,1,molecule.id());
	}
	@Override

    public int getColor(){
		return (int) (0x100000*molecule.red+0x100*molecule.green+molecule.blue);
	}
	

}
