package minechem.tileentity.decomposer;

import minechem.reference.Resources;
import minechem.tileentity.decomposer.DecomposerTileEntity.State;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class DecomposerTileEntityRenderer extends TileEntitySpecialRenderer
{
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
                bindTexture(Resources.Model.DECOMPOSER_ON);
            } else if (decomposer.state == State.active)
            {
                // Makes the machine spin and look active while it is actually decomposing items in the input slot.
                bindTexture(Resources.Model.DECOMPOSER_ON);
                decomposer.model.updateWindillRotation(decomposer);
            } else
            {
                // If we somehow enter another weird state just turn off.
                bindTexture(Resources.Model.DECOMPOSER_OFF);
            }

            decomposer.model.render(0.0625F);
            GL11.glPopMatrix();
        }
    }

}
