package minechem.potion;

public class PotionMineral extends PotionChemical
{
	public PotionMineralEnum mineral;

	public PotionMineral(PotionMineralEnum mineral, int amount)
	{
		super(amount);
		this.mineral = mineral;
	}

	@Override
	public PotionChemical copy()
	{
		return new PotionMineral(mineral, amount);
	}

    public PotionMineral(PotionMineralEnum mineral)
	{
		super(1);
		this.mineral = mineral;
	}

	@Override
	public boolean sameAs(PotionChemical potionChemical)
	{
		return potionChemical instanceof PotionMineral && ((PotionMineral) potionChemical).mineral == mineral;
	}

}
