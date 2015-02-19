package minechem.proxy.client.font;

import minechem.Compendium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;

public class Font
{
    private EnhancedFontRenderer fontRenderer;

    public Font(FontRenderer fontRenderer)
    {
        this(fontRenderer.FONT_HEIGHT, fontRenderer.getUnicodeFlag());
    }

    public Font(boolean unicode, int size, float zLevel)
    {
        Minecraft mc = Minecraft.getMinecraft();
        fontRenderer = new EnhancedFontRenderer(mc.gameSettings, Compendium.Resource.Font.vanilla, mc.getTextureManager(), unicode);
        fontRenderer.setZLevel(zLevel).setFontSize(size);
        ((IReloadableResourceManager) mc.getResourceManager()).registerReloadListener(fontRenderer);
    }

    public Font(boolean unicode)
    {
        this(unicode, 9, 0);
    }

    public Font(int fontSize, boolean unicode)
    {
        this(unicode, fontSize, 0);
    }

    public Font(float zLevel)
    {
        this(false, 9, zLevel);
    }

    public Font(int fontSize)
    {
        this(false, fontSize, 0);
    }

    public Font(int fontSize, float zLevel)
    {
        this(false, fontSize, zLevel);
    }

    public float getCurrentZLevel()
    {
        return this.fontRenderer.getZLevel();
    }

    public int getCurrentFontSize()
    {
        return this.fontRenderer.getFontSize();
    }

    public Font setZLevel(float zLevel)
    {
        this.fontRenderer.setZLevel(zLevel);
        return this;
    }

    public Font incZLevel(float inc)
    {
        this.fontRenderer.incZLevel(inc);
        return this;
    }

    public Font decZLevel(float dec)
    {
        this.fontRenderer.decZLevel(dec);
        return this;
    }

    public Font incZLevel()
    {
        this.fontRenderer.incZLevel();
        return this;
    }

    public Font decZLevel()
    {
        this.fontRenderer.decZLevel();
        return this;
    }

    public Font setFontSize(int size)
    {
        this.fontRenderer.setFontSize(size);
        return this;
    }

    public Font incFontSize(int inc)
    {
        this.fontRenderer.incFontSize(inc);
        return this;
    }

    public Font decFontSize(int dec)
    {
        this.fontRenderer.decFontSize(dec);
        return this;
    }

    public Font incFontSize()
    {
        this.fontRenderer.incFontSize();
        return this;
    }

    public Font decFontSize()
    {
        this.fontRenderer.decFontSize();
        return this;
    }

    public void print(Object o, int x, int y)
    {
        fontRenderer.drawString(String.valueOf(o), x, y, 8, false);
    }

    public void print(Object o, int x, int y, int color)
    {
        fontRenderer.drawString(String.valueOf(o), x, y, color, false);
    }

    public void print(Object o, int x, int y, int color, boolean shadow)
    {
        fontRenderer.drawString(String.valueOf(o), x, y, color, shadow);
    }

    public void printWithZ(Object o, int x, int y, int z)
    {
        float prevZ = fontRenderer.getZLevel();
        fontRenderer.setZLevel(z);
        fontRenderer.drawString(String.valueOf(o), x, y, 8, false);
        fontRenderer.setZLevel(prevZ);
    }

    public void printWithZ(Object o, int x, int y, int z, int color)
    {
        float prevZ = fontRenderer.getZLevel();
        fontRenderer.setZLevel(z);
        fontRenderer.drawString(String.valueOf(o), x, y, color, false);
        fontRenderer.setZLevel(prevZ);
    }

    public void printWithZ(Object o, int x, int y, int z, int color, boolean shadow)
    {
        float prevZ = fontRenderer.getZLevel();
        fontRenderer.setZLevel(z);
        fontRenderer.drawString(String.valueOf(o), x, y, color, shadow);
        fontRenderer.setZLevel(prevZ);
    }

    public void printWithSize(Object o, int x, int y, int size)
    {
        int prevFontSize = fontRenderer.getFontSize();
        fontRenderer.setFontSize(size);
        fontRenderer.drawString(String.valueOf(o), x, y, 8, false);
        fontRenderer.setFontSize(prevFontSize);
    }

    public void printWithSize(Object o, int x, int y, int size, int color)
    {
        int prevFontSize = fontRenderer.getFontSize();
        fontRenderer.setFontSize(size);
        fontRenderer.drawString(String.valueOf(o), x, y, color, false);
        fontRenderer.setFontSize(prevFontSize);
    }

    public void printWithSize(Object o, int x, int y, int size, int color, boolean shadow)
    {
        int prevFontSize = fontRenderer.getFontSize();
        fontRenderer.setFontSize(size);
        fontRenderer.drawString(String.valueOf(o), x, y, color, shadow);
        fontRenderer.setFontSize(prevFontSize);
    }

    public void printWithSizeAndZ(Object o, int x, int y, int z, int size)
    {
        float prevZ = fontRenderer.getZLevel();
        int prevFontSize = fontRenderer.getFontSize();
        fontRenderer.setZLevel(z);
        fontRenderer.setFontSize(size);
        fontRenderer.drawString(String.valueOf(o), x, y, 8, false);
        fontRenderer.setZLevel(prevZ);
        fontRenderer.setFontSize(prevFontSize);
    }

    public void printWithSizeAndZ(Object o, int x, int y, int z, int size, int color)
    {
        float prevZ = fontRenderer.getZLevel();
        int prevFontSize = fontRenderer.getFontSize();
        fontRenderer.setZLevel(z);
        fontRenderer.setFontSize(size);
        fontRenderer.drawString(String.valueOf(o), x, y, color, false);
        fontRenderer.setZLevel(prevZ);
        fontRenderer.setFontSize(prevFontSize);
    }

    public void printWithSizeAndZ(Object o, int x, int y, int z, int size, int color, boolean shadow)
    {
        float prevZ = fontRenderer.getZLevel();
        int prevFontSize = fontRenderer.getFontSize();
        fontRenderer.setZLevel(z);
        fontRenderer.setFontSize(size);
        fontRenderer.drawString(String.valueOf(o), x, y, color, shadow);
        fontRenderer.setZLevel(prevZ);
        fontRenderer.setFontSize(prevFontSize);
    }

    public EnhancedFontRenderer getFontRenderer()
    {
        return this.fontRenderer;
    }
}
