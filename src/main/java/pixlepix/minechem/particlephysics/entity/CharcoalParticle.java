package pixlepix.minechem.particlephysics.entity;

import pixlepix.minechem.particlephysics.api.BaseParticle;
import net.minecraft.world.World;

public class CharcoalParticle extends BaseParticle {

	public CharcoalParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 3000;
	}

	@Override
	public String getName(){
		return "Charcoal";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if(!(particle instanceof PaperParticle||particle instanceof CharcoalParticle)){
			particle.setDead();
			this.potential+=(0.5*particle.potential);
		}
	}

}
