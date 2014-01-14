package pixlepix.particlephysics.common.entity;

import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;

public class LeafParticle extends BaseParticle {

	public LeafParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 500;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Leaf";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if((!(particle instanceof LeafParticle))&&particle.effect!=2){
			particle.potential*=0.5;
			particle.effect=2;
		}
		
	}

}
