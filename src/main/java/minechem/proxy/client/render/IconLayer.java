package minechem.proxy.client.render;

import net.minecraft.util.IIcon;

public class IconLayer implements ILayer
{
    private final IIcon iIcon;
    private final boolean colour;

    public IconLayer(IIcon iIcon, boolean colour)
    {
        this.iIcon = iIcon;
        this.colour = colour;
    }

    @Override
    public void render(int z, int colour)
    {
        RenderHelper.resetOpenGLColour();
        if (this.colour) RenderHelper.setOpenGLColour(colour);
        RenderHelper.drawTexturedRectUV(0, 0, z, 16, 16, this.iIcon);
    }

    @Override
    public void render2D(int colour)
    {
        RenderHelper.resetOpenGLColour();
        if (this.colour) RenderHelper.setOpenGLColour(colour);
        RenderHelper.drawTextureIn2D(this.iIcon);
    }

    @Override
    public void render3D(int colour)
    {
        RenderHelper.resetOpenGLColour();
        if (this.colour) RenderHelper.setOpenGLColour(colour);
        RenderHelper.drawTextureIn3D(this.iIcon);
    }
}
