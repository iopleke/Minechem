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
}
