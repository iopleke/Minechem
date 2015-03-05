package minechem.helper;

import java.util.List;
import net.minecraft.client.gui.FontRenderer;

public class StringHelper
{
    public static int getSplitStringHeight(FontRenderer fontRenderer, String string, int width)
    {
        List<?> stringRows = fontRenderer.listFormattedStringToWidth(string, width);
        return stringRows.size() * fontRenderer.FONT_HEIGHT;
    }

    public static String toString(String separator, Object... objects)
    {
        String result = "";
        for (Object object : objects)
        {
            result += String.valueOf(object) + separator;
        }
        return result.substring(0, result.length() - separator.length());
    }
}
