package pixlepix.particlephysics.common.entity;

import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;

public class GlassParticle extends BaseParticle {

	
	
	public GlassParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public void onEntityUpdate(){
		super.onEntityUpdate();
		if(this.potential<9800){
			this.potential=Math.min(this.potential+5F, 10000);
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Glass";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if(particle instanceof SandParticle){
			if(this.potential>19000){
				this.potential=Math.max(this.potential+300,20000);
				particle.setDead();
			}
		}else{
			float deficit=particle.getStartingPotential()-particle.potential;
			if(deficit>0){
				float drain=Math.max(deficit, this.potential);
				particle.potential+=drain;
				this.potential-=drain;
			}
			if(this.potential<350){
				this.setDead();
			}
		}
		
	}
	
	

}
