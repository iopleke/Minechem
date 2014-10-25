package minechem.tileentity.decomposer;

import minechem.reference.Reference;
import minechem.reference.Textures;
import minechem.tileentity.decomposer.DecomposerTileEntity.State;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DecomposerTileEntityRenderer extends TileEntitySpecialRenderer
{

	private static final ResourceLocation resourceLocationDecomposerModelOff = new ResourceLocation(Reference.ID, Textures.DECOMPOSER_MODEL_OFF);
	private static final ResourceLocation resourceLocationDecomposerModelOn = new ResourceLocation(Reference.ID, Textures.DECOMPOSER_MODEL_ON);

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float var8)
	{
		if (tileEntity instanceof DecomposerTileEntity)
		{
			DecomposerTileEntity decomposer = (DecomposerTileEntity) tileEntity;
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
			GL11.glRotatef(180f, 0f, 0f, 1f);

			// When the decomposer is powered we will change the texture to reflect this.
			if (decomposer.state != State.active)
			{
				bindTexture(resourceLocationDecomposerModelOn);
			} else if (decomposer.state == State.active)
			{
				// Makes the machine spin and look active while it is actually decomposing items in the input slot.
				bindTexture(resourceLocationDecomposerModelOn);
				decomposer.model.updateWindillRotation(decomposer);
			} else
			{
				// If we somehow enter another weird state just turn off.
				bindTexture(resourceLocationDecomposerModelOff);
			}

			decomposer.model.render(0.0625F);
			GL11.glPopMatrix();
		}
	}

}
