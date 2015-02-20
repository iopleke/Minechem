package minechem.apparatus.prefab.renderer;

import minechem.apparatus.prefab.model.BasicModel;
import minechem.apparatus.prefab.tileEntity.BasicInventoryTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class BasicTileEntityRenderer extends TileEntitySpecialRenderer
{
    protected BasicModel model;
    protected float rotation;
    protected ResourceLocation texture;
    private double xOffset;

    protected float xScale;
    protected double yOffset;
    protected float yScale;
    private double zOffset;
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
        setOffset(0.5D, 1.5D, 0.5D);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale)
    {
        if (tileEntity instanceof BasicInventoryTileEntity)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(x + xOffset, y + yOffset, z + zOffset);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glRotatef((tileEntity.getBlockMetadata() * 90.0F), 0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glScaled(xScale, yScale, zScale);
            bindTexture(texture);
            model.render(rotation);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    public final void setOffset(double xOffset, double yOffset, double zOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
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
