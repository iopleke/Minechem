package pixlepix.minechem.particlephysics.helper;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Icon;
import pixlepix.minechem.particlephysics.api.BaseParticle;
import pixlepix.minechem.particlephysics.render.RenderParticle;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public static int RENDER_ID;

    public static Icon clay;
    public static Icon coal;
    public static Icon concentrated;
    public static Icon seed;
    public static Icon split;

    public static Icon sand;

    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(BaseParticle.class, new RenderParticle());
    }
}
