package pixlepix.particlephysics.common.helper;

import net.minecraft.util.Icon;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.entity.ClayParticle;
import pixlepix.particlephysics.common.entity.CoalParticle;
import pixlepix.particlephysics.common.entity.ConcentratedParticle;
import pixlepix.particlephysics.common.entity.SandParticle;
import pixlepix.particlephysics.common.entity.SeedParticle;
import pixlepix.particlephysics.common.entity.SplitParticle;
import pixlepix.particlephysics.common.render.RenderParticle;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
