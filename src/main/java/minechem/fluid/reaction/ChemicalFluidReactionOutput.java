package minechem.fluid.reaction;

import java.util.List;
import minechem.item.MinechemChemicalType;

public class ChemicalFluidReactionOutput {
	
	public final List<MinechemChemicalType> outputs;
	public final float explosionLevel;
	
	/**
	 * If explosionLevel==Float.NaN, then it will not explode.
	 * 
	 * @param outputs
	 * @param explosionLevel
	 */
	public ChemicalFluidReactionOutput(List<MinechemChemicalType> outputs,float explosionLevel) {
		this.outputs = outputs;
		this.explosionLevel = explosionLevel;
	}
	
}
