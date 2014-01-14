package pixlepix.particlephysics.common.helper;

import java.util.ArrayList;
import java.util.Hashtable;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import pixlepix.particlephysics.common.ParticlePhysics;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.entity.BlankParticle;
import pixlepix.particlephysics.common.entity.BlazepowderParticle;
import pixlepix.particlephysics.common.entity.CharcoalParticle;
import pixlepix.particlephysics.common.entity.ClayParticle;
import pixlepix.particlephysics.common.entity.CoalParticle;
import pixlepix.particlephysics.common.entity.ConcentratedParticle;
import pixlepix.particlephysics.common.entity.GlassParticle;
import pixlepix.particlephysics.common.entity.GunpowderParticle;
import pixlepix.particlephysics.common.entity.LeafParticle;
import pixlepix.particlephysics.common.entity.PaperParticle;
import pixlepix.particlephysics.common.entity.SandParticle;
import pixlepix.particlephysics.common.entity.SeedParticle;
import pixlepix.particlephysics.common.entity.SplitParticle;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ParticleRegistry {

	public static ArrayList<Class> particles=new ArrayList();
	public static void populateParticleList(){
		particles.add(ClayParticle.class);
		particles.add(CoalParticle.class);
		particles.add(ConcentratedParticle.class);
		particles.add(SandParticle.class);
		particles.add(SeedParticle.class);
		particles.add(SplitParticle.class);

		particles.add(CharcoalParticle.class);

		particles.add(GlassParticle.class);

		particles.add(PaperParticle.class);

		particles.add(BlankParticle.class);
		particles.add(GunpowderParticle.class);
		particles.add(BlazepowderParticle.class);
		particles.add(LeafParticle.class);
	}
	public static Hashtable<Class,Icon> icons = new Hashtable<Class, Icon>();
	
	public static void populateIcons(IconRegister register){
		
		for(int i=0;i<particles.size();i++){
				icons.put(particles.get(i),register.registerIcon("particlephysics:"+particles.get(i).getName().substring("pixlepix.particlephysics.common.entity.".length())));
		}	
		
		
	}
	
	public static Icon getIconFromInstance(BaseParticle particle){
		for(int i=0;i<particles.size();i++){
			if(particles.get(i).isInstance(particle)){
				return icons.get(particles.get(i));
			}
		}
		return null;
	}
	
	public static void registerEntities(){

		for(int i=0;i<particles.size();i++){
			try {
				EntityRegistry.registerModEntity(particles.get(i), particles.get(i).getName(), i, ParticlePhysics.instance, 80, 1, true);
			
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
}
