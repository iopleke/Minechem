package minechem.helper;

/**
 * Helper class for RGB color values
 *
 * getRed/getBlue/getGreen returns the RGB from a TrueColor value eg: getBlue(Compendium.TrueColor.green) returns 0.0F getGreen(Compendium.TrueColor.green) returns 1.0F
 * getRed(Compendium.TrueColor.green) returns 0.0F
 */
public class ColourHelper
{
    /**
     * Convert an RGB value to TrueColor
     *
     * @param red
     * @param green
     * @param blue
     * @return TrueColor value
     */
    public static int RGB(int red, int green, int blue)
    {
        return RGBA(red, green, blue, 255);
    }

    /**
     * Convert RGBA value to TrueColor
     *
     * @param red
     * @param green
     * @param blue
     * @param alpha
     * @return TrueColor value
     */
    public static int RGBA(int red, int green, int blue, int alpha)
    {
        return (alpha << 24) | ((red & 255) << 16) | ((green & 255) << 8) | ((blue & 255));
    }

    /**
     * Get the alpha from a TrueColor value
     *
     * @param color
     * @return
     */
    public static float getAlpa(int color)
    {
        return ((color >> 24) & 255) / 255.0F;
    }

    /**
     * Get the blue value of a TrueColor value
     *
     * @param color
     * @return
     */
    public static float getBlue(int color)
    {
        return (color & 255) / 255.0F;
    }

    /**
     * Get the green value of a TrueColor value
     *
     * @param color
     * @return
     */
    public static float getGreen(int color)
    {
        return ((color >> 8) & 255) / 255.0F;
    }

    /**
     * Get the red value of a TrueColor value
     *
     * @param color color to get the value from
     * @return
     */
    public static float getRed(int color)
    {
        return (color >> 16 & 255) / 255.0F;
    }
}
