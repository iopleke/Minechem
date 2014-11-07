package minechem.item.element;

import minechem.item.ChemicalRoomStateEnum;
import minechem.render.RenderingUtil;
import minechem.utils.MinechemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ElementItemRenderer implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		if (item.getItemDamage() == ElementEnum.heaviestMass)
		{
			return false;
		}

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

		ElementItem item = (ElementItem) itemstack.getItem();
		IIcon testtube = itemstack.getIconIndex();
		IIcon contentsTex = null;
		ElementEnum element = ElementItem.getElement(itemstack);
		float duration = 1500;
		float t = (int) (Minecraft.getSystemTime() % duration);
		int frame = (int) MinechemHelper.translateValue(t, 0, duration, 0, 7);
		if (element.roomState() == ChemicalRoomStateEnum.gas)
		{
			contentsTex = item.gas[frame];
		} else if (element.roomState() == ChemicalRoomStateEnum.liquid)
		{
			contentsTex = item.liquid[frame];
		} else
		{
			contentsTex = item.solid;
		}
		switch (type)
		{
			case INVENTORY:
				renderItemInInventory(itemstack, element, testtube, contentsTex);
				break;
			case EQUIPPED_FIRST_PERSON:
			case EQUIPPED:
				renderItemInEquipped(itemstack, element, testtube, contentsTex);
				break;
			case ENTITY:
				EntityItem entityItem = (EntityItem) data[1];
				if (entityItem.worldObj == null)
				{
					float angle = (Minecraft.getSystemTime() % 8000L) / 8000.0F * 360.0F;
					GL11.glPushMatrix();
					GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(-0.2F, -0.5F, 0.0F);
					renderItemAsEntity(itemstack, element, testtube, contentsTex);
					GL11.glPopMatrix();
				} else
				{
					renderItemAsEntity(itemstack, element, testtube, contentsTex);
				}
				break;
			default:
				break;
		}

	}

	private void renderItemInInventory(ItemStack itemstack, ElementEnum element, IIcon testtube, IIcon contents)
	{
		String shortName = ElementItem.getShortName(itemstack);
		RenderingUtil.setColorForElement(element);
		RenderingUtil.drawTexturedRectUV(0, 0, 0, 16, 16, contents);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderingUtil.drawTexturedRectUV(0, 0, 0, 16, 16, testtube);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		fontRenderer.drawString(shortName, 1, 2, 0x000000);
		fontRenderer.drawString(shortName, 1, 1, 0xEEEEEE);
	}

	private void renderItemInEquipped(ItemStack itemstack, ElementEnum element, IIcon testtube, IIcon contents)
	{
		/* float scale = .06F; GL11.glPushMatrix(); GL11.glScalef(scale, scale, scale); GL11.glTranslatef(20.0F, 15.0F, 0.0F); GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F); GL11.glColor3f(1.0F, 1.0F, 1.0F); setColorForElement(element); drawTexturedRectUV(0, 0,
		 * 0, 16, 16, contents); GL11.glColor3f(1.0F, 1.0F, 1.0F); for (float i = 0.0F; i < 1.0F; i += .1F) { drawTexturedRectUV(0, 0, i, 16, 16, testtube); } GL11.glPopMatrix(); */

		Tessellator tessellator = Tessellator.instance;
		RenderingUtil.setColorForElement(element);
		ItemRenderer.renderItemIn2D(tessellator, contents.getMaxU(), contents.getMinV(), contents.getMinU(), contents.getMaxV(), contents.getIconWidth(), contents.getIconHeight(), 0.0625F);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		ItemRenderer.renderItemIn2D(tessellator, testtube.getMaxU(), testtube.getMinV(), testtube.getMinU(), testtube.getMaxV(), testtube.getIconWidth(), testtube.getIconHeight(), 0.0625F);
	}

	private void renderItemAsEntity(ItemStack itemstack, ElementEnum element, IIcon testtube, IIcon contents)
	{
		GL11.glPushMatrix();
		RenderingUtil.setColorForElement(element);
		RenderingUtil.drawTextureIn3D(contents);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderingUtil.drawTextureIn3D(testtube);
		GL11.glPopMatrix();
	}

}
