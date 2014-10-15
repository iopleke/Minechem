package minechem.radiation;

import minechem.Settings;
import minechem.utils.Constants;

public enum RadiationEnum
{
    stable(0, 0),
    hardlyRadioactive(Constants.TICKS_PER_DAY, 1),
    slightlyRadioactive(Constants.TICKS_PER_HOUR * 12, 2),
    radioactive(Constants.TICKS_PER_HOUR * 6, 6),
    highlyRadioactive(Constants.TICKS_PER_HOUR * 3, 8),
    extremelyRadioactive(Constants.TICKS_PER_HOUR, 16);

    private long life;
    private int damage;

    private RadiationEnum(int life, int damage)
    {
        this.life = life;
        this.damage = damage;
    }

    public long getLife()
    {
        return (long)(this.life * (Settings.halfLifeMultiplier / 100F));
    }

    public int getDamage()
    {
        return this.damage;
    }
}
