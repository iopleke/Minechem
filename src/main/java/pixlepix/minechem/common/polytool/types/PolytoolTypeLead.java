package pixlepix.minechem.common.polytool.types;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.common.polytool.PolytoolUpgradeType;

public class PolytoolTypeLead extends PolytoolUpgradeType{

	public PolytoolTypeLead() {
		super();
	}

	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block) {

		return 0;
	}

	@Override
	public void hitEntity(ItemStack itemStack, EntityLivingBase target,
			EntityLivingBase player) {
		if(!target.worldObj.isRemote){
			List targets=target.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(target.posX-power*3, target.posY-power*3, target.posZ-power*3, target.posX+power*3, target.posY+power*3, target.posZ+power*3));
			Iterator iter=targets.iterator();
			while(iter.hasNext()){
				EntityLivingBase entity=(EntityLivingBase) iter.next();
				if(entity!=player){
					entity.motionY=-50;
				}
			}
		}
	}

	@Override
	public void onBlockDestroyed(ItemStack itemStack, World world, int id,
			int x, int y, int z, EntityLivingBase entityLiving) {
	}

	@Override
	public EnumElement getElement() {

		return EnumElement.Pb;
	}

	@Override
	public void onTick() {
	}

	@Override
	public String getDescription() {
		
		return "Sends nearby entities flying to the ground";
	}

}
