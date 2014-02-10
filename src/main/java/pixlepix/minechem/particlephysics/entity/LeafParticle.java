package pixlepix.minechem.particlephysics.entity;

import net.minecraft.world.World;
import pixlepix.minechem.particlephysics.api.BaseParticle;

public class LeafParticle extends BaseParticle {

	public LeafParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		return 500;
	}

	@Override
	public String getName() {
		return "Leaf";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if ((!(particle instanceof LeafParticle)) && particle.effect != 2) {
			particle.potential *= 0.5;
			particle.effect = 2;
		}

	}

}
