package minechem.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class GuiTextField extends Gui
{
    private int xSize, ySize;
    private int x, y;
    private String text;
    private FontRenderer fontRenderer;

    public GuiTextField(int width, int height, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.xSize = width;
        this.ySize = height;
        this.text = "";
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }

    private void setText(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return this.text;
    }

    public void keyTyped(char c, int keycode)
    {
        switch (keycode)
        {
            case Keyboard.KEY_BACK:
                if (this.text.length() > 0)
                    setText(this.text.substring(0,this.text.length()-1));
                break;
            default:
                setText(this.text + c);
                break;
        }

    }

    public void draw()
    {
        drawBackground();
        drawText();
    }

    private void drawBackground()
    {
        drawRect(x - 1, y - 1, x + xSize + 1, y + ySize + 1, 0xffa0a0a0);
        drawRect(x, y, x + xSize, y + ySize, 0xff000000);
    }

    private String getDrawText()
    {
        String s = getText();
        s+="|";
        return s;
    }

    public void drawText()
    {
        drawString(fontRenderer, getDrawText(), x + 2, y + ySize / 2 - 4, 0xe0e0e0);
    }
}
