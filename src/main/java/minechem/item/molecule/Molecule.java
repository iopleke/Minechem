package minechem.item.molecule;

import minechem.potion.PotionChemical;

public class Molecule extends PotionChemical
{

	public MoleculeEnum molecule;

	public Molecule(MoleculeEnum molecule, int amount)
	{
		super(amount);
		this.molecule = molecule;
	}

	@Override
	public PotionChemical copy()
	{
		return new Molecule(molecule, amount);
	}

    public Molecule(MoleculeEnum molecule)
	{
		super(1);
		this.molecule = molecule;
	}

	@Override
	public boolean sameAs(PotionChemical potionChemical)
	{
		return potionChemical instanceof Molecule && ((Molecule) potionChemical).molecule == molecule;
	}

}
