package minechem.api.util;

public class Constants {
    public static final int TICKS_PER_SECOND = 20;
    public static final int TICKS_PER_MINUTE = TICKS_PER_SECOND * 60;
    public static final int TICKS_PER_HOUR = TICKS_PER_MINUTE * 60;
    public static final int TICKS_PER_DAY = TICKS_PER_HOUR * 24;
    public static final int TICKS_PER_WEEK = TICKS_PER_DAY * 7;
    public static final int GAS_PER_VIAL = 32; // Using mekanism this gets you 1024 hydrogen vs 1000 from a bucket of water.
    public static final String TEXT_MODIFIER = "\u00A7";
    public static final String TEXT_MODIFIER_REGEX = TEXT_MODIFIER + ".";
}
