package ljdp.minechem.client.render.tileentity;

import ljdp.minechem.client.ModelProjector;
import ljdp.minechem.common.tileentity.TileEntityBlueprintProjector;
import ljdp.minechem.common.utils.ConstantValue;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityBlueprintProjectorRenderer extends TileEntitySpecialRenderer {

    ModelProjector model;

    public TileEntityBlueprintProjectorRenderer() {
        this.model = new ModelProjector();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        if (tileEntity instanceof TileEntityBlueprintProjector) {
            TileEntityBlueprintProjector blueprintProjector = (TileEntityBlueprintProjector) tileEntity;
            int facing = blueprintProjector.getFacing();
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glRotatef((facing * 90.0F), 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
            if (blueprintProjector.hasBlueprint()) {

                bindTexture(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.PROJECTOR_MODEL_ON));
            } else {

                bindTexture(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.PROJECTOR_MODEL_OFF));
            }
            model.render(0.0625F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

}
