package pixlepix.particlephysics.common.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.ClientProxy;
import pixlepix.particlephysics.common.render.BlockRenderInfo;

public class SplitParticle extends BaseParticle{
	boolean derivedFromCoal;
	SplitParticle partner;
	long birthTime;
	
	public SplitParticle(World par1World) {
		super(par1World);

		this.birthTime=worldObj.getTotalWorldTime();
	}
	public void setPartner(SplitParticle particle){
		this.partner=particle;
	}
	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 4000;
	}

	@Override
	public String getName(){
		return "Split";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if(particle instanceof SplitParticle){
			if(this.birthTime-this.worldObj.getTotalWorldTime()<-2){
				ConcentratedParticle produce=new ConcentratedParticle(this.worldObj);
				produce.setPosition(this.posX,this.posY,this.posZ);
				produce.setVelocity(0,1,0);
				worldObj.spawnEntityInWorld(produce);
				
				particle.setDead();

				this.setDead();
			}
		}
		
		
	}
}
