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
	private int cursorPos = 0;

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
		if (Character.isLetterOrDigit(c))
		{
			if (cursorPos != this.text.length())
			{
				setText(this.text.substring(0, cursorPos) + c + this.text.substring(cursorPos));
			} else
			{
				setText(this.text + c);
			}

			cursorPos++;
		} else
		{
			switch (keycode)
			{
				case Keyboard.KEY_BACK:
					if (this.text.length() > 0)
					{
						if (cursorPos == this.text.length())
						{
							setText(this.text.substring(0, cursorPos - 1));
						} else if (cursorPos > 1)
						{
							setText(this.text.substring(0, cursorPos - 1) + this.text.substring(cursorPos));
						} else if (cursorPos == 1)
						{
							setText(this.text.substring(1));
						}

						if (cursorPos > 0)
						{
							cursorPos--;
						}
					}
					break;
				case Keyboard.KEY_DELETE:
					if (this.text.length() > 0)
					{
						if (cursorPos == 0)
						{
							setText(this.text.substring(1));
						} else if (cursorPos > 0 && cursorPos < this.text.length())
						{
							setText(this.text.substring(0, cursorPos) + this.text.substring(cursorPos + 1));
						}
					}
					break;
				case Keyboard.KEY_SPACE:
					if (cursorPos <= this.text.length())
					{
						cursorPos++;
					}
					setText(this.text + " ");
					break;
				case Keyboard.KEY_LEFT:
					if (cursorPos > 0)
					{
						cursorPos--;
					}
					break;
				case Keyboard.KEY_RIGHT:
					if (cursorPos < this.text.length())
					{
						cursorPos++;
					}
					break;
				case Keyboard.KEY_HOME:
					cursorPos = 0;
					break;
				case Keyboard.KEY_END:
					cursorPos = this.text.length();
					break;
				default:
					break;
			}
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
		if (!s.equals(""))
		{
			String before = s.substring(0, cursorPos);
			String after = s.substring(cursorPos);
			return before + "|" + after;
		}
		return "|";
	}

	public void drawText()
	{
		drawString(fontRenderer, getDrawText(), x + 2, y + ySize / 2 - 4, 0xe0e0e0);
	}
}
