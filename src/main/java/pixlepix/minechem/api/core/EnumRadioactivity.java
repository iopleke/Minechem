package pixlepix.minechem.api.core;

import pixlepix.minechem.api.util.Constants;

public enum EnumRadioactivity {
    stable(0, 0),
    hardlyRadioactive(Constants.TICKS_PER_DAY, 1),
    slightlyRadioactive(Constants.TICKS_PER_HOUR, 2),
    radioactive(Constants.TICKS_PER_MINUTE * 10, 4),
    highlyRadioactive(Constants.TICKS_PER_MINUTE * 2, 8),
    extremelyRadioactive(Constants.TICKS_PER_SECOND * 20, 16);

    private int life;
    private int damage;

    private EnumRadioactivity(int life, int damage) {
        this.life = life;
        this.damage = damage;
    }

    public int getLife() {
        return this.life;
    }

    public int getDamage() {
        return this.damage;
    }
}
