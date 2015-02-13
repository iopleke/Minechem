package minechem.helper;

import fontbox.*;
import fontbox.io.StackedPushbackStringReader;
import minechem.Compendium;
import minechem.Config;
import org.apache.logging.log4j.Level;

import java.io.IOException;

public class FontBoxHelper
{
    public static LayoutCalculator layoutCalculator = new LayoutCalculator();
    public static final WrittenFontRenderer writtenFontRenderer = new WrittenFontRenderer();
    public static final FontMetric fontMetricDaniel;
    public static final FontRenderBuffer fontRenderBufferDaniel;

    /**
     * Sets up all static data
     */
    static 
    {
        fontMetricDaniel = new FontMetric(Compendium.Resource.Font.danielFont, 418, 242, Compendium.Resource.Font.danielMetrics);
        try
        {
            fontMetricDaniel.buildFont();
        } catch (FontException e)
        {
            LogHelper.exception("Could not load custom font", e, Level.WARN);
        }
        fontRenderBufferDaniel = new FontRenderBuffer(fontMetricDaniel);
    }

    public static void boxLine(String text, PageBox pageBox)
    {
        try
        {
            layoutCalculator.boxLine(fontMetricDaniel, new StackedPushbackStringReader(text), pageBox);
        } catch (IOException e)
        {
            LogHelper.exception("Something went wrong during the boxing of the text", e, Level.WARN);
        }
    }

    public static void renderPageBox(PageBox pageBox, float x, float y, float z)
    {
        writtenFontRenderer.renderPages(fontMetricDaniel, fontRenderBufferDaniel, pageBox, x, y , z, Config.debugMode);
    }
    
    public static PageBox boxText(String text, int width, int height, int margin_l, int margin_r, int min_sp, int min_lhs)
    {
        try
        {
            return layoutCalculator.boxParagraph(fontMetricDaniel, text, width, height, margin_l, margin_r, min_sp, min_lhs)[0];
        } catch (IOException e)
        {
            LogHelper.exception("Something went wrong during the boxing of the text", e, Level.WARN);
        }
        return null;
    }
}
