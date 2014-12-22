package minechem.item.molecule;

import minechem.render.RenderingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class MoleculeItemRenderer implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		switch (type)
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
	public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);

		MoleculeItem item = (MoleculeItem) itemstack.getItem();
		IIcon testtube = itemstack.getIconIndex();
		switch (type)
		{
			case INVENTORY:
				renderItemInInventory(type, itemstack, item.filledMolecule, item.render_pass1, item.render_pass2);
				break;
			case EQUIPPED_FIRST_PERSON:
			case EQUIPPED:
				renderItemInEquipped(type, itemstack, testtube, item.render_pass1, item.render_pass2);
				break;
			case ENTITY:
				EntityItem entityItem = (EntityItem) data[1];
				if (entityItem.worldObj == null)
				{
					float angle = (Minecraft.getSystemTime() % 8000L) / 8000.0F * 360.0F;
					GL11.glPushMatrix();
					GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-0.2F, -0.5F, 0.0F);
					renderItemAsEntity(type, itemstack, testtube, item.render_pass1, item.render_pass2);
					GL11.glPopMatrix();
				} else
				{
					renderItemAsEntity(type, itemstack, testtube, item.render_pass1, item.render_pass2);
				}
				break;
			default:
				break;
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private void renderItemInInventory(ItemRenderType type, ItemStack itemstack, IIcon testtube, IIcon pass1, IIcon pass2)
	{
		MoleculeEnum molecule = MoleculeItem.getMolecule(itemstack);
		GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
		RenderingUtil.drawTexturedRectUV(type, 0, 0, 5, 16, 16, pass1);
		GL11.glColor3f(molecule.red2, molecule.green2, molecule.blue2);
		RenderingUtil.drawTexturedRectUV(type, 0, 0, 5, 16, 16, pass2);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderingUtil.drawTexturedRectUV(type, 0, 0, 5, 16, 16, testtube);
	}

	private void renderItemInEquipped(ItemRenderType type, ItemStack itemstack, IIcon testtube, IIcon pass1, IIcon pass2)
	{
		MoleculeEnum molecule = MoleculeItem.getMolecule(itemstack);
		/* float scale = 0.75F; GL11.glPushMatrix(); GL11.glScalef(scale, scale, scale); GL11.glTranslatef(1.2F, 1.1F, -0.25F); GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F); GL11.glColor3f(molecules.red, molecules.green, molecules.blue); drawTexturedRectUV(type,
		 * 0, 0, 0, 16, 16, pass1); GL11.glColor3f(molecules.red2, molecules.green2, molecules.blue2); drawTexturedRectUV(type, 0, 0, 0, 16, 16, pass2); GL11.glColor3f(1.0F, 1.0F, 1.0F); GL11.glTranslatef(0.0F, 0.0F, -0.001F); for (float i = 0.0F; i < .1F; i
		 * += .01F) { drawTexturedRectUV(type, 0, 0, i, 16, 16, testtube); } GL11.glPopMatrix(); */
		Tessellator tessellator = Tessellator.instance;
		GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
		ItemRenderer.renderItemIn2D(tessellator, pass1.getMaxU(), pass1.getMinV(), pass1.getMinU(), pass1.getMaxV(), pass1.getIconWidth(), pass1.getIconHeight(), 0.0625F);
		GL11.glColor3f(molecule.red2, molecule.green2, molecule.blue2);
		ItemRenderer.renderItemIn2D(tessellator, pass2.getMaxU(), pass2.getMinV(), pass2.getMinU(), pass2.getMaxV(), pass2.getIconWidth(), pass2.getIconHeight(), 0.0625F);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		ItemRenderer.renderItemIn2D(tessellator, testtube.getMaxU(), testtube.getMinV(), testtube.getMinU(), testtube.getMaxV(), testtube.getIconWidth(), testtube.getIconHeight(), 0.0625F);
	}

	private void renderItemAsEntity(ItemRenderType type, ItemStack itemstack, IIcon testtube, IIcon pass1, IIcon pass2)
	{
		MoleculeEnum molecule = MoleculeItem.getMolecule(itemstack);
		GL11.glPushMatrix();
		GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
		RenderingUtil.drawTextureIn3D(pass1);
		GL11.glColor3f(molecule.red2, molecule.green2, molecule.blue2);
		RenderingUtil.drawTextureIn3D(pass2);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderingUtil.drawTextureIn3D(testtube);
		GL11.glPopMatrix();
	}
}
