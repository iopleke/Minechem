package minechem.render;

import minechem.fluid.FluidChemical;
import minechem.fluid.FluidElement;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

public class FluidItemRenderingHandler implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		if (type == ItemRenderType.INVENTORY)
		{
			return true;
		}
		if (type == ItemRenderType.EQUIPPED)
		{
			return true;
		}
		if (type == ItemRenderType.ENTITY)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		if (helper == ItemRendererHelper.ENTITY_BOBBING)
		{
			return true;
		}
		if (helper == ItemRendererHelper.ENTITY_ROTATION)
		{
			return true;
		}
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
//		GL11.glEnable(GL11.GL_BLEND);
		Fluid fluid = ((BlockFluidBase) ((ItemBlock) item.getItem()).field_150939_a).getFluid();
		if (fluid instanceof FluidElement)
		{
			switch (((FluidElement) fluid).element.classification())
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
		} else if (fluid instanceof FluidChemical)
		{
			MoleculeEnum molecule = ((FluidChemical) fluid).molecule;
			GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
		}
		IIcon icon = ((ItemBlock) item.getItem()).field_150939_a.getBlockTextureFromSide(0);

		switch (type)
		{
			case INVENTORY:
				drawTexturedRectUV(0, 0, 0, 16, 16, icon);
				break;
			case EQUIPPED:
				ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
				break;
			case ENTITY:
				EntityItem entityItem = (EntityItem) data[1];
				if (entityItem.worldObj == null)
				{
					float angle = (Minecraft.getSystemTime() % 8000L) / 8000.0F * 360.0F;
					GL11.glPushMatrix();
					GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-0.2F, -0.5F, 0.0F);
					renderItemAsEntity(item, icon);
					GL11.glPopMatrix();
				} else
				{
					renderItemAsEntity(item, icon);
				}
				break;
			default:
				break;
		}
	}

	private void drawTexturedRectUV(float x, float y, float z, float w, float h, IIcon icon)
	{
		Tessellator tesselator = Tessellator.instance;
		tesselator.startDrawingQuads();
		tesselator.addVertexWithUV(x, y + h, z, icon.getMinU(), icon.getMaxV());
		tesselator.addVertexWithUV(x + w, y + h, z, icon.getMaxU(), icon.getMaxV());
		tesselator.addVertexWithUV(x + w, y, z, icon.getMaxU(), icon.getMinV());
		tesselator.addVertexWithUV(x, y, z, icon.getMinU(), icon.getMinV());
		tesselator.draw();
	}

	private void renderItemAsEntity(ItemStack itemStack, IIcon icon)
	{
		drawTextureIn3D(icon);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		drawTextureIn3D(icon);
	}

	private void drawTextureIn3D(IIcon texture)
	{
		Tessellator tesselator = Tessellator.instance;
		float scale = 0.7F;
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		ItemRenderer.renderItemIn2D(tesselator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), .05F);
		GL11.glPopMatrix();
	}
}
