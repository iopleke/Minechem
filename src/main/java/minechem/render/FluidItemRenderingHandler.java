package minechem.render;

import minechem.fluid.FluidMolecule;
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
		switch(type)
		{
			case ENTITY:
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
			case INVENTORY:
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		switch (helper)
		{
			case ENTITY_BOBBING:
			case ENTITY_ROTATION:
				return true;
			default:
				return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		Fluid fluid = ((BlockFluidBase) ((ItemBlock) item.getItem()).field_150939_a).getFluid();
		if (fluid instanceof FluidElement)
		{
			RenderingUtil.setColorForElement(((FluidElement) fluid).element);
		} else if (fluid instanceof FluidMolecule)
		{
			MoleculeEnum molecule = ((FluidMolecule) fluid).molecule;
			GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
		}
		IIcon icon = ((ItemBlock) item.getItem()).field_150939_a.getBlockTextureFromSide(0);

		switch (type)
		{
			case INVENTORY:
				RenderingUtil.drawTexturedRectUV(0, 0, 0, 16, 16, icon);
				break;
			case EQUIPPED_FIRST_PERSON:
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

	private void renderItemAsEntity(ItemStack itemStack, IIcon icon)
	{
		RenderingUtil.drawTextureIn3D(icon);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderingUtil.drawTextureIn3D(icon);
	}
}
