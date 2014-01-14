package pixlepix.particlephysics.common.helper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import pixlepix.particlephysics.common.blocks.Emitter;

public class ParticlePhysicsTab extends CreativeTabs {
	public static ParticlePhysicsTab instance;
	public ParticlePhysicsTab() {
		super("tabParticlePhysics");

		instance=this;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(BetterLoader.getBlock(Emitter.class));
		
	}
}
