package minechem.potion;

public class PotionChemical implements Cloneable
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

    @Override
    public PotionChemical clone()
    {
        return copy();
    }
}
