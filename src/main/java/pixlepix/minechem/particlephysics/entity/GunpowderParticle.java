package pixlepix.particlephysics.common.entity;

import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;

public class GunpowderParticle extends BaseParticle {

	public GunpowderParticle(World worldObj) {
		super(worldObj);
	}
	public int ticksToExplode;
	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Gunpowder";
	}
	@Override
	public void onEntityUpdate(){
		super.onEntityUpdate();
		this.ticksToExplode++;
		if(this.ticksToExplode>100){
			worldObj.createExplosion(this, this.posX,this.posY,this.posZ,1F,true);
			this.setDead();
		}
	}
	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if(particle instanceof GunpowderParticle){
			return;
		}
		GunpowderParticle produce=new GunpowderParticle(worldObj);
		produce.potential=particle.potential;
		produce.setPosition(particle.posX, particle.posY,particle.posZ);
		produce.setVelocity(particle.motionX, particle.motionY,particle.motionZ);
		//this.setVelocity(this.motionX*-1, this.motionY*-1,this.motionZ*-1);
		worldObj.spawnEntityInWorld(produce);
		particle.setDead();
	}

}
