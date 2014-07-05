package minechem.tileentity.decomposer;

import minechem.ModMinechem;
import minechem.tileentity.decomposer.DecomposerTileEntity.State;
import minechem.utils.Reference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

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
            GL11.glEnable(GL11.GL_LIGHTING);
            
            // When the decomposer is powered we will change the texture to reflect this.
            if (decomposer.isPowered() && decomposer.state != State.active)
            {
                bindTexture(new ResourceLocation(ModMinechem.ID, Reference.DECOMPOSER_MODEL_ON));
            }
            else if (decomposer.isPowered() && decomposer.state == State.active)
            {
                // Makes the machine spin and look active while it is actually decomposing items in the input slot.
                bindTexture(new ResourceLocation(ModMinechem.ID, Reference.DECOMPOSER_MODEL_ON));
                decomposer.model.updateWindillRotation(decomposer);
            }
            else if (!decomposer.isPowered())
            {
                // When the machine has no power we will reflect this with texture.
                bindTexture(new ResourceLocation(ModMinechem.ID, Reference.DECOMPOSER_MODEL_OFF));
            }
            else
            {
                // If we somehow enter another weird state just turn off.
                bindTexture(new ResourceLocation(ModMinechem.ID, Reference.DECOMPOSER_MODEL_OFF));
            }
            
            decomposer.model.render(0.0625F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

}
