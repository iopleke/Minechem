package minechem.tick;

import java.util.Map;

public class ChemicalExplosionReactionOutput {
	
	public final Map<Enum, Float> outputs;
	public final float explosionLevel;
	
	/**
	 * The key of outputs is a chemical, the value of outputs is change.
	 * If explosionLevel==Float.NaN, then it will not explode.
	 * 
	 * @param outputs
	 * @param explosionLevel
	 */
	public ChemicalExplosionReactionOutput(Map<Enum, Float> outputs,float explosionLevel) {
		this.outputs = outputs;
		this.explosionLevel = explosionLevel;
	}
	
}
