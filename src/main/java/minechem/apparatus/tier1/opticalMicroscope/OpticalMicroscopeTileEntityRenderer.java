package minechem.apparatus.tier1.opticalMicroscope;

import minechem.reference.Compendium;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class OpticalMicroscopeTileEntityRenderer extends TileEntitySpecialRenderer
{
    OpticalMicroscopeModel opticalMicroscopeModel;

    public OpticalMicroscopeTileEntityRenderer()
    {
        opticalMicroscopeModel = new OpticalMicroscopeModel();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float var8)
    {
        if (tileEntity instanceof OpticalMicroscopeTileEntity)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glRotatef((tileEntity.blockMetadata * 90.0F), 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            bindTexture(Compendium.Resource.Model.microscope);
            opticalMicroscopeModel.render(0.0625F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

}
