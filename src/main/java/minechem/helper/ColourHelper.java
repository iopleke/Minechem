package minechem.helper;

/**
 * Helper class for RGB color values
 *
 * getRed/getBlue/getGreen returns the RGB from a {@link minechem.Compendium.Color.TrueColor} value eg: getBlue(Compendium.TrueColor.green) returns 0.0F getGreen(Compendium.TrueColor.green) returns
 * 1.0F getRed(Compendium.TrueColor.green) returns 0.0F
 */
public class ColourHelper
{
    /**
     * Convert an RGB value to {@link minechem.Compendium.Color.TrueColor}
     *
     * @param red
     * @param green
     * @param blue
     * @return {@link minechem.Compendium.Color.TrueColor} value
     */
    public static int RGB(int red, int green, int blue)
    {
        return RGBA(red, green, blue, 255);
    }

    /**
     * Convert an RGB value to {@link minechem.Compendium.Color.TrueColor}
     *
     * @param red
     * @param green
     * @param blue
     * @return {@link minechem.Compendium.Color.TrueColor} value
     */
    public static int RGB(float red, float green, float blue)
    {
        return RGBA((int)red*255, (int)green*255, (int)blue*255, 255);
    }

    /**
     * Convert an #RRGGBB value to {@link minechem.Compendium.Color.TrueColor}
     *
     * @param colour the #RRGGBB value
     * @return the {@link minechem.Compendium.Color.TrueColor} value or an {@link java.lang.IllegalArgumentException} if a mal formed input is given
     */
    public static int RGB(String colour)
    {
        if (!colour.startsWith("#") || !(colour.length() == 7))
        {
            throw new IllegalArgumentException("Use #RRGGBB format");
        }
        return RGB(Integer.parseInt(colour.substring(1, 3), 16), Integer.parseInt(colour.substring(3, 5), 16), Integer.parseInt(colour.substring(5, 7), 16));
    }

    /**
     * Convert RGBA value to {@link minechem.Compendium.Color.TrueColor}
     *
     * @param red
     * @param green
     * @param blue
     * @param alpha
     * @return {@link minechem.Compendium.Color.TrueColor} value
     */
    public static int RGBA(int red, int green, int blue, int alpha)
    {
        return (alpha << 24) | ((red & 255) << 16) | ((green & 255) << 8) | ((blue & 255));
    }

    /**
     * Get the alpha from a {@link minechem.Compendium.Color.TrueColor} value
     *
     * @param color
     * @return
     */
    public static float getAlpha(int color)
    {
        return ((color >> 24) & 255) / 255.0F;
    }

    /**
     * Get the blue value of a {@link minechem.Compendium.Color.TrueColor} value
     *
     * @param color
     * @return
     */
    public static float getBlue(int color)
    {
        return (color & 255) / 255.0F;
    }

    /**
     * Get the green value of a {@link minechem.Compendium.Color.TrueColor} value
     *
     * @param color
     * @return
     */
    public static float getGreen(int color)
    {
        return ((color >> 8) & 255) / 255.0F;
    }

    /**
     * Get the red value of a {@link minechem.Compendium.Color.TrueColor} value
     *
     * @param color color to get the value from
     * @return
     */
    public static float getRed(int color)
    {
        return (color >> 16 & 255) / 255.0F;
    }

    /**
     * Blends given int colours
     *
     * @param colours an amount of colours
     * @return the mix int colour value or an IllegalArgumentException if colours is empty
     */
    public static int blend(int... colours)
    {
        if (colours.length < 1)
        {
            throw new IllegalArgumentException();
        }

        int[] alphas = new int[colours.length];
        int[] reds = new int[colours.length];
        int[] greens = new int[colours.length];
        int[] blues = new int[colours.length];

        for (int i = 0; i < colours.length; i++)
        {
            alphas[i] = (colours[i] >> 24 & 0xff);
            reds[i] = ((colours[i] & 0xff0000) >> 16);
            greens[i] = ((colours[i] & 0xff00) >> 8);
            blues[i] = (colours[i] & 0xff);
        }

        float a, r, g, b;
        a = r = g = b = 0;
        float ratio = 1.0F / colours.length;

        for (int alpha : alphas)
        {
            a += alpha * ratio;
        }

        for (int red : reds)
        {
            r += red * ratio;
        }

        for (int green : greens)
        {
            g += green * ratio;
        }

        for (int blue : blues)
        {
            b += blue * ratio;
        }

        return (((int) a) << 24 | ((int) r) << 16 | ((int) g) << 8 | ((int) b));
    }

    public static int tone(int colour, float scale)
    {
        float r = (colour >> 16) & 255;
        float g = (colour >> 8) & 255;
        float b = colour & 255;
        return RGB(r*scale, g*scale, b*scale);
    }
}
