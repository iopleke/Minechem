package fontbox;

import fontbox.io.StackedPushbackStringReader;
import java.io.IOException;
import java.util.ArrayList;

public class LayoutCalculator
{
    /**
     * Attempt to box a line or part of a line onto a PageBox. This immediately attempts to fit as much of the line onto a LineBox and then glues it to the tail of a PageBox if the PageBox can support
     * the addition of a line. Any overflow text which cannot be boxed onto the page is returned.
     *
     * @param metric The font metric to calculate with
     * @param text   The line
     * @param page   The page to box onto
     * @return If a page overflow occurs - that is, if there is no more available vertical space for lines to occupy.
     * @throws java.io.IOException
     */
    public boolean boxLine(FontMetric metric, StackedPushbackStringReader text, PageBox page) throws IOException
    {
        // Calculate some required properties
        int effectiveWidth = page.page_width - page.margin_left
            - page.margin_right;
        int effectiveHeight = page.getFreeHeight();

        int width_new_line = 0, width_new_word = 0;
        // Start globbing characters
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Character> chars = new ArrayList<Character>();
        // Push our place in case we have to abort
        text.pushPosition();
        while (text.available() > 0)
        {
            // Take a char
            char c = text.next();
            // Treat space as a word separator
            if (c == '\r' || c == '\n')
            {
                // Look and see if the next character is a newline
                char c1 = text.next();
                if (c1 != '\n' && c1 != '\r')
                {
                    text.rewind(1); // put it back, aha!
                }
                break; // hard eol
            } else if (c == ' ')
            {
                // Push a whole word if one exists
                if (chars.size() > 0)
                {
                    // Find out if there is enough space to push this word
                    int new_width_nl = width_new_line + width_new_word
                        + page.min_space_size;
                    if (effectiveWidth >= new_width_nl)
                    {
                        // Yes, there is enough space, add the word
                        width_new_line += width_new_word;
                        StringBuilder builder = new StringBuilder();
                        for (char c1 : chars)
                        {
                            builder.append(c1);
                        }
                        words.add(builder.toString());
                        // Clear the character buffers
                        chars.clear();
                        width_new_word = 0;
                    } else
                    {
                        // No, the word doesn't fit, back it up
                        chars.clear();
                        width_new_word = 0;
                        break;
                    }
                }
            } else
            {
                GlyphMetric mx = metric.glyphs.get((int) c);
                if (mx != null)
                {
                    width_new_word += mx.width;
                    chars.add(c);
                }
            }
        }

        // Anything left on buffer?
        if (chars.size() > 0)
        {
            // Find out if there is enough space to push this word
            int new_width_nl = width_new_line + width_new_word
                + page.min_space_size;
            if (effectiveWidth >= new_width_nl)
            {
                // Yes, there is enough space, add the word
                width_new_line += width_new_word;
                StringBuilder builder = new StringBuilder();
                for (char c1 : chars)
                {
                    builder.append(c1);
                }
                words.add(builder.toString());
                // Clear the character buffers
                chars.clear();
                width_new_word = 0;
            } else
            {
                // No, the word doesn't fit, back it up
                chars.clear();
            }
        }

        // Find the maximum height of any characters in the line
        int height_new_line = page.lineheight_size;
        for (int i = 0; i < words.size(); i++)
        {
            String word = words.get(i);
            for (int j = 0; j < word.length(); j++)
            {
                char c = word.charAt(j);
                if (c != ' ')
                {
                    GlyphMetric mx = metric.glyphs.get((int) c);
                    if (mx.height > height_new_line)
                    {
                        height_new_line = mx.height;
                    }
                }
            }
        }

        // If the line doesn't fit at all, we can't do anything
        if (height_new_line > effectiveHeight)
        {
            text.popPosition(); // back out
            return true;
        }

        // Commit our position as we have now read a line and it fits all
        // current constraints on the page
        text.commitPosition();

        // Glue the whole line together
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < words.size(); i++)
        {
            line.append(words.get(i));
            if (i != words.size() - 1)
            {
                line.append(" ");
            }
        }

        // Figure out how much space is left over from the line
        int space_remain = effectiveWidth - width_new_line;
        int space_width = page.min_space_size;

        // If the line is not blank, then...
        if (words.size() > 0)
        {
            int extra_px_per_space = (int) Math.floor(space_remain
                / words.size());
            if (width_new_line > extra_px_per_space)
            {
                space_width = page.min_space_size + extra_px_per_space;
            }
        } else
        {
            height_new_line = 2 * page.lineheight_size;
        }

        // Make the line height fit exactly 1 or more line units
        int line_height = height_new_line;
        if (line_height % page.lineheight_size != 0)
        {
            line_height -= line_height % page.lineheight_size;
            // line_height += page.lineheight_size;
        }

        // Create the linebox
        page.lines.add(new LineBox(line.toString(), space_width, line_height));
        return false;
    }

    /**
     * Attempt to box a paragraph or part of a paragraph onto a collection of PageBox instances.
     *
     * @param metric The font metric to calculate with
     * @param text   The text blob
     * @return The page results
     */
    public PageBox[] boxParagraph(FontMetric metric, String text, int width,
        int height, int margin_l, int margin_r, int min_sp, int min_lhs)
        throws IOException
    {
        StackedPushbackStringReader reader = new StackedPushbackStringReader(
            text);
        ArrayList<PageBox> pages = new ArrayList<PageBox>();
        PageBox currentPage = new PageBox(width, height, margin_l, margin_r,
            min_sp, min_lhs);
        boolean flag = false;
        while (reader.available() > 0)
        {
            flag = boxLine(metric, reader, currentPage);
            if (flag)
            {
                pages.add(currentPage);
                currentPage = new PageBox(width, height, margin_l, margin_r,
                    min_sp, min_lhs);
            }
        }
        if (!flag)
        {
            pages.add(currentPage);
        }
        return pages.toArray(new PageBox[0]);
    }

}
