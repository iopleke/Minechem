package pixlepix.minechem.fluid;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import pixlepix.minechem.api.core.EnumMolecule;
import pixlepix.minechem.common.MinechemItems;
import net.minecraft.block.Block;
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
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
			this.setFlowingIcon(Block.waterMoving.getIcon(0, 0));
			this.setStillIcon(Block.waterMoving.getIcon(0, 0));
    	}
	}
	@Override
	public ItemStack getOutputStack() {
		return new ItemStack(MinechemItems.molecule,1,molecule.id());
	}
	@Override

    public int getColor(){
		int red=(int) (molecule.red*256);

		int green=(int) (molecule.green*256);
		int blue=(int) (molecule.blue*256);
		return red << 16|green<<8|blue;
	}
	

}
