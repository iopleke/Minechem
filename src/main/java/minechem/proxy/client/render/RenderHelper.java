package minechem.proxy.client.render;

import minechem.helper.ColourHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHelper extends net.minecraft.client.renderer.RenderHelper
{
    public static void setOpenGLColour(int colour)
    {
        GL11.glColor4f(ColourHelper.getRed(colour), ColourHelper.getGreen(colour), ColourHelper.getBlue(colour), ColourHelper.getAlpha(colour));
    }

    public static void resetOpenGLColour()
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void setGreyscaleOpenGLColour(float greyscale)
    {
        GL11.glColor4f(greyscale, greyscale, greyscale, 1.0F);
    }
    
    public static void enableBlend()
    {
        GL11.glEnable(GL11.GL_BLEND);        
    }

    public static void disableBlend()
    {
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void setOpacity(float opacity)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, opacity);
    }

    public static void resetOpacity()
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

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

    public static void drawTextureIn2D(IIcon texture)
    {
        Tessellator tessellator = Tessellator.instance;
        ItemRenderer.renderItemIn2D(tessellator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), 0.0625F);
    }

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

    public static void stopScissor()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
