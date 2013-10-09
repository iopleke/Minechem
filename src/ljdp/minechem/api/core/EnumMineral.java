package ljdp.minechem.api.core;

import java.util.ArrayList;

public enum EnumMineral {
	quartz("Quartz", new Molecule(EnumMolecule.siliconDioxide)),
	berlinite("Berlinite", new Molecule(EnumMolecule.aluminiumPhosphate, 4)),
	;

	private final String descriptiveName;
	private final ArrayList<Chemical> components;

	public String getName() {
		return this.descriptiveName;
	}

	public Chemical[] getComposition() {
		return this.components.toArray(new Chemical[this.components.size()]);
	}

	EnumMineral(String descriptiveName, Chemical... chemicals) {
		this.descriptiveName = descriptiveName;
		this.components = new ArrayList<Chemical>();
		for (Chemical chemical : chemicals) {
			this.components.add(chemical);
		}
	}
}
