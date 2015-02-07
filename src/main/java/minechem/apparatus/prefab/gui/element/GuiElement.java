package minechem.apparatus.prefab.gui.element;

import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

/**
 * Base class for GuiElements Holds methods that can be useful when making GuiElements
 *
 * @author way2muchnoise
 */
public abstract class GuiElement extends Gui
{
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;

    public GuiElement(int posX, int posY, int width, int height)
    {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(int guiLeft, int guiTop);

    /**
     * Shorthand for binding a Resource
     *
     * @param resource the ResourceLocation
     */
    protected void bindTexture(ResourceLocation resource)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
    }

    /**
     * Shorthand for getting the {@link net.minecraft.client.Minecraft} {@link net.minecraft.client.gui.FontRenderer}
     *
     * @return the main MinecraftFontRenderer
     */
    protected FontRenderer getFontRenderer()
    {
        return Minecraft.getMinecraft().fontRenderer;
    }

    /**
     * Draws a textured rectangle at the current zLevel.
     *
     * @param x            x pos
     * @param y            y pos
     * @param u            u pos of texture
     * @param v            v pos of texture
     * @param actualWidth  width of texture
     * @param actualHeight height of texture
     * @param drawWidth    width to draw on
     * @param drawHeight   height to draw on
     */
    protected void drawTexturedModalRect(int x, int y, int u, int v, int actualWidth, int actualHeight, int drawWidth, int drawHeight)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) x, (double) (y + drawHeight), (double) this.zLevel, (double) ((float) u * f), (double) ((float) (v + actualHeight) * f1));
        tessellator.addVertexWithUV((double) (x + drawWidth), (double) (y + drawHeight), (double) this.zLevel, (double) ((float) (u + actualWidth) * f), (double) ((float) (v + actualHeight) * f1));
        tessellator.addVertexWithUV((double) (x + drawWidth), (double) (y), (double) this.zLevel, (double) ((float) (u + actualWidth) * f), (double) ((float) (v) * f1));
        tessellator.addVertexWithUV((double) (x), (double) (y), (double) this.zLevel, (double) ((float) (u) * f), (double) ((float) (v) * f1));
        tessellator.draw();
    }

    protected void drawHoveringText(List<String> tooltip, int x, int y, FontRenderer fontrenderer)
    {
        if (!tooltip.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
    
            for (String line : tooltip)
            {
                int l = fontrenderer.getStringWidth(line);
    
                if (l > k)
                {
                    k = l;
                }
            }
    
            int i1 = x + 12;
            int j1 = y - 12;
            int k1 = 8;
    
            if (tooltip.size() > 1)
            {
                k1 += 2 + (tooltip.size() - 1) * 10;
            }
    
            this.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
    
            for (int k2 = 0; k2 < tooltip.size(); ++k2)
            {
                String s1 = tooltip.get(k2);
                fontrenderer.drawStringWithShadow(s1, i1, j1, -1);
    
                if (k2 == 0)
                {
                    j1 += 2;
                }
    
                j1 += 10;
            }
    
            this.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }
}
