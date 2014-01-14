package pixlepix.particlephysics.common.entity;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.ClientProxy;
import pixlepix.particlephysics.common.render.BlockRenderInfo;

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
