package pixlepix.particlephysics.common.entity;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.ClientProxy;
import pixlepix.particlephysics.common.render.BlockRenderInfo;

public class ClayParticle extends BaseParticle {
	
	public int ticks=0;
	
	public int iSize=1;
	public int jSize=1;
	public int kSize=1;
	public int rotationX=0;
	public int rotationY=0;
	public int rotationZ=0;
	public float potential;
	public ForgeDirection movementDirection;
	
	public ClayParticle(World par1World) {
		super(par1World);
		
	}
	public float getStartingPotential(){
		return 1500;
		
	}
	@Override
	public String getName(){
		return "Clay";
	}
	
	@Override
	public void onCollideWithParticle(BaseParticle particle){
		if(particle instanceof CoalParticle){
			if(!worldObj.isRemote){
				SplitParticle coalDerived=new SplitParticle(worldObj);
				coalDerived.setPosition(particle.posX,particle.posY,particle.posZ);
				coalDerived.setVelocity(particle.motionX, particle.motionY, particle.motionZ);
				SplitParticle lapisDerived=new SplitParticle(worldObj);

				lapisDerived.setPosition(this.posX,this.posY,this.posZ);
				lapisDerived.setVelocity(this.motionX, this.motionY, this.motionZ);
				coalDerived.setPartner(lapisDerived);
				lapisDerived.setPartner(coalDerived);
				lapisDerived.movementDirection=this.movementDirection;
				coalDerived.movementDirection=particle.movementDirection;
				worldObj.spawnEntityInWorld(coalDerived);
				worldObj.spawnEntityInWorld(lapisDerived);
			}
			this.setVelocity(0, 0,0);
			particle.setVelocity(0,0,0);
			particle.setDead();
			this.setDead();
			
		}
	}
	
 

	
	
	
	

}
