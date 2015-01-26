package minechem.apparatus.prefab.renderer;

import minechem.apparatus.prefab.model.BasicModel;
import minechem.apparatus.prefab.tileEntity.BasicTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class BasicTileEntityRenderer extends TileEntitySpecialRenderer
{
    protected BasicModel model;
    protected float rotation;
    protected ResourceLocation texture;

    protected float xScale;
    protected double yOffset;
    protected float yScale;
    protected float zScale;

    public BasicTileEntityRenderer()
    {
        setScale(1.0F);
    }

    public BasicTileEntityRenderer(float scale)
    {
        setScale(scale);
        setRotation(0.0625F);
    }

    public BasicTileEntityRenderer(float scale, float rotation)
    {
        setScale(scale);
        setRotation(rotation);
        setOffset(1.5D);
    }

    public BasicTileEntityRenderer(float scale, float rotation, double yOffset)
    {
        setScale(scale);
        setRotation(rotation);
        setOffset(yOffset);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale)
    {
        if (tileEntity instanceof BasicTileEntity)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5D, y + yOffset, z + 0.5D);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glRotatef((tileEntity.blockMetadata * 90.0F), 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glScaled(xScale, yScale, zScale);
            bindTexture(texture);
            model.render(rotation);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    public final void setOffset(double yOffset)
    {
        this.yOffset = yOffset;
    }

    private void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public final void setScale(float scale)
    {
        this.xScale = scale;
        this.yScale = scale;
        this.zScale = scale;
    }

    public final void setScale(float xScale, float yScale, float zScale)
    {
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;

    }

}
