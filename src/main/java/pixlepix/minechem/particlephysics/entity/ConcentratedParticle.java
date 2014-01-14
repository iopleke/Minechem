package pixlepix.minechem.particlephysics.entity;

import net.minecraft.world.World;
import pixlepix.minechem.particlephysics.api.BaseParticle;

public class ConcentratedParticle extends BaseParticle {

	public ConcentratedParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 25000;
	}

	@Override
	public String getName(){
		return "Concentrated";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		
		
	}

}
