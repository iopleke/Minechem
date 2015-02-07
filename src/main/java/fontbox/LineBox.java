package fontbox;

/**
 * One formatted line with a spacing and line-height
 *
 * @author AfterLifeLochie
 */
public class LineBox
{
    public final String line;
    public final int space_size;
    public final int line_height;

    public LineBox(String line, int space_size, int line_height)
    {
        this.line = line;
        this.space_size = space_size;
        this.line_height = line_height;
    }
}
