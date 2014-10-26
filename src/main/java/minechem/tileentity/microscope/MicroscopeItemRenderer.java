package minechem.tileentity.microscope;

import cpw.mods.fml.client.FMLClientHandler;
import minechem.item.ItemMinechemRenderer;
import minechem.reference.Resources;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class MicroscopeItemRenderer extends ItemMinechemRenderer
{

	private MicroscopeModel microscopeModel;

	public MicroscopeItemRenderer()
	{
		microscopeModel = new MicroscopeModel();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		switch (type.ordinal())
		{
			case 0:
			{
				return true;
			}

			case 1:
			{
				return true;
			}

			case 2:
			{
				return true;
			}
			case 3:
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		if (helper == ItemRendererHelper.INVENTORY_BLOCK)
		{
			return true;
		}
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
		GL11.glPushMatrix();
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(Resources.Model.MICROSCOPE);
		switch (type.ordinal())
		{
			case 0:
			{

			}
			case 1:
			{

				GL11.glRotatef(2.0F, 0F, 0.0F, 0.0F);
				GL11.glTranslatef(0F, -0.5F, 0.5F);
			}
			case 2:
			{
				GL11.glTranslatef(0.0F, 0.5F, 0.0F);
				GL11.glRotatef(-4.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(2.0F, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(1F, 1F, 1F);
			}
			case 3:
			{
				GL11.glTranslatef(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180f, 0f, 0f, 1f);
			}

		}

		microscopeModel.render(0.0625F);
		GL11.glPopMatrix();
	}

}
