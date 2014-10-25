package minechem.tileentity.microscope;

import minechem.reference.Reference;
import minechem.reference.Textures;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MicroscopeTileEntityRenderer extends TileEntitySpecialRenderer
{

	private static final ResourceLocation resourceLocationMicroscopeModel = new ResourceLocation(Reference.ID, Textures.MICROSCOPE_MODEL);
	MicroscopeModel microscopeModel;

	public MicroscopeTileEntityRenderer()
	{
		microscopeModel = new MicroscopeModel();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float var8)
	{
		if (tileEntity instanceof MicroscopeTileEntity)
		{
			int facing = ((MicroscopeTileEntity) tileEntity).getFacing();
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
			GL11.glRotatef(180f, 0f, 0f, 1f);
			GL11.glRotatef((facing * 90.0F), 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			bindTexture(resourceLocationMicroscopeModel);
			microscopeModel.render(0.0625F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}

}
