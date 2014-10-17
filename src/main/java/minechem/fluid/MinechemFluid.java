package minechem.fluid;

import minechem.item.ChemicalRoomStateEnum;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

abstract public class MinechemFluid extends Fluid {
	
	public MinechemFluid(String fluidName,ChemicalRoomStateEnum roomstatus) {
		super(fluidName);
		setGaseous(roomstatus.isGas());
		setViscosity(roomstatus.getViscosity());
		setDensity(roomstatus.isGas()?-10:10);
		FluidRegistry.registerFluid(this);
	}

	abstract public ItemStack getOutputStack();
	
}
