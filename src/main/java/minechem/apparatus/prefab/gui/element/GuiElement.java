package minechem.apparatus.prefab.gui.element;

import minechem.reference.Compendium;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public abstract class GuiElement extends Gui
{
    protected void bindTexture(ResourceLocation resource)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
    }
    
    protected FontRenderer getFontRenderer()
    {
        return Minecraft.getMinecraft().fontRenderer;
    }

    /**
     * Draws a textured rectangle at the current zLevel.
     * @param x x pos
     * @param y y pos
     * @param u u pos of texture
     * @param v v pos of texture
     * @param actualWidth width of texture
     * @param actualHeight height of texture
     * @param drawWidth width to draw on
     * @param drawHeight height to draw on
     */
    protected void drawTexturedModalRect(int x, int y, int u, int v, int actualWidth, int actualHeight, int drawWidth, int drawHeight)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) x, (double)(y + drawHeight), (double)this.zLevel, (double)((float) u * f), (double)((float)(v + actualHeight) * f1));
        tessellator.addVertexWithUV((double)(x + drawWidth), (double)(y + drawHeight), (double)this.zLevel, (double)((float)(u + actualWidth) * f), (double)((float)(v + actualHeight) * f1));
        tessellator.addVertexWithUV((double)(x + drawWidth), (double)(y), (double)this.zLevel, (double)((float)(u + actualWidth) * f), (double)((float)(v) * f1));
        tessellator.addVertexWithUV((double)(x), (double)(y), (double)this.zLevel, (double)((float)(u) * f), (double)((float)(v) * f1));
        tessellator.draw();
    }
}
