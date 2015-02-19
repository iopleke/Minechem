package minechem.proxy.client.render;

public interface ILayer
{
    void render(int z, int colour);

    void render2D(int colour);

    void render3D(int colour);
}
