package minechem.item.element;

import minechem.utils.Constants;

public enum EnumRadioactivity
{
    stable(0, 0), hardlyRadioactive(Constants.TICKS_PER_DAY, 1), slightlyRadioactive(Constants.TICKS_PER_HOUR * 12, 2), radioactive(Constants.TICKS_PER_HOUR * 6, 6), highlyRadioactive(Constants.TICKS_PER_HOUR * 3, 8), extremelyRadioactive(
            Constants.TICKS_PER_HOUR * 1, 16);

    private int life;
    private int damage;

    private EnumRadioactivity(int life, int damage)
    {
        this.life = life;
        this.damage = damage;
    }

    public int getLife()
    {
        return this.life;
    }

    public int getDamage()
    {
        return this.damage;
    }
}
