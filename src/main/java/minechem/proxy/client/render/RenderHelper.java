package minechem.proxy.client.render;

import minechem.helper.ColourHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Holds short hands for common used {@link org.lwjgl.opengl.GL11} methods
 */
public class RenderHelper extends net.minecraft.client.renderer.RenderHelper
{
    /**
     * Executes GL11.glColor4f() for given int colour
     * @param colour in int form
     */
    public static void setOpenGLColour(int colour)
    {
        GL11.glColor4f(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour), ColourHelper.getAlpha(colour));
    }

    /**
     * Executes GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
     */
    public static void resetOpenGLColour()
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Executes GL11.glColor4f(greyscale, greyscale, greyscale, 1.0F)
     * @param greyscale the greyscale in float form where 0.0F is black and 1.0F is white
     */
    public static void setGreyscaleOpenGLColour(float greyscale)
    {
        GL11.glColor4f(greyscale, greyscale, greyscale, 1.0F);
    }

    /**
     * Enables GL11.GL_BLEND
     */
    public static void enableBlend()
    {
        GL11.glEnable(GL11.GL_BLEND);        
    }

    /**
     * Disables GL11.GL_BLEND
     */
    public static void disableBlend()
    {
        GL11.glDisable(GL11.GL_BLEND);
    }

    /**
     * Executes GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity)
     * Used in combination with blend
     * @param opacity the opacity in float form where 1.0F is opaque and 0.0F is transparent
     */
    public static void setOpacity(float opacity)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity);
    }

    /**
     * Executes GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F) back to full opaque
     */
    public static void resetOpacity()
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     *  Draws a {@link net.minecraft.util.IIcon} on given x, y, z
     * @param x xPos
     * @param y yPos
     * @param z zPos
     * @param w icon width
     * @param h icon height
     * @param icon the {@link net.minecraft.util.IIcon}
     */
    public static void drawTexturedRectUV(float x, float y, float z, int w, int h, IIcon icon)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + h, z, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + w, y + h, z, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + w, y, z, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(x, y, z, icon.getMinU(), icon.getMinV());
        tessellator.draw();
    }

    /**
     * Draw a {@link net.minecraft.util.ResourceLocation} on given coords
     * @param x xPos
     * @param y yPos
     * @param z zPos
     * @param u uPos on the {@link net.minecraft.util.ResourceLocation}
     * @param v vPos on the {@link net.minecraft.util.ResourceLocation}
     * @param w width
     * @param h height
     * @param resource the {@link net.minecraft.util.ResourceLocation}
     */
    public static void drawTexturedRectUV(float x, float y, float z, float u, float v, float w, float h, ResourceLocation resource)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        float textScale = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + h, z, u * textScale, (v + h) * textScale);
        tessellator.addVertexWithUV(x + w, y + h, z, (u + w)  * textScale, (v + h) * textScale);
        tessellator.addVertexWithUV(x + w, y, z, (u + w) * textScale, y * textScale);
        tessellator.addVertexWithUV(x, y, z, u * textScale, v * textScale);
        tessellator.draw();
    }

    /**
     * Draw a {@link net.minecraft.util.ResourceLocation} on given coords with given scaling
     * @param x xPos
     * @param y yPos
     * @param z zPos
     * @param u uPos on the {@link net.minecraft.util.ResourceLocation}
     * @param v vPos on the {@link net.minecraft.util.ResourceLocation}
     * @param w width
     * @param h height
     * @param scale the scale to draw 1.0F is exact, less is smaller and bigger is larger
     * @param resource the {@link net.minecraft.util.ResourceLocation}
     */
    public static void drawScaledTexturedRectUV(float x, float y, float z, float u, float v, float w, float h, float scale, ResourceLocation resource)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        float drawH = h * scale;
        float drawW = w * scale;
        float xScale = 1.0F / w;
        float yScale = 1.0F / h;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + drawH, z, u * xScale, (v + h) * yScale);
        tessellator.addVertexWithUV(x + drawW, y + drawH, z, (u + w)  * xScale, (v + h) * yScale);
        tessellator.addVertexWithUV(x + drawW, y, z, (u + w) * xScale, v * yScale);
        tessellator.addVertexWithUV(x, y, z, u * xScale, v * yScale);
        tessellator.draw();
    }

    /**
     * Draw a {@link net.minecraft.util.ResourceLocation} on given coords with given draw width and draw height
     * @param x xPos
     * @param y yPos
     * @param z zPos
     * @param u uPos on the {@link net.minecraft.util.ResourceLocation}
     * @param v vPos on the {@link net.minecraft.util.ResourceLocation}
     * @param w actual width
     * @param h actual height
     * @param drawW the draw width
     * @param drawH the draw height
     * @param resource the {@link net.minecraft.util.ResourceLocation}
     */
    public static void drawTexturedRectUV(float x, float y, float z, float u, float v, float w, float h, float drawW, float drawH, ResourceLocation resource)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        float xScale = 1.0F / w;
        float yScale = 1.0F / h;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + drawH, z, u * xScale, (v + h) * yScale);
        tessellator.addVertexWithUV(x + drawW, y + drawH, z, (u + w)  * xScale, (v + h) * yScale);
        tessellator.addVertexWithUV(x + drawW, y, z, (u + w) * xScale, v * yScale);
        tessellator.addVertexWithUV(x, y, z, u * xScale, v * yScale);
        tessellator.draw();
    }

    /**
     * Draw a texture in 2D using {@link net.minecraft.client.renderer.ItemRenderer#renderItemIn2D(net.minecraft.client.renderer.Tessellator, float, float, float, float, int, int, float)}
     * @param texture the {@link net.minecraft.util.IIcon}
     */
    public static void drawTextureIn2D(IIcon texture)
    {
        Tessellator tessellator = Tessellator.instance;
        ItemRenderer.renderItemIn2D(tessellator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), 0.0625F);
    }

    /**
     * Draws a texture in 3D using {@link net.minecraft.client.renderer.ItemRenderer#renderItemIn2D(net.minecraft.client.renderer.Tessellator, float, float, float, float, int, int, float)}
     * @param texture the {@link net.minecraft.util.IIcon}
     */
    public static void drawTextureIn3D(IIcon texture)
    {
        Tessellator tessellator = Tessellator.instance;
        float scale = 0.7F;
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(-scale / 2, -scale / 2, 0.0F);
        ItemRenderer.renderItemIn2D(tessellator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), .0625F);
        GL11.glPopMatrix();
    }

    /**
     * Start a GL_SCISSOR_TEST
     * @param guiWidth {@link net.minecraft.client.gui.GuiScreen#width}
     * @param guiHeight {@link net.minecraft.client.gui.GuiScreen#height}
     * @param x xPos to start scissor
     * @param y yPos to start scissor
     * @param w width of the scissor
     * @param h height of the scissor
     */
    public static void setScissor(int guiWidth, int guiHeight, float x, float y, int w, int h)
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int scale = scaledRes.getScaleFactor();
        x *= scale;
        y *= scale;
        w *= scale;
        h *= scale;
        float guiScaledWidth = (guiWidth * scale);
        float guiScaledHeight = (guiHeight * scale);
        float guiLeft = ((mc.displayWidth / 2) - guiScaledWidth / 2);
        float guiTop = ((mc.displayHeight / 2) + guiScaledHeight / 2);
        int scissorX = Math.round((guiLeft + x));
        int scissorY = Math.round(((guiTop - h) - y));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scissorX, scissorY, w, h);
    }

    /**
     *  Stop a GL_SCISSOR_TEST
     */
    public static void stopScissor()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
