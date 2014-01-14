package pixlepix.particlephysics.common.entity;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.ClientProxy;
import pixlepix.particlephysics.common.render.BlockRenderInfo;

public class BlankParticle extends BaseParticle {

	public BlankParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		return 500;
	}

	@Override
	public String getName(){
		return "Blank";
	}
	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if(!(particle instanceof BlankParticle||particle instanceof PaperParticle||particle instanceof CharcoalParticle)){
			BaseParticle blankParticle=null;
			try {
				blankParticle = particle.getClass().getConstructor(World.class).newInstance(worldObj);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}

			blankParticle.setPosition(this.posX,this.posY,this.posZ);

			blankParticle.setVelocity(this.motionX, this.motionY, this.motionZ);

			blankParticle.movementDirection=this.movementDirection;
			worldObj.spawnEntityInWorld(blankParticle);
			
			this.setDead();
		}
	}

}
