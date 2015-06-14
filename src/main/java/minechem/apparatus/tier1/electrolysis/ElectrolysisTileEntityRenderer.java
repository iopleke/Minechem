package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.renderer.BasicTileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class ElectrolysisTileEntityRenderer extends BasicTileEntityRenderer
{
    ElectrolysisModel model;

    public ElectrolysisTileEntityRenderer()
    {
        super(0.4F, 0.0625F);

        setOffset(0.5D, 0.6D, 0.5D);

        model = new ElectrolysisModel();
        texture = Compendium.Resource.Model.electrolysis;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale)
    {
        if (tileEntity instanceof ElectrolysisTileEntity)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(x + xOffset, y + yOffset, z + zOffset);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glRotatef((tileEntity.getBlockMetadata() * 90.0F), 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glScaled(xScale, yScale, zScale);
            bindTexture(texture);
            model.setLeftTube(((ElectrolysisTileEntity) tileEntity).getLeftTube() != null);
            model.setRightTube(((ElectrolysisTileEntity) tileEntity).getRightTube() != null);
            model.render(rotation);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

}
