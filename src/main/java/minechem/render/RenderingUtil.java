package minechem.render;

import minechem.item.element.ElementEnum;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public final class RenderingUtil {
	
	public static void drawTextureIn3D(IIcon texture)
	{
		Tessellator tesselator = Tessellator.instance;
		float scale = 0.7F;
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		ItemRenderer.renderItemIn2D(tesselator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), .0625F);
		GL11.glPopMatrix();
	}

	public static void drawTexturedRectUV(ItemRenderType type, float x, float y, float z, float w, float h, IIcon icon)
	{
		Tessellator tesselator = Tessellator.instance;
		tesselator.startDrawingQuads();
		tesselator.addVertexWithUV(x, y + h, z, icon.getMinU(), icon.getMaxV());
		tesselator.addVertexWithUV(x + w, y + h, z, icon.getMaxU(), icon.getMaxV());
		tesselator.addVertexWithUV(x + w, y, z, icon.getMaxU(), icon.getMinV());
		tesselator.addVertexWithUV(x, y, z, icon.getMinU(), icon.getMinV());
		tesselator.draw();
	}
	
	public static void setColorForElement(ElementEnum element)
	{
		switch (element.classification())
		{
			case actinide:
				GL11.glColor3f(1.0F, 0.0F, 0.0F);
				break;
			case alkaliMetal:
				GL11.glColor3f(0.0F, 1.0F, 0.0F);
				break;
			case alkalineEarthMetal:
				GL11.glColor3f(0.0F, 0.0F, 1.0F);
				break;
			case halogen:
				GL11.glColor3f(1.0F, 1.0F, 0.0F);
				break;
			case inertGas:
				GL11.glColor3f(0.0F, 1.0F, 1.0F);
				break;
			case lanthanide:
				GL11.glColor3f(1.0F, 0.0F, 1.0F);
				break;
			case nonmetal:
				GL11.glColor3f(1.0F, 0.5F, 0.0F);
				break;
			case otherMetal:
				GL11.glColor3f(0.5F, 1.0F, 0.0F);
				break;
			case semimetallic:
				GL11.glColor3f(0.0F, 1.0F, 0.5F);
				break;
			case transitionMetal:
				GL11.glColor3f(0.0F, 0.5F, 1.0F);
				break;
			default:
				break;
		}
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


}
