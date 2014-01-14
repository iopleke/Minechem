package pixlepix.particlephysics.common.api;

import net.minecraft.world.World;

public interface IParticleBouncer {
	public boolean canBounce(World world, int x, int y, int z, BaseParticle particle);

}
