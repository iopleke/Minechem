package pixlepix.particlephysics.common.entity;

import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.ClientProxy;
import pixlepix.particlephysics.common.render.BlockRenderInfo;
import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class CoalParticle extends BaseParticle {

	public CoalParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 4000;
	}
	@Override
	public void onEntityUpdate() {
		
		super.onEntityUpdate();
	}
	@Override
	public String getName(){
		return "Coal";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
	}

}
