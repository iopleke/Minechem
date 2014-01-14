package pixlepix.particlephysics.common.entity;

import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;

public class BlazepowderParticle extends BaseParticle {

	public BlazepowderParticle(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Blazepowder";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if(!(particle instanceof BlazepowderParticle)){
			particle.effect=1;
		}
	}

}
