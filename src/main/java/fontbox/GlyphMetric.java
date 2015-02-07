package fontbox;

/**
 * Metrics about a character
 *
 * @author AfterLifeLochie
 */
public class GlyphMetric
{
    /**
     * The character's width
     */
    public int width;
    /**
     * The character's height
     */
    public int height;

    /**
     * The u-coordinate of the texture
     */
    public int ux;
    /**
     * The v-coordinate of the texture
     */
    public int vy;

    /**
     * Creates a new GlpyhMetric.
     *
     * @param w The character's width
     * @param h The character's height
     * @param u The u-coordinate of the texture
     * @param v The v-coordinate of the texture
     */
    public GlyphMetric(int w, int h, int u, int v)
    {
        width = w;
        height = h;
        ux = u;
        vy = v;
    }
}
