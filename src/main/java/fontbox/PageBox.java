package fontbox;

import java.util.LinkedList;

/**
 * One whole page containing a collection of spaced lines with line-heights and inside a page margin (gutters).
 *
 * @author AfterLifeLochie
 */
public class PageBox
{
    public final int page_width;
    public final int page_height;
    public final int margin_left;
    public final int margin_right;
    public final int min_space_size;
    public final int lineheight_size;
    public LinkedList<LineBox> lines = new LinkedList<LineBox>();

    public PageBox(int w, int h, int ml, int mr, int min_sp, int min_lhs)
    {
        page_width = w;
        page_height = h;
        margin_left = ml;
        margin_right = mr;
        min_space_size = min_sp;
        lineheight_size = min_lhs;
    }

    public int getFreeHeight()
    {
        int h = page_height;
        for (LineBox line : lines)
        {
            h -= line.line_height;
        }
        return h;
    }
}
