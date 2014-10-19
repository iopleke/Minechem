package minechem.potion;

public class PotionChemical
{

	public int amount;

	public PotionChemical(int amount)
	{
		this.amount = amount;
	}

	public PotionChemical copy()
	{
		return new PotionChemical(amount);
	}

	public boolean sameAs(PotionChemical potionChemical)
	{
		return false;
	}

}
