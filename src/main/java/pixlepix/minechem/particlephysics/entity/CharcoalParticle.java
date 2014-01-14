package pixlepix.particlephysics.common.entity;

import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.ClientProxy;
import pixlepix.particlephysics.common.render.BlockRenderInfo;
import net.minecraft.block.Block;
import net.minecraft.util.Icon;
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
