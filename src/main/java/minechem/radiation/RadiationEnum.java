package minechem.radiation;

public enum RadiationEnum
{
    stable(0, 0),
    hardlyRadioactive(24, 1),
    slightlyRadioactive(12, 2),
    radioactive(6, 6),
    highlyRadioactive(3, 8),
    extremelyRadioactive(1, 16);

    private float decayChance;
    private int damage;

    // ttl is in hours
    private RadiationEnum(int ttl, int damage)
    {
        this.decayChance = ttl == 0 ? 0F : (1F / ttl);
        this.damage = damage;
    }

    public float getDecayChance()
    {
        return this.decayChance;
    }

    public int getDamage()
    {
        return this.damage;
    }
}
