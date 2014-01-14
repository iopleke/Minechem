package pixlepix.particlephysics.common.entity;

import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;

public class PaperParticle extends BaseParticle {

	public PaperParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		return 500;
	}
	@Override
	public void onEntityUpdate(){
		super.onEntityUpdate();
	}
	@Override
	public String getName(){
		return "Paper";
	}
	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if((particle instanceof CharcoalParticle)){
			BlankParticle blankParticle=new BlankParticle(worldObj);

			blankParticle.setPosition(this.posX,this.posY,this.posZ);

			blankParticle.setVelocity(this.motionX, this.motionY, this.motionZ);

			blankParticle.movementDirection=this.movementDirection;
			worldObj.spawnEntityInWorld(blankParticle);
			particle.setDead();
			this.setDead();
		}
	}

}
