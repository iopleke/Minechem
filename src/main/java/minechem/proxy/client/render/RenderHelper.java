package minechem.proxy.client.render;

import minechem.helper.ColourHelper;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
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

    public static void drawTexturedRectUV(float x, float y, float z, int w, int h, IIcon icon)
    {
        Tessellator tesselator = Tessellator.instance;
        tesselator.startDrawingQuads();
        tesselator.addVertexWithUV(x, y + h, z, icon.getMinU(), icon.getMaxV());
        tesselator.addVertexWithUV(x + w, y + h, z, icon.getMaxU(), icon.getMaxV());
        tesselator.addVertexWithUV(x + w, y, z, icon.getMaxU(), icon.getMinV());
        tesselator.addVertexWithUV(x, y, z, icon.getMinU(), icon.getMinV());
        tesselator.draw();
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
        GL11.glTranslatef(-scale/2, -scale/2, 0.0F);
        ItemRenderer.renderItemIn2D(tessellator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), .0625F);
        GL11.glPopMatrix();
    }
}
