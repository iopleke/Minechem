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

    /**
     * Concat all objects with given separator
     *
     * @param separator eg. ", "
     * @param objects
     * @return one string
     */
    public static String toString(String separator, Object... objects)
    {
        StringBuilder result = new StringBuilder();
        String value;
        for (Object object : objects)
        {
            if (object != null && object.getClass().isArray())
            {
                StringBuilder intermediate = new StringBuilder();
                intermediate.append("[");
                for (Object o : ArrayHelper.convertToArray(object))
                {
                    intermediate.append(toString(separator, o)).append(separator);
                }
                value = intermediate.substring(0, intermediate.length() - separator.length()) + "]";
            }
            else
            {
                value = String.valueOf(object);
            }
            if (value.equals("%"))
            {
                value = "%%"; // fixes issue wit formatter
            }
            result.append(value).append(separator);
        }
        return result.substring(0, result.length() - separator.length());
    }
}
