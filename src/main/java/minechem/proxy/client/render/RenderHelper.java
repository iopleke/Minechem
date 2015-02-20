package minechem.proxy.client.render;

import minechem.helper.ColourHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHelper
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
        float textScale = 0.00390625F;
        float drawH = h * scale;
        float drawW = w * scale;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + drawH, z, u * textScale, (v + h) * textScale);
        tessellator.addVertexWithUV(x + drawW, y + drawH, z, (u + w)  * textScale, (v + h) * textScale);
        tessellator.addVertexWithUV(x + drawW, y, z, (u + w) * textScale, v * textScale);
        tessellator.addVertexWithUV(x, y, z, u * textScale, v * textScale);
        tessellator.draw();
    }

    public static void drawTexturedRectUV(float x, float y, float z, float u, float v, float w, float h, float drawW, float drawH, ResourceLocation resource)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
        float textScale = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + drawH, z, u * textScale, (v + h) * textScale);
        tessellator.addVertexWithUV(x + drawW, y + drawH, z, (u + w)  * textScale, (v + h) * textScale);
        tessellator.addVertexWithUV(x + drawW, y, z, (u + w) * textScale, v * textScale);
        tessellator.addVertexWithUV(x, y, z, u * textScale, v * textScale);
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
}
