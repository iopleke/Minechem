package minechem.utils;

public class TimeHelper
{
    public static String getTimeFromTicks(long ticks)
    {
        if (ticks < 0)
        {
            return MinechemUtil.getLocalString("minechem.unstable");
        }
        String timeLeft = "";
        String hourabbr = MinechemUtil.getLocalString("minechem.hour.abbr");
        String minabbr = MinechemUtil.getLocalString("minechem.min.abbr");
        String secabbr = MinechemUtil.getLocalString("minechem.sec.abbr");

        if (ticks < Constants.TICKS_PER_DAY)
        {
            int hrs = (int) (ticks / Constants.TICKS_PER_HOUR);
            ticks = ticks - (Constants.TICKS_PER_HOUR * hrs);
            if (hrs > 0)
            {
                if (hourabbr.isEmpty() || hourabbr.equals("minechem.hour.abbr"))
                {
                    timeLeft = hrs + "hr ";
                } else
                {
                    timeLeft = hrs + hourabbr + " ";
                }
            }
        }
        if (ticks < Constants.TICKS_PER_HOUR)
        {
            int mins = (int) (ticks / Constants.TICKS_PER_MINUTE);
            ticks = ticks - (Constants.TICKS_PER_MINUTE * mins);
            if (mins > 0)
            {
                if (minabbr.isEmpty() || minabbr.equals("minechem.min.abbr"))
                {
                    timeLeft = timeLeft + mins + "m";
                } else
                {
                    timeLeft = timeLeft + mins + minabbr;
                }
            }
        }
        if (ticks < Constants.TICKS_PER_MINUTE)
        {
            int secs = (int) (ticks / Constants.TICKS_PER_SECOND);
            ticks = ticks - (Constants.TICKS_PER_SECOND * secs);
            if (secs > 0)
            {
                if (!timeLeft.equals(""))
                {
                    timeLeft += " ";
                }
                if (secabbr.isEmpty() || secabbr.equals("minechem.sec.abbr"))
                {
                    timeLeft = timeLeft + secs + "s";
                } else
                {
                    timeLeft = timeLeft + secs + secabbr;
                }
            }
        }
        return timeLeft;
    }
}
