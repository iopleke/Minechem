package minechem.radiation;

import minechem.Settings;
import minechem.utils.Constants;

public enum RadiationEnum
{
	stable(0, 0, "7"),
	hardlyRadioactive(Constants.TICKS_PER_DAY, 1, "a"),
	slightlyRadioactive(Constants.TICKS_PER_HOUR * 12, 2,"2"),
	radioactive(Constants.TICKS_PER_HOUR * 6, 6,"e"),
	highlyRadioactive(Constants.TICKS_PER_HOUR * 3, 8,"6"),
	extremelyRadioactive(Constants.TICKS_PER_HOUR, 16, "4");

	private long life;
	private int damage;
	private String colour;

	private RadiationEnum(int life, int damage, String colour)
	{
		this.life = life;
		this.damage = damage;
		this.colour = Constants.TEXT_MODIFIER + colour;
	}

	public long getLife()
	{
		return (long) (this.life * (Settings.halfLifeMultiplier / 100F));
	}

	public int getDamage()
	{
		return this.damage;
	}

	public String getColour()
	{
		return colour;
	}
}
