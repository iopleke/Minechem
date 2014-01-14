package pixlepix.minechem.common.polytool;

import pixlepix.minechem.api.core.EnumElement;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class PolytoolUpgradeType {

	public float power=0;

	public abstract float getStrVsBlock(ItemStack itemStack, Block block);
	public abstract void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player);
	public abstract void onBlockDestroyed(ItemStack itemStack, World world, int id, int x, int y, int z, EntityLivingBase entityLiving);
	public abstract EnumElement getElement();
	public abstract void onTick();
	public PolytoolUpgradeType(){
		if(!PolytoolHelper.types.containsKey(getElement())){
			PolytoolHelper.types.put(this.getElement(), (Class<PolytoolUpgradeType>) this.getClass());
		}
	}
	
	//To prevent NPE in alloys
	public PolytoolUpgradeType(boolean b) {
	}
	public void onTickFull(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		this.onTick();
	}
	public abstract  String getDescription();


}


