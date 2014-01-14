package pixlepix.minechem.client.render.tileentity;

import pixlepix.minechem.common.tileentity.TileEntitySynthesis;
import pixlepix.minechem.common.utils.ConstantValue;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntitySynthesisRenderer extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        if (tileEntity instanceof TileEntitySynthesis) {
            TileEntitySynthesis synthesis = (TileEntitySynthesis) tileEntity;
            int facing = synthesis.getFacing();

            if (synthesis.getEnergyStored() > 100)
                synthesis.model.updateArm();

            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glRotatef(facing * 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            bindTexture(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.SYNTHESIS_MODEL));
            synthesis.model.render(0.0625F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

}
