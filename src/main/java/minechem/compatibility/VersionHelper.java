package minechem.compatibility;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionHelper
{

    public static boolean isModVersion(String modId, String version)
    {
        return getModVersion(modId).equals(version);
    }

    /**
     * Calculates whether the loaded version of a mod is newer than required
     *
     * @param modId       of the mod to check
     * @param versionInt  the version to compare to
     * @param deliminator the string to split the mod version on
     * @return true if loaded and version is equal to or greater - false if neither of these, or a parsing error;
     */
    public static boolean isModVersionAccepted(String modId, int[] versionInt, String deliminator)
    {
        String version = getModVersion(modId);
        if (!version.isEmpty())
        {
            String[] split = version.split(deliminator);
            for (int i = 0; i < split.length && i < versionInt.length; i++)
            {
                if (compareStringInt(split[i], versionInt[i])) return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Advanced mod version comparison - for when a simple deliminator does not fully describe the version String.
     * Example Use: Mod CoFHCore version B7+
     * <code>
     * //Pattern no Comparator
     * Pattern pattern = Pattern.compile("1\\.7\\.10R3\\.0\\.0B(?:\d{2,}|[7-9])"); - the group on the end is a non-capturing group accepting any 2 digit number or 7-9
     * if (isModVersionAccepted(Mods.COFHCORE,pattern))...
     * //Pattern with Comparator
     * Pattern pattern = Pattern.compile("1\\.7\\.10R3\\.0\\.0B(\d+)"); - group is now capturing and accepting any number
     * if (isModVersionAccepted(Mods.COFHCORE,pattern,7))...
     * </code>
     *
     * @param modId       of the mod to check
     * @param regex       Pattern
     * @param comparators optional - allows the Pattern to be simplified, or different versions to be checked without changing the pattern each time.
     * @return true for accepted version
     */
    public static boolean isModVersionAccepted(String modId, Pattern regex, int... comparators)
    {
        String version = getModVersion(modId);
        if (!version.isEmpty())
        {
            Matcher match = regex.matcher(version);
            if (match.find())
            {
                for (int i = 0; i < comparators.length && i < match.groupCount(); i++)
                {
                    if (compareStringInt(match.group(i), comparators[i])) return false;
                }
                return true;
            }
        }
        return false;
    }

    private static String getModVersion(String modId)
    {
        if (Loader.isModLoaded(modId))
        {
            for (ModContainer mod : Loader.instance().getActiveModList())
            {
                if (mod.getModId().equals(modId))
                {
                    return mod.getVersion();
                }
            }
        }
        return "";
    }

    private static boolean compareStringInt(String string, int val)
    {
        try
        {
            if (Integer.valueOf(string) >= val) return true;
        } catch (Exception e)
        {
        }
        return false;
    }
}
