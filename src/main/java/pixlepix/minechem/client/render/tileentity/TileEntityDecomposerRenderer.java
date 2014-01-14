package pixlepix.minechem.client.render.tileentity;

import pixlepix.minechem.common.tileentity.TileEntityDecomposer;
import pixlepix.minechem.common.utils.ConstantValue;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityDecomposerRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float var8) {
        if (tileEntity instanceof TileEntityDecomposer) {
            TileEntityDecomposer decomposer = (TileEntityDecomposer) tileEntity;
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glEnable(GL11.GL_LIGHTING);
            if (decomposer.isPowered()) {
            	bindTexture(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.DECOMPOSER_MODEL_ON));
                decomposer.model.updateWindillRotation(decomposer);
            } else {
            	bindTexture(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.DECOMPOSER_MODEL_OFF));
            }
            decomposer.model.render(0.0625F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

}
