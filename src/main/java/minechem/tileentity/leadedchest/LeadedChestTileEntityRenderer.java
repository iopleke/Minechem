package minechem.tileentity.leadedchest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.reference.Resources;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class LeadedChestTileEntityRenderer extends TileEntitySpecialRenderer
{
	private final ModelChest leadedChestModel = new ModelChest();

	public LeadedChestTileEntityRenderer()
	{

	}

	public void renderTileEntityLeadedChestAt(LeadedChestTileEntity leadedChest, double xCoord, double yCoord, double zCoord, float partialTick)
	{
		if (leadedChest == null)
		{
			return;
		}
		int facing = 0;

		if (leadedChest.hasWorldObj())
		{
			Block var10 = leadedChest.getBlockType();
			facing = leadedChest.getBlockMetadata();

			if (var10 != null && facing == 0)
			{
				facing = leadedChest.getBlockMetadata();
			}
		} else
		{
			facing = 0;
		}

		ModelChest var14 = this.leadedChestModel;

		bindTexture(Resources.Model.LEADED_CHEST);

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) xCoord, (float) yCoord + 1.0F, (float) zCoord + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short var11 = 0;

		if (facing == 2)
		{
			var11 = 180;
		}

		if (facing == 3)
		{
			var11 = 0;
		}

		if (facing == 4)
		{
			var11 = 90;
		}

		if (facing == 5)
		{
			var11 = -90;
		}

		GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.chestLid.rotateAngleX = 0;
		var14.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double xCoord, double yCoord, double zCoord, float partialTick)
	{
		this.renderTileEntityLeadedChestAt((LeadedChestTileEntity) tileentity, xCoord, yCoord, zCoord, partialTick);
	}
}
