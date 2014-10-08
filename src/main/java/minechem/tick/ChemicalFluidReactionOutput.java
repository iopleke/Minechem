package minechem.tick;

import java.util.Map;

public class ChemicalFluidReactionOutput {
	
	public final Map<Enum, Float> outputs;
	public final float explosionLevel;
	
	/**
	 * The key of outputs is a chemical, the value of outputs is chance.
	 * If explosionLevel==Float.NaN, then it will not explode.
	 * 
	 * @param outputs
	 * @param explosionLevel
	 */
	public ChemicalFluidReactionOutput(Map<Enum, Float> outputs,float explosionLevel) {
		this.outputs = outputs;
		this.explosionLevel = explosionLevel;
	}
	
}
