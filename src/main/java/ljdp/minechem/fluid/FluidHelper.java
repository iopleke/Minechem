package ljdp.minechem.fluid;

import java.util.HashMap;

import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.api.core.EnumMolecule;

public class FluidHelper {
	
	public static HashMap<EnumMolecule,FluidChemical> molecule=new HashMap();
	public static final int FLUID_CONSTANT=5;
	public static HashMap<EnumElement,FluidElement> elements=new HashMap();
	
	public static void registerFluids(){
		for(EnumMolecule moleculeToCreate:EnumMolecule.values()){
			molecule.put(moleculeToCreate, new FluidChemical(moleculeToCreate));
		}
		for(EnumElement moleculeToCreate:EnumElement.values()){
			elements.put(moleculeToCreate, new FluidElement(moleculeToCreate));
		}
	}
	
	
}
